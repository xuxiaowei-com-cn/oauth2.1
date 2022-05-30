package cn.com.xuxiaowei.authorizationserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

import java.util.UUID;

/**
 * 注册客户 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class RegisteredClientRepositoryTests {

    private JdbcTemplate jdbcTemplate;

    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    /**
     * 创建客户
     */
    @Test
    void save() {

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
        RegisteredClient builder1Client = builder1.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();

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

        registeredClientRepository.save(builder1Client);
        registeredClientRepository.save(builder2Client);
    }

}
