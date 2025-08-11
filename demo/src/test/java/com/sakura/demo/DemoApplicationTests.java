package com.sakura.demo;

import com.sakura.demo.domain.User;
import com.sakura.demo.mapper.UserMapper;
import com.sakura.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {



    @Test
    void contextLoads() {
        String jwt = JwtUtil.createJWT("10086","sakura",null);
        System.out.println(jwt);

        try {
            Claims claims = JwtUtil.parseJWT(jwt);
            String id = claims.getId();
            String subject = claims.getSubject();
            System.out.println(id+"==="+subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
    @Test注解：标记这是一个测试方法，由JUnit 5执行
    方法名UserMapperTest()：测试方法名称，用于验证UserMapper的功能
    List<User> users = userMapper.selectList(null)：
    调用MyBatis-Plus提供的selectList方法查询所有用户
    参数null表示不添加任何查询条件，即查询全部数据
    返回结果是User对象的列表
    users.forEach(System.out::println)：
    使用Java 8的Stream API遍历用户列表
    对每个User对象调用toString()方法并打印到控制台
     */
    @Autowired
    private UserMapper userMapper;
    @Test
    void UserMapperTest() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Autowired

    private PasswordEncoder passwordEncoder;
    @Test
    void testPasswordEncoder() {
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("123", encode);
        System.out.println(matches);
    }

}
