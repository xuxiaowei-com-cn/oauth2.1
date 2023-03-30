package cn.com.xuxiaowei.authorizationserver;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionDecoderFactory;
import org.springframework.security.oauth2.server.authorization.web.OAuth2ClientAuthenticationFilter;
import org.springframework.security.oauth2.server.authorization.web.authentication.JwtClientAssertionAuthenticationConverter;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    void contextLoads() throws JOSEException {

        String clientId = "client3";
        String clientSecret = "01234567890123456789012345678912";
        String scope = "snsapi_base";
        String accessTokenUri = "http://localhost:1301/oauth2/token" + "?client_id={client_id}&grant_type=client_credentials&scope={scope}&client_assertion_type={client_assertion_type}&client_assertion={client_assertion}";

        // 至少以下四项信息
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                // 主体：固定clientId
                .subject(clientId)
                // 发行者：固定clientId
                .issuer(clientId)
                // 授权中心的地址
                .audience("http://localhost:1401")
                // 过期时间
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).build();

        String jwt = hmacSign(claimsSet, clientSecret);

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

    /**
     * 使用 HMAC 算法加签生成jwt
     */
    private static String hmacSign(JWTClaimsSet claimsSet, String secret) throws JOSEException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        JWSSigner signer = new MACSigner(secretKeySpec);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

}
