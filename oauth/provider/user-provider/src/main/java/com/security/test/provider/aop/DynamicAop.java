package com.security.test.provider.aop;

import com.security.test.provider.aop.annotation.SourceType;
import com.security.test.provider.config.DynamicHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Order(-1)
public class DynamicAop {

    @Pointcut("@annotation(com.security.test.provider.aop.annotation.SourceType)")
    public void SourceAspect() {
    }

    @Before("SourceAspect()")
    public void before(JoinPoint joinPoint) {
        log.info("{}.{} 调用，参数位{}", joinPoint.getSignature().getDeclaringType().getName(), joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SourceType annotation = methodSignature.getMethod().getAnnotation(SourceType.class);
        if (annotation != null) {
            DynamicHolder.setDynamicType(annotation.value());
        }
    }

    @After("SourceAspect()")
    public void after() {
        DynamicHolder.remove();
    }
}
