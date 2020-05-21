package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Deng
 * @date: 2020-05-20 15:11
 * @description:
 */
@Controller
@RequestMapping("/oauth")
public class LoginRedirect {

    @GetMapping("/login")
    public String login(String FROM, Model model) {
        model.addAttribute("from",FROM);
        return "login";
    }
}
