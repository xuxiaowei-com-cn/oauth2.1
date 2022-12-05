package cn.com.xuxiaowei.authorizationserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @RequestMapping
    public String index(Authentication authentication, JwtAuthenticationToken jwtAuthenticationToken) throws JsonProcessingException {

        Jwt token = jwtAuthenticationToken.getToken();
        Map<String, Object> claims = token.getClaims();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String s = objectMapper.writeValueAsString(claims);

        return s;
    }

}
