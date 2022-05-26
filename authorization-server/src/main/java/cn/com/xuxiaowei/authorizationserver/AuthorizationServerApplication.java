package cn.com.xuxiaowei.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;

/**
 * 程序执行入口
 *
 * @author xuxiaowei
 * @see <a href="http://127.0.0.1:1301/oauth2/authorize">/oauth2/authorize</a>
 * @see OAuth2AuthorizationEndpointFilter /oauth2/authorize
 * @since 0.0.1
 */
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
