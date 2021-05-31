package com.security.test.provider.utils;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class MybatisPlusPagingConfig {
    public static void main(String[] args) {
        List list = new ArrayList<String>();
        list.add("123");
        list.add("456");
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
            if ("123".equals(iterator.next()))
                iterator.remove();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(-1);
        return paginationInterceptor;
    }

}