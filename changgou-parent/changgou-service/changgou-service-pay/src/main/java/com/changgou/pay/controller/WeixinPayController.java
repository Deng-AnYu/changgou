package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-21 19:22
 * @description:
 */
@RestController
@RequestMapping("/weixin/pay")
@CrossOrigin
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.pay.exchange.order}")
    private String exchange;
    @Value("${mq.pay.queue.order}")
    private String queue;
    @Value("${mq.pay.routing.key}")
    private String routing;

    /**
     * 创建二维码
     *
     * @param out_trade_no 订单号
     * @param total_fee    金额
     * @return
     */
    @RequestMapping("/create/native")
    public Result createNative(String out_trade_no, String total_fee) {
        Map<String, String> resultMap = weixinPayService.createNative(out_trade_no, total_fee);
        return new Result(true, StatusCode.OK, "生成二维码url成功", resultMap);

    }

    /***
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @GetMapping("/stats/query")
    public Result queryPayStatus(@RequestParam(name = "out_trade_no") String out_trade_no) {
        Map<String, String> resultMap = weixinPayService.queryPayStatus(out_trade_no);
        return new Result(true, StatusCode.OK, "查询状态成功！", resultMap);
    }

    /**
     * 接收微信的通知
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/url")
    public String notifyUrl(HttpServletRequest request) {
        //从流中读取数据
        ByteArrayOutputStream outSteam = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, read);
            }
            String content = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> contentMap = WXPayUtil.xmlToMap(content);
            System.out.println(contentMap);
            String return_code = contentMap.get("return_code");
            //得到了反馈,去发消息给RabbitMQ
            //需要参数:路由key 交换机 参数
            rabbitTemplate.convertAndSend(exchange, routing, contentMap);
//            String json = JSON.toJSONString(contentMap);
//            rabbitTemplate.convertAndSend(exchange, routing, json);
            //处理完,发送回复给微信
            Map<String, String> map = new HashMap<>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            return WXPayUtil.mapToXml(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outSteam != null) {
                try {
                    outSteam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "<xml><return_code><![CDATA[FAIL]]></return_code>\n" +
                "  <return_msg><![CDATA[BU OK]]></return_msg>\n" +
                "</xml>";

    }
}