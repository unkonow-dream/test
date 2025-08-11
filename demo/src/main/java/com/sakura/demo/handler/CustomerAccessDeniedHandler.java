package com.sakura.demo.handler;

import com.alibaba.fastjson2.JSON;
import com.sakura.demo.common.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置状态码为403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 设置响应内容类型为JSON
        response.setContentType("application/json;charseet=UTF-8");
        // 设置编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        R result = R.error()
                .code(700)                    // 自定义无权限码
                .message("无权限访问，请联系管理员！");

        String json = JSON.toJSONString(result);

        try(var writer = response.getWriter()){
            writer.write(json);
            writer.flush();
        }

    }
}
