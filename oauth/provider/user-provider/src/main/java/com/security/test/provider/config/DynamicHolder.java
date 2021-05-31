package com.security.test.provider.config;

import org.springframework.stereotype.Component;

@Component
public class DynamicHolder {

    private static final ThreadLocal<DbType> dynamicType = new ThreadLocal<>();

    public static DbType getDynamicType() {
        return dynamicType.get();
    }

    public static void setDynamicType(DbType dbType) {
        dynamicType.set(dbType);
    }

    public static void remove() {
        dynamicType.remove();
    }

    public enum DbType {
        DB1, DB2;
    }
}
