package com.andyadc.scaffold.showcase.web.controller;

import com.andyadc.scaffold.showcase.entity.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author andaicheng
 */
@RequestMapping("api")
@Controller
public class ApiController {

    @ResponseBody
    @RequestMapping(value = "test")
    public Object test() {
        AuthUser user = new AuthUser();
        user.setAccount("test");
        return user;
    }

}
