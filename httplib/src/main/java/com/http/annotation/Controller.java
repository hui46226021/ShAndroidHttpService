package com.http.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps: 控制器注解
 * space 作用于  例如 space =/app
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String space();
}
