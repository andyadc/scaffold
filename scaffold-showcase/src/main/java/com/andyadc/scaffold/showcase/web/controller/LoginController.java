package com.andyadc.scaffold.showcase.web.controller;

import com.andyadc.scaffold.showcase.common.annotation.Performance;
import com.andyadc.scaffold.showcase.security.CaptchaFormAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andaicheng
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @Performance
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        String error_exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        LOGGER.info("info: {}", error_exception);

        if (StringUtils.isNotBlank(error_exception)) {
            if (CaptchaFormAuthenticationFilter.CaptchaValidationException.class.getName().equals(error_exception)) {
                model.addAttribute("message", "验证码错误!");
                return "login";
            }
            if (IncorrectCredentialsException.class.getName().equals(error_exception)) {
                model.addAttribute("message", "用户名或密码错误!");
                return "login";
            }
            if (UnknownAccountException.class.getName().equals(error_exception)) {
                model.addAttribute("message", "用户名或密码错误!");
                return "login";
            }
            if (LockedAccountException.class.getName().equals(error_exception)) {
                model.addAttribute("message", "账户被锁定!");
                return "login";
            }
            if (ExcessiveAttemptsException.class.getName().equals(error_exception)) {
                model.addAttribute("message", "登录尝试次数过多!");
                return "login";
            }
        }
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
