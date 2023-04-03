package cn.com.xuxiaowei.resourceserver.configuration;

import cn.com.xuxiaowei.resourceserver.properties.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @see JwtIssuerAuthenticationManagerResolver
 */
@Configuration
public class AuthenticationManagerResolverConfiguration implements AuthenticationManagerResolver<HttpServletRequest> {

    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    public void setRsaKeyProperties(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Override
    public AuthenticationManager resolve(HttpServletRequest context) {

        PublicKey publicKey = rsaKeyProperties.rsaPublicKey();

        NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder.withPublicKey((RSAPublicKey) publicKey);
        NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();


        return null;
    }

}
