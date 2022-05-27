package cn.com.xuxiaowei.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 配置
 *
 * @author xuxiaowei
 * @see UsernamePasswordAuthenticationFilter 用户名密码认证过滤器
 * @see DefaultLoginPageGeneratingFilter 默认登录页面
 * @see HttpSecurity#formLogin() 登录配置
 * @since 0.0.1
 */
@EnableWebSecurity
public class WebSecurityConfigurerAdapterConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/webjars/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        http.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/messaging-client-oidc")).oauth2Client(withDefaults());

        return http.build();
    }

}
