package com.sakura.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.demo.domain.User;
import com.sakura.demo.domain.vo.LoginUser;
import com.sakura.demo.mapper.UserMapper;
import com.sakura.demo.service.UserService;
import com.sakura.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper , User> implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(User user) {
        //1.封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //2.进行校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //3.如果为空
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //导入登录用户
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String loginUserString = JSON.toJSONString(loginUser);
        String jwt = JwtUtil.createJWT(loginUserString,null);
        //存入redis
        String redisKey = "login:" + jwt;
        //stringRedisTemplate.opsForValue().set(redisKey,jwt,JwtUtil.JWT_TTL/1000);
        stringRedisTemplate.opsForValue().set(jwt, loginUserString, JwtUtil.JWT_TTL/1000);


        return jwt;
    }
}
