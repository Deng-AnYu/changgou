package com.changgou.order.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-19 19:56
 * @description:
 */
@RestController
@RequestMapping(value = "/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private CartService cartService;

    /***
     * 加入购物车
     * @param num:购买的数量
     * @param id：购买的商品(SKU)ID
     * @return
     */
    @RequestMapping(value = "/add")
    public Result add(Integer num, Long id) {
//        String username = "zhangsan";
        String username = this.tokenDecode.getUsername();
        cartService.add(num, id, username);
        return new Result(true, StatusCode.OK, "添加购物车成功");
    }
    /***
     * 显示购物车列表
     */
    @RequestMapping(value = "/list")
    public Result list() {
        String username = this.tokenDecode.getUsername();

        List<OrderItem> list = cartService.list(username);
        return new Result(true, StatusCode.OK, "获取购物车列表成功",list);
    }
}
