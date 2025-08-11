package com.sakura.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class NewsController {

    @GetMapping("/getNews")
    @PreAuthorize("hasAnyAuthority('sys:new:select')")
    public String getNews() {
        return "news列表";
    }

    @PostMapping("/insertNews")
    @PreAuthorize("hasAnyAuthority('sys:new:add')")
    public String insertNews() {
        return "保存成功";
    }

    @PutMapping("/updateNews")
    @PreAuthorize("hasAnyAuthority('sys:new:update')")
    public String updateNews() {
        return "修改成功";
    }

    @DeleteMapping("/deleteNews")
    @PreAuthorize("hasAnyAuthority('sys:new:delete')")
    public String deleteNews() {
        return "删除成功";
    }
}