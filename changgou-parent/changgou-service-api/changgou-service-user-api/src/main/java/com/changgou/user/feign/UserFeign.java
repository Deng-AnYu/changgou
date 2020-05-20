package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Deng
 * @date: 2020-05-19 11:10
 * @description:
 */
@FeignClient(name = "user")
@RequestMapping("/user")
public interface UserFeign {
    /**
     * 查看用户是否存在
     *
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    Result<User> loadById(@PathVariable(name = "id") String id);


}
