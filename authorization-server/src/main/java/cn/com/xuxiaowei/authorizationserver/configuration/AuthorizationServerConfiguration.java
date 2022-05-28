package cn.com.xuxiaowei.authorizationserver.configuration;

import cn.com.xuxiaowei.authorizationserver.properties.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * 授权服务器配置
 *
 * @author xuxiaowei
 * @see <a href="http://127.0.0.1:1301/oauth2/authorize?client_id=xuxiaowei_client_id&redirect_uri=http://127.0.0.1:1401/code&response_type=code&scope=snsapi_base&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7">/oauth2/authorize</a>
 * @see <a href="http://127.0.0.1:1301/.well-known/oauth-authorization-server">OAuth 2.0 授权服务器元数据请求的默认端点URI</a>
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class AuthorizationServerConfiguration {

    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    public void setRsaKeyProperties(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    /**
     * @see <a href="https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html">协议端点的</a> Spring Security 过滤器链。
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // 未从授权端点进行身份验证时重定向到登录页面
        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    /**
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/index.html">用于身份验证</a> 的 Spring Security 过滤器链。
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 表单登录处理从授权服务器过滤器链到登录页面的重定向
        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).formLogin(Customizer.withDefaults());

        return http.build();
    }

    /**
     * @see UserDetailsService 用于检索用户进行身份验证的实例。
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * @see RegisteredClientRepository 用于管理客户端的实例。
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString());
        builder.clientId("xuxiaowei_client_id");
        builder.clientSecret("{noop}xuxiaowei_client_secret");
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
        builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
        builder.authorizationGrantType(AuthorizationGrantType.IMPLICIT);
        builder.redirectUri("http://127.0.0.1:1401/code");
        builder.scope("snsapi_base");

        RegisteredClient messagingClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("messaging-client")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://127.0.0.1:1401/login/oauth2/code/messaging-client-oidc")
            .redirectUri("http://127.0.0.1:1401/authorized")
            .scope(OidcScopes.OPENID)
            .scope("message.read")
            .scope("message.write")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build();

        RegisteredClient registeredClient = builder.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();

        return new InMemoryRegisteredClientRepository(registeredClient, messagingClient);
    }

    /**
     * @see JWKSource 用于签署访问令牌的实例。
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        PublicKey publicKey = rsaKeyProperties.rsaPublicKey();
        PrivateKey privateKey = rsaKeyProperties.rsaPrivateKey();
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) publicKey).privateKey(privateKey).build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * {@link ProviderSettings} 配置 Spring Authorization Server 的实例。
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().build();
    }

}
