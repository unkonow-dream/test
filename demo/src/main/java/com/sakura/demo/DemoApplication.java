package com.sakura.demo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
@MapperScan("com.sakura.demo.mapper")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

//        SpringApplication.run(DemoApplication.class, args);
        //获取容器
        /*
        启动Spring Boot应用
        使用 SpringApplication.run() 方法启动整个Spring Boot应用程序
        传入主应用类 DemoApplication.class 和命令行参数 args
        返回应用上下文
        方法返回一个 ConfigurableApplicationContext 对象
        这个对象是Spring应用的核心容器，管理着所有的Bean和应用配置
        应用上下文的作用
        通过 ConfigurableApplicationContext 可以对应用进行进一步的配置和管理
        可以用来获取Bean实例、监听应用事件、刷新或关闭应用等操作
         */
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        System.out.println("123");
    }

}
