package com.security.test.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.test.provider.domain.User;

public interface UserService extends IService<User> {

    User getUser(String id);
}
