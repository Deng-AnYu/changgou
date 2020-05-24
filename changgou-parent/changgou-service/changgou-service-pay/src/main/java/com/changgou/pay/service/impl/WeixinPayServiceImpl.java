package com.changgou.pay.service.impl;

import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-21 19:28
 * @description:
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {


    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.notifyurl}")
    private String notifyurl;

    @Override
    public Map<String, String> createNative(String out_trade_no, String total_fee) {
        try {
            //设置参数
            Map param = new HashMap();
            //应用ID
            param.put("appid", appid);
            //商户ID号
            param.put("mch_id", partner);
            //随机数 //todo 这个订单号现在用的是截取uuid,安全性低,需要改进,有空的时候要改一下
            param.put("nonce_str", WXPayUtil.generateNonceStr());
            //订单描述
            param.put("body", "畅购");
            //商户订单号
            param.put("out_trade_no", out_trade_no);
            //交易金额
            param.put("total_fee", total_fee);
            //终端IP
            param.put("spbill_create_ip", "127.0.0.1");
            //回调地址
            param.put("notify_url", notifyurl);
            //交易类型
            param.put("trade_type", "NATIVE");
            //把参数转成xml格式
            String paramXml = WXPayUtil.generateSignedXml(param, partnerkey);
            //需要发请求到接口,获取code_url
            //发请求,这里选择使用HttpClient
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            //发起post请求
            httpClient.post();
            //这个请求返回的是个xml格式,所以需要转换
            String content = httpClient.getContent();
            Map<String, String> responseMap = WXPayUtil.xmlToMap(content);
            //创建一个要返回的map
            HashMap<String, String> map = new HashMap<>();
            map.put("code_url", responseMap.get("code_url"));
            map.put("out_trade_no", out_trade_no);
            map.put("total_fee", total_fee);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

        //然后返回一个map,map里装着code_url,金额,订单编号
    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        try {

            //使用HttpClient,调用接口
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            HashMap<String, String> map = new HashMap<>();
            map.put("appid", appid);
            map.put("mch_id", partner);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", out_trade_no);
            String parms = WXPayUtil.generateSignedXml(map,partnerkey);
            httpClient.setXmlParam(parms);
            httpClient.setHttps(true);

            httpClient.post();
            String content = httpClient.getContent();
            Map<String, String> responseMap = WXPayUtil.xmlToMap(content);
            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
