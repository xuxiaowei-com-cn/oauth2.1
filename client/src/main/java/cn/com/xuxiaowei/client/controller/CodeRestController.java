package cn.com.xuxiaowei.client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Code
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
public class CodeRestController {

    /**
     * 使用授权码 获取 Token
     *
     * @param request  请求
     * @param response 响应
     * @param code     授权码
     * @param state    状态码
     * @return 返回 Token
     */
    @RequestMapping("/code")
    public Map<String, Object> code(HttpServletRequest request, HttpServletResponse response, String code, String state) {

        log.info(code);

        String clientId = "xuxiaowei_client_id";
        String clientSecret = "xuxiaowei_client_secret";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(clientId, clientSecret);

        Map<String, String> param = new HashMap<>(8);
        param.put("code", code);
        param.put("redirect_uri", "http://127.0.0.1:1401/code");

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        String accessTokenUri = "http://127.0.0.1:1301/oauth2/token" + "?code={code}&redirect_uri={redirect_uri}&grant_type=authorization_code";

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(accessTokenUri, httpEntity, Map.class, param);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        log.info(String.valueOf(statusCode));

        Map body = responseEntity.getBody();

        log.info(String.valueOf(body));

        body.put("注意", "这是额外信息，由于授权码 code 只能使用一次，故此URL不能刷新。");

        return body;
    }

}
