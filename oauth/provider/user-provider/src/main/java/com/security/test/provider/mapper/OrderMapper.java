package com.security.test.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.test.provider.domain.UserOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {

    int insert(UserOrder order);
}
