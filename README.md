# ShAndroidHttpService
Android http服务器

启动http服务

~~~java
    WebServer  webServer = new WebServer(PORT);
            //配置controller 集合
            webServer.setControllerName(getClassName(CONTROLLER));
            //配置拦截器 集合
            webServer.setInterceptPName(getClassName(INTERCEPTS));
            webServer.setDaemon(true);
            webServer.start();
~~~

## ShInterceptor  拦截器
继承ShInterceptor  实现preHandle 和postHandle 方法 
前着在收到请求前调用  后者在完成请求后调用
~~~java
public class HttpIntercepts implements ShInterceptor {
    @Override
    public boolean preHandle(HttpRequest request, HttpResponse response, HttpContext context) {
        try {
            String target = URLDecoder.decode(request.getRequestLine().getUri(),
                    "UTF-8");

            Log.e("url=", target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpRequest request, HttpResponse response, HttpContext context) {

    }
}
~~~
## Controller  Action
### @Controller
改controller的作用域，
### @RequestMapping
调用的action 
下面的写法最终生成的地址  xxx.xxx.xxx.xxx:xxxx/api/getphoneinof
返回值以json类型返回
~~~java 
@Controller(space = "/api")
public class HttpController {
 
    @RequestMapping(path = "/getphoneinof")
    public ResultModel getPhoneInof()  {
         ResultModel resultModel = new ResultModel(true);
        return resultModel;
    }
}
~~~
### 传递参数
在方法的前面加上@Requestparam 即可
~~~java 
   @RequestMapping(path = "/toast")
    public ResultModel toast(@Requestparam("message") String message)  {
        ResultModel resultModel = new ResultModel(true);
        MainActivity.mainActivity.toast(message);
        return resultModel;
    }
~~~