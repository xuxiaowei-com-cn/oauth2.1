package cn.com.xuxiaowei.resourceserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务配置
 *
 * @author xuxiaowei
 * @see JwtIssuerAuthenticationManagerResolver
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class ResourceServerConfigurerAdapterConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        http.oauth2ResourceServer().authenticationManagerResolver(authenticationManagerResolver);

        return http.build();
    }

}
