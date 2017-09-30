package com.kasakaid.security.web;

import com.kasakaid.security.spring.SecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(path = SecurityConfig.LOGIN_PAGE)
    public String login() {
        return "login";
    }
    @GetMapping(path = SecurityConfig.LOGIN_PAGE + "?error")
    public String loginFailure() {
        return "login";
    }
    @GetMapping(path = "/debug")
    public String debug() {
        return "debug";
    }
}
