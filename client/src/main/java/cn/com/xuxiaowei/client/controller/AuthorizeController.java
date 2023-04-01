package cn.com.xuxiaowei.client.controller;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class AuthorizeController {

    private static final String CLIENT_ID = "xuxiaowei_client_id";

    private static final String CLIENT_SECRET = "xuxiaowei_client_secret";

    private static final String SCOPE = "snsapi_base";

    private static final String BASE_URL = "http://127.0.0.1:1301";

    @GetMapping("/authorize")
    public String authorize(Model model) {

        String accessTokenUri = BASE_URL + "/oauth2/device_authorization?client_id={client_id}&scope={scope}";

        Map<String, String> param = new HashMap<>(4);
        param.put("client_id", CLIENT_ID);
        param.put("scope", SCOPE);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        Map<String, Object> responseParameters = restTemplate.postForObject(accessTokenUri, httpEntity, Map.class, param);

        Instant issuedAt = Instant.now();
        Integer expiresIn = (Integer) responseParameters.get("expires_in");
        Instant expiresAt = issuedAt.plusSeconds(expiresIn);
        Object tokenValue = responseParameters.get("device_code");
        Object userCode = responseParameters.get("user_code");
        Object verificationUri = responseParameters.get("verification_uri");
        Object verificationUriComplete = responseParameters.get("verification_uri_complete");

        model.addAttribute("deviceCode", tokenValue);
        model.addAttribute("expiresAt", expiresAt);
        model.addAttribute("userCode", userCode);
        model.addAttribute("verificationUri", verificationUri);
        // 注意：您可以使用二维码显示此网址
        model.addAttribute("verificationUriComplete", verificationUriComplete);

        return "authorize";
    }

    @GetMapping("/authorized")
    public String authorized(HttpSession session, Model model) {

        Object accessToken = session.getAttribute("accessToken");
        Object refreshToken = session.getAttribute("refreshToken");
        Object scope = session.getAttribute("scope");
        Object tokenType = session.getAttribute("tokenType");
        Object expiresIn = session.getAttribute("expiresIn");

        Object payloadDecode = session.getAttribute("payloadDecode");

        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);
        model.addAttribute("scope", scope);
        model.addAttribute("tokenType", tokenType);
        model.addAttribute("expiresIn", expiresIn);

        model.addAttribute("payloadDecode", payloadDecode);

        return "authorized";
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/authorize")
    public Map<String, Object> authorize(HttpSession session, @RequestParam("device_code") String deviceCode) {

        String accessTokenUri = BASE_URL + "/oauth2/token?grant_type=urn:ietf:params:oauth:grant-type:device_code&device_code={device_code}";

        Map<String, String> param = new HashMap<>(4);
        param.put("device_code", deviceCode);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseParameters = new HashMap<>();
        try {
            responseParameters = restTemplate.postForObject(accessTokenUri, httpEntity, Map.class, param);

            String accessToken = responseParameters.get("access_token").toString();
            Object refreshToken = responseParameters.get("refresh_token");
            Object scope = responseParameters.get("scope");
            Object tokenType = responseParameters.get("token_type");
            Object expiresIn = responseParameters.get("expires_in");

            String[] split = accessToken.split("\\.");
            String payload = split[1];

            String payloadDecode = Base64.decodeStr(payload);

            session.setAttribute("accessToken", accessToken);
            session.setAttribute("refreshToken", refreshToken);
            session.setAttribute("scope", scope);
            session.setAttribute("tokenType", tokenType);
            session.setAttribute("expiresIn", expiresIn);

            session.setAttribute("payloadDecode", payloadDecode);

        } catch (Exception e) {
            log.info("轮训授权结果：", e);
            if (e instanceof HttpClientErrorException.BadRequest) {
                HttpClientErrorException.BadRequest badRequest = (HttpClientErrorException.BadRequest) e;
                String responseBodyAsString = badRequest.getResponseBodyAsString();

                responseParameters = objectMapper.readValue(responseBodyAsString, new TypeReference<Map<String, Object>>() {
                });
            }
        }

        // 响应数据可能出现的结果：
        // 授权待定，继续轮询...
        // map.put("errorCode", "authorization_pending");
        // 放慢速度...
        // map.put("errorCode", "slow_down");
        // 令牌已过期，正在停止...
        // map.put("errorCode", "token_expired");
        // 访问被拒绝，正在停止...
        // map.put("errorCode", "access_denied");

        return responseParameters;
    }

}
