package cn.com.xuxiaowei.authorizationserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xuxiaowei
 */
@Slf4j
@Configuration
public class UserPasswordEncoder implements PasswordEncoder {

    public static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        log.info("用户密码加密");
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("用户密码比较");
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}