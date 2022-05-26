package cn.com.xuxiaowei.authorizationserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 授权服务器配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class AuthorizationServerConfiguration {

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {

        String password = "123";
        log.debug("默认用户名：user，密码：{}", password);

        securityProperties.getUser().setPassword(password);
    }

}
