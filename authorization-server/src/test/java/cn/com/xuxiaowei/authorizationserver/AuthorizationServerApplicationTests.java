package cn.com.xuxiaowei.authorizationserver;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionDecoderFactory;
import org.springframework.security.oauth2.server.authorization.web.OAuth2ClientAuthenticationFilter;
import org.springframework.security.oauth2.server.authorization.web.authentication.JwtClientAssertionAuthenticationConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
//@SpringBootTest

/**
 * @see OAuth2ClientAuthenticationFilter 客户凭证验证过滤器（根据路径拦截）
 * @see JwtClientAssertionAuthenticationConverter 判断使用哪种方式进行客户凭证的验证（根据参数拦截）
 * @see JwtClientAssertionAuthenticationProvider 用于对 {@link Jwt} client_assertion 参数进行身份验证（根据参数拦截到之后，再根据授权类型拦截）
 * @see JwtClientAssertionDecoderFactory 用于对 {@link Jwt} client_assertion 参数中的 {@link Jwt} 进行身份验证
 */
@Slf4j
class AuthorizationServerApplicationTests {

    @Test
    void contextLoads() {

        String clientId = "client3";
        String clientSecret = "01234567890123456789012345678912";
        String scope = "snsapi_base";
        String accessTokenUri = "http://localhost:1301/oauth2/token" + "?client_id={client_id}&grant_type=client_credentials&scope={scope}&client_assertion_type={client_assertion_type}&client_assertion={client_assertion}";

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(clientId)
                .issuer(clientId)
                .audience(Collections.singletonList("http://localhost:1401"))
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS)).build();

        String jwt = hmacSign(jwtClaimsSet, clientSecret);

        log.info(jwt);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map = new HashMap<>(8);
        map.put("client_id", clientId);
        map.put("scope", scope);
        map.put("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        map.put("client_assertion", jwt);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(accessTokenUri, httpEntity, String.class, map);

        HttpStatus statusCode = responseEntity.getStatusCode();
        log.info(String.valueOf(statusCode));
        log.info(responseEntity.getBody());

    }

    private static String hmacSign(JwtClaimsSet claimsSet, String secret) {
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(secret.getBytes(StandardCharsets.UTF_8));
        NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(jwkSource);
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, claimsSet);
        Jwt encode = nimbusJwtEncoder.encode(jwtEncoderParameters);
        return encode.getTokenValue();
    }

}
