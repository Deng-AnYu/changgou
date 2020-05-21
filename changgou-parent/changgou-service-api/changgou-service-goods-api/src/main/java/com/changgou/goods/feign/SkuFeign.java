package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-05-11 14:48
 * @description:
 */
@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {
    /***
     * 根据审核状态查询Sku
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result<List<Sku>> findBuStatus(@PathVariable(name = "status") String status);

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Sku> findById(@PathVariable(name = "id") Long id);

    /**
     * 从sku表中减少库存
     * @param skuId
     * @return
     */
    @RequestMapping("/deleteCount")
    Result deleteCount(@RequestParam(name = "skuId") Long skuId, @RequestParam(name = "num")Integer num);
}