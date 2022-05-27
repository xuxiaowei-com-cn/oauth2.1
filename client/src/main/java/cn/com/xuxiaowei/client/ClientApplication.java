package cn.com.xuxiaowei.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
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
 * @since 0.0.1
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
