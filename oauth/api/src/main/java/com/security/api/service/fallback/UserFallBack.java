package com.security.api.service.fallback;

import com.security.api.service.UserClientService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFallBack implements FallbackFactory<UserClientService> {

    @Override
    public UserClientService create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new UserClientService() {
            @Override
            public String test() {
                return "test()yichang";
            }

            @Override
            public String test1() {
                return "test1()yichang";
            }
        };
    }
}
