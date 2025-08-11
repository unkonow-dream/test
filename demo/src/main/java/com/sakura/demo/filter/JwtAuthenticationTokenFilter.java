package com.sakura.demo.filter;

import com.alibaba.fastjson.JSON;
import com.sakura.demo.domain.vo.LoginUser;
import com.sakura.demo.exception.CustomerAuthenticationException;
import com.sakura.demo.handler.LoginFaliureHandler;
import com.sakura.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final LoginFaliureHandler loginFaliureHandler;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public JwtAuthenticationTokenFilter(LoginFaliureHandler loginFaliureHandler) {
        this.loginFaliureHandler = loginFaliureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1、判断如果是访问登录接口/user/login，直接放行
            String uri = request.getRequestURI();
            if (uri.equals("/user/login")|| uri.equals("/user/logout")) {
                filterChain.doFilter(request, response);
                return;
            }
            this.validateToken(request);
        } catch (AuthenticationException e) {
            loginFaliureHandler.onAuthenticationFailure(request, response,e);
            return;
        }
        // 6、放行
        filterChain.doFilter(request, response);


    }
    private void validateToken(HttpServletRequest request){
        // 2、判断token令牌是否为空
        String token = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if(ObjectUtils.isEmpty(token)){
            throw new CustomerAuthenticationException("token为空");
        }
        String redisString = stringRedisTemplate.opsForValue().get(token);
        if(ObjectUtils.isEmpty(redisString)){
            throw new CustomerAuthenticationException("token已过期");
        }

        // 3、解析token令牌
        LoginUser loginUser= null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String loginUserString = claims.getSubject();
            loginUser = JSON.parseObject(loginUserString, LoginUser.class);
            System.out.println(loginUser.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 4、封装AuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        // 5、存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
