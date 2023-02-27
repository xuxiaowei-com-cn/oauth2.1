package cn.com.xuxiaowei.client.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * 日志 过滤器
 * <p>
 * 最高优先级
 *
 * @author xuxiaowei
 * @see RestTemplate
 * @since 0.0.1
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LogHttpFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        String param = objectMapper.writeValueAsString(parameterMap);

        log.info("HTTP方法：{}，requestUri：{}，参数：\n{}", req.getMethod(), req.getRequestURI(), param);

        super.doFilter(req, res, chain);
    }

}
