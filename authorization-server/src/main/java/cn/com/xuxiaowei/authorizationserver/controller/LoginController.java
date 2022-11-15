package cn.com.xuxiaowei.authorizationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private CsrfTokenRepository csrfTokenRepository;

    @Autowired
    public void setCsrfTokenRepository(CsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    /**
     * @see org.springframework.security.web.csrf.CsrfFilter
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request,Model model) {

        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);

        String headerName = csrfToken.getHeaderName();

        String parameterName = csrfToken.getParameterName();
        String token = csrfToken.getToken();

        model.addAttribute("headerName", headerName);
        model.addAttribute("parameterName", parameterName);
        model.addAttribute("token", token);

        return "login";
    }

}
