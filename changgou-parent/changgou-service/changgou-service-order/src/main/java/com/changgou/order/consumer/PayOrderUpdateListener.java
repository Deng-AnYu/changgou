package com.changgou.order.consumer;

import com.alibaba.fastjson.JSON;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-24 18:58
 * @description:
 */
@Component
@RabbitListener(queues = "queue.order")
public class PayOrderUpdateListener {



    @Autowired
    private OrderService orderService;

    @RabbitHandler
//    public void handler(String msg) {
    public void handler(Map<String,String> map) {
        //根据map中的信息决定怎样修改表数据
//        Map<String, String> map = JSON.parseObject(msg, Map.class);
        System.out.println(map);
        if (map != null) {
            if ("SUCCESS".equals(map.get("return_code"))) {
                orderService.tradSuccess(map);
            } else {
                orderService.tradFailed(map);
            }
        }
    }

}
