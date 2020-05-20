package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-05-12 22:35
 * @description:
 */
public class SearchResultMapperImpl implements SearchResultMapper {
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
        //创建集合
        ArrayList<T> content = new ArrayList<>();
        //获取response中的结果集合
        SearchHits hits = searchResponse.getHits();
        //如果结果为空,则返回
        if (hits == null || hits.totalHits <= 0) {
            return new AggregatedPageImpl<T>(content);
        }
        //遍历结果集合
        for (SearchHit hit : hits) {
            //获取这个结果中的数据
            String sourceAsString = hit.getSourceAsString();
            //转化为SkuInfo对象
            SkuInfo skuInfo = JSON.parseObject(sourceAsString, SkuInfo.class);
            //获取这个结果的属性Map集合
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //获取属性map中的name属性
            HighlightField name = highlightFields.get("name");
            //如果这个name属性不为空,则说明,包好了需要高亮的数据
            if (name != null) {
                //把原本的数据替换成高亮数据
                StringBuffer stringBuffer = new StringBuffer();
                Text[] fragments = name.getFragments();
                for (Text fragment : fragments) {
                    stringBuffer.append(fragment.toString());
                }
                skuInfo.setName(stringBuffer.toString());

            }
            //把装了高亮数据的对象放进内容集合中
            content.add((T) skuInfo);
        }

        return new AggregatedPageImpl<T>(content, pageable, hits.getTotalHits(), searchResponse.getAggregations(),searchResponse.getScrollId());
    }
}
