package com.sakura.demo.controller;

import com.sakura.demo.common.R;
import com.sakura.demo.domain.User;
import com.sakura.demo.exception.CustomerAuthenticationException;
import com.sakura.demo.service.UserService;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/login")
    public R login(@RequestBody User user){
        String jwt = userService.login(user);
        if(StringUtils.hasLength(jwt)){
            return R.ok().message("登录成功").data("token",jwt);
        }
        return R.error().message("登录失败");
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("===== logout 方法执行了 =====");

        // 获取jwt token
        String token = request.getHeader("Authorization");
        if (isEmpty(token)) {
            token = request.getParameter("Authorization");
        }


        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 1、清除安全上下文
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // 2、从Redis中删除token
        Boolean deleted = stringRedisTemplate.delete(token);
        if (Boolean.TRUE.equals(deleted)) {
            return R.ok().message("退出系统成功！");
        } else {
            return R.ok().message("退出系统成功！（token已失效）");
        }
    }


}
