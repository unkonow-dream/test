package com.sakura.demo.service.Impl;

import com.sakura.demo.domain.User;
import com.sakura.demo.domain.vo.LoginUser;
import com.sakura.demo.mapper.MenuMapper;
import com.sakura.demo.mapper.UserMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("")){
            throw new InternalAuthenticationServiceException("");
        }

        // 根据用户名查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);

        // 如果查询不到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("");
        }

        // TODO: 查询用户对应的权限信息
        // List<String> permissions = ...;
//        List<String> roles = new ArrayList<>();
//        roles.add("test");
        //动态权限
        List<String> roles = menuMapper.getMenuByUserId(user.getId());

        // 返回封装好的用户信息
        return new LoginUser(user, roles);
    }
}
