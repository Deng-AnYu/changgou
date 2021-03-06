package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 给指定的用户增加指定的积分
     * @param username
     * @param points
     * @return
     */
    @PostMapping("/addPoints")
    public Result addPoints(@RequestParam(name = "username") String username,
                            @RequestParam(name = "points")Integer points);
    

}
