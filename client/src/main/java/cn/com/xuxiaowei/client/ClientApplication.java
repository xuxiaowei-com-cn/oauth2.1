package cn.com.xuxiaowei.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.NimbusJwtClientAuthenticationParametersConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 程序执行入口
 *
 * @author xuxiaowei
 * @see DefaultAuthorizationCodeTokenResponseClient#getTokenResponse(OAuth2AuthorizationCodeGrantRequest) 使用授权码获取Token
 * @see org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationGrantRequestEntityUtils#getTokenRequestHeaders(ClientRegistration) 获取Token时的请求Headers
 * @see HttpHeaders#encodeBasicAuth(String, String, Charset) 加密
 * @see Base64.Decoder#decode(String, Charset) 解密（处理为 String：new String(Base64.getDecoder().decode(), StandardCharsets.ISO_8859_1)）
 * @see NimbusJwtClientAuthenticationParametersConverter 添加一个签名的 JSON Web 令牌 (JWS) 来自定义 OAuth 2.0 访问令牌请求参数，以用于在授权服务器的令牌端点进行客户端身份验证。用于签署 JWS 的私有/秘密密钥由构造函数提供的com.nimbusds.jose.jwk.JWK解析器提供。
 * @see org.springframework.security.oauth2.client.endpoint.AbstractOAuth2AuthorizationGrantRequestEntityConverter
 * @see OAuth2AuthorizationCodeGrantRequestEntityConverter {@link org.springframework.security.oauth2.client.endpoint.AbstractOAuth2AuthorizationGrantRequestEntityConverter} 的实现，它将提供的 {@link OAuth2AuthorizationCodeGrantRequest} 转换为授权代码授予的 OAuth 2.0 访问令牌请求的 {@link RequestEntity} 表示
 * @since 0.0.1
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
