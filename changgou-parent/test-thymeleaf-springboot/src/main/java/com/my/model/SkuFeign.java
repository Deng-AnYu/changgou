//package com.changgou.search.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Map;
//
///**
// * @Author: Deng
// * @date: 2020-05-13 22:12
// * @description:
// */
//@FeignClient(name = "search")
//@RequestMapping("/search")
//public interface SkuFeign {
//
//    /**
//     * 调用ES搜索
//     * @param map
//     * @return
//     */
//    @GetMapping
//    Map search(@RequestBody(required = false) Map<String,String> map);
//}
