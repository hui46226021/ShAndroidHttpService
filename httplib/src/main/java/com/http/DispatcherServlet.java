package com.http;

import com.google.gson.Gson;
import com.http.annotation.Controller;
import com.http.annotation.RequestMapping;
import com.http.annotation.Requestparam;
import com.http.intercept.ShInterceptor;
import com.http.utils.HttpParamUtils;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhush on 17-6-7 下午7:20
 * Email:405086805@qq.com
 * Ps: http请求分发器
 */
public class DispatcherServlet implements HttpRequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response,
                       HttpContext context) throws HttpException, IOException {

        /**
         * 循环前置拦截器
         */
        for (ShInterceptor intercept : WebServer.intercepts) {
            if (!intercept.preHandle(request, response, context)) {
                return;
            }
        }
        /**
         * 调用Controller
         */

        try {
            handleReqquest(request, response, context);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            StringEntity entity = new StringEntity(
                    "<html><body><h1>Error 404, access denied.</h1></body></html>",
                    "UTF-8");
            response.setHeader("Content-Type", "text/html");
            response.setEntity(entity);
        }
        /**
         *循环后置拦截器
         */
        for (ShInterceptor intercept : WebServer.intercepts) {
            intercept.postHandle(request, response, context);
        }

    }

    private void handleReqquest(HttpRequest request, HttpResponse response,
                                HttpContext context) throws Exception {
        String target = request.getRequestLine().getUri();
        int contextStart = target.indexOf("/");
        int contextEnd = target.indexOf("/", contextStart + 1);
        String contextStr = target.substring(contextStart, contextEnd);
        String pathStr = "";
        if (target.contains("?")) {
            pathStr = target.substring(contextEnd, target.indexOf("?"));
        } else {
            pathStr = target.substring(contextEnd);
        }

        for (Object object : WebServer.controllers) {
            Class classz = object.getClass();
            Controller controllerAnn = (Controller) classz
                    .getAnnotation(Controller.class);
            if (controllerAnn != null && controllerAnn.space().equals(contextStr)) {
                Method[] methods = classz.getMethods();
                for (Method method : methods) {
                    RequestMapping requestMappingAnn = method
                            .getAnnotation(RequestMapping.class);
                    if (requestMappingAnn != null && requestMappingAnn.path().equals(pathStr)) {
                        try {


                           List<Object> paramValue= organizationParam(method,request, response, context);
//                            List<Object> paramValue = new ArrayList();
//                                for(ParamModel paramModel:paramModels){
//                                    paramValue.add(paramModel.getValue());
//                                }
                            Object responseObject  = null;
                            switch (paramValue.size()){
                                case 0:
                                    responseObject = method.invoke(object);
                                    break;
                                case 1:
                                    responseObject = method.invoke(object,paramValue.get(0));
                                    break;
                                case 2:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1));
                                    break;
                                case 3:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2));
                                    break;
                                case 4:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3));
                                    break;
                                case 5:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4));
                                    break;
                                case 6:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4),paramValue.get(5));
                                    break;
                                case 7:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4),paramValue.get(5),paramValue.get(6));
                                    break;
                                case 8:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4),paramValue.get(5),paramValue.get(6),paramValue.get(7));
                                    break;
                                case 9:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4),paramValue.get(5),paramValue.get(6),paramValue.get(7),paramValue.get(8));
                                    break;
                                case 10:
                                    responseObject = method.invoke(object,paramValue.get(0),paramValue.get(1),paramValue.get(2),paramValue.get(3),paramValue.get(4),paramValue.get(5),paramValue.get(6),paramValue.get(7),paramValue.get(8),paramValue.get(9));
                                    break;
                            }


                            response.setStatusCode(HttpStatus.SC_OK);
                            Gson gson = new Gson();
                            String responseStr = gson.toJson(responseObject);
                            StringEntity entity = new StringEntity(
                                    responseStr, "UTF-8");
                            response.setHeader("Content-Type", "text/html");
                            response.setEntity(entity);
                        } catch (Exception e) {
                            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                            StringEntity entity = new StringEntity(
                                    "<html><body><h1>Error 400, access denied.</h1></body></html>",
                                    "UTF-8");
                            response.setHeader("Content-Type", "text/html");
                            response.setEntity(entity);
                        }
                        return;
                    }
                }

            }
        }

        response.setStatusCode(HttpStatus.SC_NOT_FOUND);
        StringEntity entity = new StringEntity(
                "<html><body><h1>Error 404, access denied.</h1></body></html>",
                "UTF-8");
        response.setHeader("Content-Type", "text/html");
        response.setEntity(entity);
        return;
    }

    /**
     * 组织参数
     * @return
     */
    public List<Object> organizationParam(Method method,HttpRequest request, HttpResponse response,
                                              HttpContext context)throws Exception{

        List<Object> value = new ArrayList<>();
        String target = URLDecoder.decode(request.getRequestLine().getUri(),
                "UTF-8");
        if(!target.contains("?")){
            return value;
        }
        String params = target.substring(target.indexOf("?") + 1);

        String [] paramNmaes  =  getMethodParameterNamesByAnnotation(method);
        Class[] parameterTypes = method.getParameterTypes();
        for(int i=0;i<paramNmaes.length;i++){
            String paramValue = HttpParamUtils.getQueryParameter(params, paramNmaes[i]);
            if(parameterTypes[i].isAssignableFrom(Integer.class)){
                value.add(Integer.parseInt(paramValue));
            }
            if(parameterTypes[i].isAssignableFrom(Long.class)){
                value.add(Long.parseLong(paramValue));
            }
            if(parameterTypes[i].isAssignableFrom(String.class)){
                value.add(paramValue);
            }
            if(parameterTypes[i].isAssignableFrom(Double.class)){
                value.add(Double.parseDouble(paramValue));
            }
            if(parameterTypes[i].isAssignableFrom(HttpRequest.class)){
                value.add(request);
            }
            if(parameterTypes[i].isAssignableFrom(HttpResponse.class)){
                value.add(response);
            }
            if(parameterTypes[i].isAssignableFrom(HttpContext.class)){
                value.add(context);
            }
        }
        return value;
    }

    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    public  String[] getMethodParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        String[] parameterNames = new String[parameterAnnotations.length];
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Requestparam) {
                    Requestparam param = (Requestparam) annotation;
                    parameterNames[i++] = param.value();
                }
            }
        }
        return parameterNames;
    }
}
