package org.rmysj.api.api.beetl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("title", "Beetl welcome to U!!!");
        model.put("time", new Date());
        model.put("message", "Hello Spring Boot Beetl!");
        return "welcome";
    }
}
