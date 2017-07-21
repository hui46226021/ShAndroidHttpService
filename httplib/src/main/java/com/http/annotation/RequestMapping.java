package com.http.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps: 请求映射注解
 * 例如  path=  /getData.do
 * 跟 Controller 组成完整路径 /app/getData.do
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String path();
}
