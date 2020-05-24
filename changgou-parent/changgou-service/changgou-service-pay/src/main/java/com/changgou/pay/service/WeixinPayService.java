package com.changgou.pay.service;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-21 19:27
 * @description:
 */
public interface WeixinPayService {

    Map<String, String> createNative(String out_trade_no, String total_fee);

    /***
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    Map queryPayStatus(String out_trade_no);
}
