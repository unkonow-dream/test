package com.sakura.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.demo.domain.User;

public interface UserService extends IService<User> {
    String login(User user);
}
