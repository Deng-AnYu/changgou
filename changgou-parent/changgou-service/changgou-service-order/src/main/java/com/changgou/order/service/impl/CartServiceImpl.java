package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-05-19 19:34
 * @description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param num:购买商品数量
     * @param id：购买ID
     * @param username：购买用户
     */
    @Override
    public void add(Integer num, Long id, String username) {
        if (num<=0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            return;
        }
        //根据id,使用feign获取sku对象
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();
        //根据sku对象id,获取spu
        Result<Spu> spuResult = spuFeign.findById(sku.getSpuId());
        Spu spu = spuResult.getData();
        //创建OrderItem对象
        OrderItem orderItem = new OrderItem();
        //把需要的信息存进去
        orderItem.setNum(num);
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setImage(sku.getImage());
        orderItem.setMoney(sku.getPrice()*num);
        orderItem.setPayMoney(sku.getPrice()*num);

        orderItem.setSpuId(spu.getId());
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        //创建一个OrderItem集合,根据username以hash的形式存进redis中,
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
    }

    @Override
    public List<OrderItem> list(String username) {
        return redisTemplate.boundHashOps("Cart_"+username).values();
    }

    @Override
    public void clean(String username) {
        redisTemplate.delete("Cart_"+username);
    }
}
