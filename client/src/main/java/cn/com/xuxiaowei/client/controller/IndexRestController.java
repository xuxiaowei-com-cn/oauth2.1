package cn.com.xuxiaowei.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Index
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
public class IndexRestController {

    /**
     * 获取 Token
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 Token
     */
    @RequestMapping("")
    public OAuth2AuthorizedClient index(HttpServletRequest request, HttpServletResponse response, @RegisteredOAuth2AuthorizedClient("messaging-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {

        return authorizedClient;
    }

}
