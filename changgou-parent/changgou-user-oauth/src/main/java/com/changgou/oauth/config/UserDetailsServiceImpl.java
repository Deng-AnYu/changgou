package com.changgou.oauth.config;

import com.changgou.user.feign.UserFeign;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/***
 * 描述
 * @author ljh
 * @packagename com.itheima.config
 * @version 1.0
 * @date 2020/1/10
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("获取到的用户名是：" + username);
        String permission = "ROLE_ADMIN,ROLE_USER";//设置权限
        //使用feign根据id获取数据库中的用户信息
        Result<com.changgou.user.pojo.User> userResult = userFeign.loadById(username);
        if (userResult.getData()==null){
            return null;
        }
//        return new User(username, passwordEncoder.encode("szitheima"),
        return new User(username, userResult.getData().getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
    }
}
