package cn.com.xuxiaowei.resourceserver.configuration;

import cn.com.xuxiaowei.resourceserver.properties.RsaKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @see JwtIssuerAuthenticationManagerResolver
 * @see JwtAuthenticationProvider
 */
@Slf4j
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

        return new AuthenticationManager() {

            private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new JwtAuthenticationConverter();

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
                Jwt jwt = nimbusJwtDecoder.decode(bearer.getToken());
                AbstractAuthenticationToken token = jwtAuthenticationConverter.convert(jwt);
                token.setDetails(bearer.getDetails());
                log.debug("Authenticated token");
                return token;
            }
        };
    }

}
