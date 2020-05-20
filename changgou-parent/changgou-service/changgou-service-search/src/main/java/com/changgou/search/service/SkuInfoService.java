package com.changgou.search.service;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-11 13:33
 * @description:
 */
public interface SkuInfoService {

    /**
     * 导入状态码正常的商品的信息
     */
    void importSku();

    /**
     * 搜索商品
     * @param serachMap
     * @return
     */
    Map search(Map<String,String> serachMap);
}
