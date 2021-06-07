package com.security.test.provider.controller;


import com.security.test.provider.aop.annotation.SourceType;
import com.security.test.provider.config.DynamicHolder;
import com.security.test.provider.domain.Storage;
import com.security.test.provider.domain.User;
import com.security.test.provider.service.UserService;
import com.security.test.provider.service.imp.StorageImp;
import com.security.test.provider.utils.MiaoSha;
import com.security.test.provider.utils.RequestAttibutesUtils;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class UserProviderController {

    @Autowired
    DataSource dataSource;
    //    @Autowired
    RequestRateLimiter requestRateLimiter;

    @Autowired
    UserService userService;
    @Autowired
    StorageImp storageImp;
    //    @Autowired
    MiaoSha miaoSha;

    @GetMapping("test4")
//    @SourceType(DynamicHolder.DbType.DB2)
    public List<Storage> test4() {
        return storageImp.listStorages(0);
    }

    @GetMapping("test3")
    @SourceType(DynamicHolder.DbType.DB1)
    @Transactional
    public List<Storage> test3() {
        List list = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
            iterator.next();
        return storageImp.listStorages(0);
//        List<Storage> list=  storageImp.listStorages(0);
//        Storage storage=Storage.builder().id("123").build();
//        list.add(storage);
//        list.stream().map(Convert::convert).collect(Collectors.toList());
//        //        User user=User.builder().id(345);
//        log.info("==========进入");
//        throw new Exception();
//        return RequestAttibutesUtils.getRequest();
    }

    @GetMapping("/test")
    public String test1() {
        System.out.println("========================");
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<List<User>>() {
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<User>> uList = restTemplate.exchange("http://localhost:8090/test/user/{3}/{6}", HttpMethod.GET, httpEntity, typeReference, "1", 2);
        return RequestAttibutesUtils.getRequest();
    }

    //    @GetMapping("/user/{id}/{code}")
//    public List<User> user(@PathVariable("id") String id, @PathVariable("code") String code) {
//        List<User> users = new ArrayList<>();
//        users.add(userService.getUser(id));
//        return users;
//    }
    @GetMapping("/ms/{key}/{id}")
    public Object miaosha(@PathVariable String id, @PathVariable String key) {
        if (requestRateLimiter.overLimitWhenIncremented("{order}" + key + "_" + id))
            return "稍后再试";
        return miaoSha.miaosha(id);
    }
}
