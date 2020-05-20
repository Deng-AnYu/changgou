package com.changgou.search.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @Author: Deng
 * @date: 2020-05-11 15:25
 * @description:
 */
public interface SkuEsMapper extends ElasticsearchCrudRepository<SkuInfo,Long> {
}