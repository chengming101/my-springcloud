package com.security.test.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class AuthenticationApplication {

    @Autowired
    StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
