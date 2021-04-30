package com.security.api.service;


import com.security.api.service.fallback.UserFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-provider", fallbackFactory = UserFallBack.class)
@RequestMapping("/test")
public interface UserClientService {

    @GetMapping
    String test();

    @GetMapping("/test")
    String test1();

}
