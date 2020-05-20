package com.changgou.search.controller;

import com.changgou.search.service.SkuInfoService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-11 13:29
 * @description:
 */
@RestController
@RequestMapping("/search")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuService;

    @GetMapping("/import")
    public Result importSku() {
        skuService.importSku();
        return new Result(true, StatusCode.OK, "导入数据到索引库中成功！");
    }

    @GetMapping
    public Map search(@RequestParam(required = false) Map map) {
        return skuService.search(map);
    }


}
