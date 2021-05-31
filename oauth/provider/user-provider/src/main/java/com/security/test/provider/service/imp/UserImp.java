package com.security.test.provider.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.test.provider.domain.User;
import com.security.test.provider.mapper.UserMapper;
import com.security.test.provider.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getUser(String id) {
        return super.getById(id);
    }
}
