package cn.com.xuxiaowei.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationServerMetadataEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2ClientAuthenticationFilter;
import org.springframework.security.oauth2.server.authorization.web.authentication.ClientSecretBasicAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.ClientSecretPostAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.JwtClientAssertionAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.PublicClientAuthenticationConverter;

/**
 * 程序执行入口
 * <p>
 * 1. SQL<p>
 * spring-security-oauth2-authorization-server-*.*.*.jar!\org\springframework\security\oauth2\server\authorization\client\oauth2-registered-client-schema.sql<p>
 * spring-security-oauth2-authorization-server-*.*.*.jar!\org\springframework\security\oauth2\server\authorization\oauth2-authorization-consent-schema.sql<p>
 * spring-security-oauth2-authorization-server-*.*.*.jar!\org\springframework\security\oauth2\server\authorization\oauth2-authorization-schema.sql<p>
 * spring-security-oauth2-authorization-server-*.*.*.jar!\org\springframework\security\oauth2\server\authorization\client\oauth2-registered-client-schema.sql<p>
 *
 * @author xuxiaowei
 * @see OAuth2AuthorizationServerConfiguration OAuth 2.0 授权服务器支持的 {@link Configuration} 。
 * @see UserDetailsServiceAutoConfiguration
 * @see OAuth2AuthorizationEndpointFilter /oauth2/authorize
 * @see ProviderSettings#builder() /oauth2/authorize、/oauth2/token、/oauth2/jwks、/oauth2/revoke、/oauth2/introspect、/connect/register、/userinfo
 * @see OAuth2AuthorizationService 此接口的实现负责OAuth 2.0 Authorization(s)的管理。
 * @see InMemoryOAuth2AuthorizationService 一个 {@link OAuth2AuthorizationService} 存储 {@link OAuth2Authorization} 的内存。
 * @see JdbcOAuth2AuthorizationService {@link OAuth2AuthorizationService} 的 JDBC 实现，它使用 {@link org.springframework.jdbc.core.JdbcOperations} 进行 {@link OAuth2Authorization} 持久性。
 * @see OAuth2AuthorizationConsentService 此接口的实现负责管理 {@link OAuth2AuthorizationConsent} OAuth 2.0 Authorization Consent(s) 。
 * @see InMemoryOAuth2AuthorizationConsentService 一个 {@link OAuth2AuthorizationConsentService} 存储 {@link OAuth2AuthorizationConsent} 的内存。
 * @see JdbcOAuth2AuthorizationConsentService {@link OAuth2AuthorizationConsentService} 的 JDBC 实现，它使用 {@link org.springframework.jdbc.core.JdbcOperations} 进行 {@link OAuth2AuthorizationConsent} 持久性。
 * @see RegisteredClientRepository OAuth 2.0 {@link RegisteredClient} (s) 的存储库。
 * @see InMemoryRegisteredClientRepository 在内存中存储 {@link RegisteredClientRepository} (s) 的 {@link RegisteredClient} 。
 * @see JdbcRegisteredClientRepository {@link RegisteredClientRepository} 的 JDBC 实现，它使用 {@link org.springframework.jdbc.core.JdbcOperations} 进行 {@link RegisteredClient} 持久性。
 * @see OidcScopes
 * @see OAuth2AuthorizationServerMetadataEndpointFilter
 * @see OAuth2ClientAuthenticationFilter OAuth 2.1 客户凭证验证
 * @see JwtClientAssertionAuthenticationConverter
 * @see ClientSecretBasicAuthenticationConverter
 * @see ClientSecretPostAuthenticationConverter
 * @see PublicClientAuthenticationConverter
 * @since 0.0.1
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
