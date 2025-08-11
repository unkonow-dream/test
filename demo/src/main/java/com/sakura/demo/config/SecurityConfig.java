package com.sakura.demo.config;

import com.sakura.demo.filter.JwtAuthenticationTokenFilter;
import com.sakura.demo.handler.AnonymousAuthenticationHandler;
import com.sakura.demo.handler.CustomerAccessDeniedHandler;
import com.sakura.demo.handler.LoginFaliureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)// 开启方法权限认证
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    // 拒绝访问处理
    @Autowired
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    @Autowired
    private LoginFaliureHandler loginFaliureHandler;

    // 匿名访问处理
    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/logout") // 登录和登出都放行
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureHandler(loginFaliureHandler)
                )
                .logout(logout -> logout.disable()) // 禁用默认 logout 过滤器
                .csrf(csrf -> csrf.disable()) // 开发阶段关闭 CSRF
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customerAccessDeniedHandler)
                        .authenticationEntryPoint(anonymousAuthenticationHandler)
                );

        http.addFilterBefore(jwtAuthenticationTokenFilter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
