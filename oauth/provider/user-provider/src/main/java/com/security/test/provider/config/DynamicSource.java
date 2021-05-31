package com.security.test.provider.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据库路由，决定使用何数据库进行数据库连接获取
 */
public class DynamicSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicHolder.getDynamicType();
    }
}
