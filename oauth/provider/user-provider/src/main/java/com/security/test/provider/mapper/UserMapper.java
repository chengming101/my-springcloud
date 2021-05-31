package com.security.test.provider.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.test.provider.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void getUser();
}
