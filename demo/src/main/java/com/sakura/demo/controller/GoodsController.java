package com.sakura.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakura
 *  @RestController 是一个组合注解，它结合了 @Controller 和 @ResponseBody 注解的功能
 *  （就相当于把两个注解组合在一起）。在使用 @RestController 注解标记的类中，
 *  每个方法的返回值都会以 JSON 或 XML 的形式直接写入 HTTP 响应体中，
 *  相当于在每个方法上都添加了 @ResponseBody 注解。
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @GetMapping("/select")
    @PreAuthorize("hasAuthority('test')")
    public String getGoods() {
        return "iphone";
    }
}
