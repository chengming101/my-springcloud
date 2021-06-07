package com.security.test.provider.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源，可用作数据库主从读写分离 {@link com.security.test.provider.config.DynamicHolder}
 */

@Configuration
public class DynamicSourceConfig {

    @Bean
    @ConfigurationProperties(value = "spring.datasource.db1")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(value = "spring.datasource.db2")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DynamicSource dynamicSource() {
        DynamicSource dynamicSource = new DynamicSource();
        Map<Object, Object> map = new HashMap<>(2);
        map.put(DynamicHolder.DbType.DB1, dataSourceOne());
        map.put(DynamicHolder.DbType.DB2, dataSourceTwo());
        dynamicSource.setTargetDataSources(map);
        dynamicSource.afterPropertiesSet();
        dynamicSource.setDefaultTargetDataSource(dataSourceTwo());
        return dynamicSource;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicSource dynamicSource) throws Exception {
        // TODO 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicSource);
        return sqlSessionFactoryBean.getObject();

    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DynamicSource dynamicSource) {
        return new DataSourceTransactionManager(dynamicSource);
    }
}