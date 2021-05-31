package com.security.test.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestController {

    @Autowired
    TokenStore tokenStore;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    CheckTokenEndpoint checkTokenEndpoint;

    @GetMapping("/test")
    public String test(String token) {
//        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        Map<String, ?> response = jwtAccessTokenConverter.convertAccessToken(tokenStore.readAccessToken(token), tokenStore.readAuthentication(token));
        System.out.println("====" + response);
        checkTokenEndpoint.checkToken(token);
        return "hello world";
    }
}
