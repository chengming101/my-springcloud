package com.security.test.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.test.provider.domain.UserOrder;

public interface OrderService extends IService<UserOrder> {

    int insert(UserOrder order);
}
