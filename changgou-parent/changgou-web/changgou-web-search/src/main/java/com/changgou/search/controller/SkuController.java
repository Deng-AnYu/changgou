package com.changgou.search.controller;

import com.changgou.search.feign.SkuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-13 21:52
 * @description:
 */
@Controller
@RequestMapping("/search")
public class SkuController {
    @Autowired
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map searchMap, Model model) {
        //调用changgou.service.search.api的fegin
        Map resultMap = skuFeign.search(searchMap);
        model.addAttribute("result", resultMap);
        model.addAttribute("searchMap",searchMap);
        return "search";
    }



}
