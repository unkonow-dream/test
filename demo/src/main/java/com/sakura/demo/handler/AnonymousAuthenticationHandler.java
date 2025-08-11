package com.sakura.demo.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.sakura.demo.common.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        String result;

        if (authException instanceof BadCredentialsException) {
            result = JSON.toJSONString(R.error()
                    .code(HttpServletResponse.SC_UNAUTHORIZED)
                    .message(authException.getMessage()), JSONWriter.Feature.WriteNulls);
        } else if (authException instanceof InternalAuthenticationServiceException) {
            result = JSON.toJSONString(R.error()
                    .code(HttpServletResponse.SC_UNAUTHORIZED)
                    .message("用户名为空!"), JSONWriter.Feature.WriteNulls);
        } else {
            result = JSON.toJSONString(R.error()
                    .code(600)
                    .message("匿名用户无权访问!"), JSONWriter.Feature.WriteNulls);
        }
        // 输出结果
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
