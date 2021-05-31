package com.security.test.provider.aop.annotation;


import com.security.test.provider.config.DynamicHolder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SourceType {

    DynamicHolder.DbType value() default DynamicHolder.DbType.DB2;
}
