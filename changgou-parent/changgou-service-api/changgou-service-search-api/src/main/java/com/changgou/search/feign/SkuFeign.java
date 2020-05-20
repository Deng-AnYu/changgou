package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-14 19:25
 * @description:
 */
@FeignClient(name = "search")
@RequestMapping("/search")
public interface SkuFeign {

    @GetMapping
    Map search(@RequestParam(required = false) Map<String, String> map);


}
