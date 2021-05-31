package com.security.test.provider.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.test.provider.domain.UserOrder;
import com.security.test.provider.mapper.OrderMapper;
import com.security.test.provider.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderImp extends ServiceImpl<OrderMapper, UserOrder> implements OrderService {

    public int insert(UserOrder order) {
        return baseMapper.insert(order);
    }

//    public Page<Storage> listPage(String id) {
//       return super.page(new Page<>(0, 2), new QueryWrapper<UserOrder>().lambda()
//                .eq(UserOrder::getOrderId, id))
//                .convert(Convert::convert);
//    }
}
