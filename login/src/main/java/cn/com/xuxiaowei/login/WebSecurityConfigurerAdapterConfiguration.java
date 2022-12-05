package cn.com.xuxiaowei.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfigurerAdapterConfiguration {

    /**
     *
     */
    @Bean
    @Order(0)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> {
            authorize
                    // 放行端点
                    .antMatchers("/login/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated();
        });

        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }

}
