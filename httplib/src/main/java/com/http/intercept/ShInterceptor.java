package com.http.intercept;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps:
 */

public interface ShInterceptor {
    /**
     * 在调用请求前调用
     * @param request
     * @param response
     * @param context
     * @return
     */
    public boolean preHandle(HttpRequest request, HttpResponse response,
                             HttpContext context) ;


    /**
     * 在调用请求后调用
     * @param request
     * @param response
     * @param context
     */
    public void postHandle(HttpRequest request, HttpResponse response, HttpContext context);
}
