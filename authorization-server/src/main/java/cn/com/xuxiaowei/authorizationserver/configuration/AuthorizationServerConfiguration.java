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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
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
 * @see JwtGenerator
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

        RegisteredClient.Builder builder1 = RegisteredClient.withId(UUID.randomUUID().toString());
        // 客户ID
        builder1.clientId("xuxiaowei_client_id");
        // 客户凭证
        builder1.clientSecret("{noop}xuxiaowei_client_secret");
        // 客户凭证验证方式
        builder1.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder1.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        // 授权类型
        builder1.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder1.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
        builder1.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
        builder1.authorizationGrantType(AuthorizationGrantType.IMPLICIT);
        // 授权成功后重定向地址
        builder1.redirectUri("http://127.0.0.1:1401/code");
        // 授权范围
        builder1.scope("snsapi_base");
        builder1.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build());
        builder1.tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build());
        RegisteredClient builder1Client = builder1.build();

        RegisteredClient.Builder builder2 = RegisteredClient.withId(UUID.randomUUID().toString());
        // 客户ID
        builder2.clientId("messaging-client");
        // 客户凭证
        builder2.clientSecret("{noop}secret");
        // 客户凭证验证方式
        builder2.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        builder2.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        // 授权类型
        builder2.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder2.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
        builder2.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
        // 授权成功后重定向地址
        builder2.redirectUri("http://127.0.0.1:1401/login/oauth2/code/messaging-client-oidc");
        builder2.redirectUri("http://127.0.0.1:1401/authorized");
        // 授权范围
        builder2.scope(OidcScopes.OPENID);
        builder2.scope("message.read");
        builder2.scope("message.write");
        RegisteredClient builder2Client = builder2.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();

        return new InMemoryRegisteredClientRepository(builder1Client, builder2Client);
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
     * {@link AuthorizationServerSettings} 配置 Spring Authorization Server 的实例。
     */
    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}
