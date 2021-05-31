package com.security.test.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
//
//    @Autowired
//    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomTokenService customTokenService;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
//        resources.tokenServices(customTokenService);
        resources.accessDeniedHandler(accessDeniedHandler());
        resources.authenticationEntryPoint(authenticationEntryPoint());
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("code", "401");
            map.put("message", "身份认证失败");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(map));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((httpServletRequest, httpServletResponse, e) -> {
            Map<String, String> map = new HashMap<>(2);
            map.put("code", "403");
            map.put("message", "权限不足");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(map));
        });
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/test").permitAll()
                .anyRequest().authenticated();
    }
}
