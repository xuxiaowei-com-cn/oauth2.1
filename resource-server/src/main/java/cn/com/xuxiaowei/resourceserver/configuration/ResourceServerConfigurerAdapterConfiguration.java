package cn.com.xuxiaowei.resourceserver.configuration;

import cn.com.xuxiaowei.resourceserver.properties.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 资源服务配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class ResourceServerConfigurerAdapterConfiguration {

    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    public void setRsaKeyProperties(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        PublicKey publicKey = rsaKeyProperties.rsaPublicKey();

        NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder.withPublicKey((RSAPublicKey) publicKey);
        NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();

        http.oauth2ResourceServer().jwt().decoder(nimbusJwtDecoder);

        return http.build();
    }

}
