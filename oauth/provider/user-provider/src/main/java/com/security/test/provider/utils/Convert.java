package com.security.test.provider.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public abstract class Convert<T> implements Serializable {

    private static final Long serialVersionUID = 1L;

    @TableField(exist = false)
    private Class<T> clazz;

    public Convert() {
        //当前对象的直接超类的 Type
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            //参数化类型
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.clazz = (Class<T>) actualTypeArguments[0];
        } else {
            this.clazz = (Class<T>) genericSuperclass;
        }

    }

    public T convert() {
        try {
            T t = this.clazz.newInstance();
            BeanUtils.copyProperties(this, t);
            return t;
        } catch (InstantiationException e) {
            log.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
        return null;
    }
}
