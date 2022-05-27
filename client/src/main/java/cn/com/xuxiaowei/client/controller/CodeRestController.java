package cn.com.xuxiaowei.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Code
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
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
        Map<String, Object> map = new HashMap<>(4);

        return map;
    }

}
