package com.http.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps: 参数注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Requestparam {
    String value();
    boolean required() default true;
}
