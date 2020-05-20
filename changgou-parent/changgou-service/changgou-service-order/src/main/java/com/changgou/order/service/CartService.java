package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-05-19 19:33
 * @description:
 */
public interface CartService {

    /***
     * 添加购物车
     * @param num:购买商品数量
     * @param id：购买ID
     * @param username：购买用户
     * @return
     */
    void add(Integer num, Long id, String username);


    /**
     * 根据用户名获取redis中的购物车列表
     * @param username
     * @return
     */
    List<OrderItem> list(String username);

}
