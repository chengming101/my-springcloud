package com.security;

import com.security.test.provider.domain.Storage;
import com.security.test.provider.service.imp.OrderImp;
import com.security.test.provider.service.imp.StorageImp;
import com.security.test.provider.utils.Convert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserProviderTestApplication.class)
public class UserProviderTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StorageImp storageImp;

    @Autowired
    OrderImp orderImp;


    @Test
    public void delete() {
        storageImp.deleteByMap();
    }

    @Test
    public void test() {
        storageImp.listPage("0");
        Storage storage = Storage.builder().id("123").build();
        List<Storage> list = new ArrayList<>();
        list.add(storage);
        list.stream().map(Convert::convert).collect(Collectors.toList());
        //        User user=User.builder().id(345);
    }
}
