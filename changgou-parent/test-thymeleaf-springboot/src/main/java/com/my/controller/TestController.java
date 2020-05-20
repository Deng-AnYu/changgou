package com.my.controller;

import com.my.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-13 20:32
 * @description:
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("hello","i want money");
        model.addAttribute("user",new User(1,"白露未晞","myhome"));
        ArrayList<String> list = new ArrayList<>();
        list.add("money");
        list.add("woman");
        list.add("health");
        list.add("family");
        list.add("freedom");
        model.addAttribute("list",list);
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(2,"蒹葭2","2"));
        users.add(new User(3,"蒹葭3","2"));
        users.add(new User(4,"蒹葭4","2"));
        model.addAttribute("users",users);

        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("i","want");
        dataMap.put("much","money");
        model.addAttribute("dataMap",dataMap);
        model.addAttribute("now",new Date());
        return "demo";
    }

}
