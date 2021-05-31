package com.security.test.provider.utils;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisUtils {

    @Autowired
    StringRedisTemplate redisTemplate;

    public Boolean setNx(String key, String value, Long expire) {
        if (expire == null)
            expire = 1L;
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return;
    }

    public void remove(String key) {
        redisTemplate.delete(key);
        return;
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean execute(String lua) {
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript(lua, Long.class);
        Long result = (Long) redisTemplate.execute(defaultRedisScript, null);
        return result > 0;
    }
}
