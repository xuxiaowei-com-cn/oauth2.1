package cn.com.xuxiaowei.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

/**
 * 程序执行入口
 *
 * @author xuxiaowei
 * @see DefaultAuthorizationCodeTokenResponseClient#getTokenResponse(OAuth2AuthorizationCodeGrantRequest) 使用授权码获取Token
 * @since 0.0.1
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
