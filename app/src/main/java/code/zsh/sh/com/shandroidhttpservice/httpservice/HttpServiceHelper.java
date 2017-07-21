package code.zsh.sh.com.shandroidhttpservice.httpservice;

import com.http.WebServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import code.zsh.sh.com.shandroidhttpservice.MyApplication;
import dalvik.system.DexFile;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps: httpservice帮助类
 */

public class HttpServiceHelper {

    /**
     * 单例模式
     */
    private static HttpServiceHelper mInstance;
    public static HttpServiceHelper instance() {
        if(mInstance==null){
              mInstance = new HttpServiceHelper();
        }
        return mInstance;
    }
    //端口号
    public static final int PORT = 8088;
    private WebServer webServer;
    final String CONTROLLER ="code.zsh.sh.com.shandroidhttpservice.httpservice.controller";
    final String INTERCEPTS ="code.zsh.sh.com.shandroidhttpservice.httpservice.intercepts";
    /**
     * 启动HTTP
     */
    public void startHttpService(){
        webServer = new WebServer(PORT);
        webServer.setControllerName(getClassName(CONTROLLER));
        webServer.setInterceptPName(getClassName(INTERCEPTS));
        webServer.setDaemon(true);
        webServer.start();
    }


    /**
     * 关闭HTTP
     */
    public void closeHttpService(){
        if(webServer!=null){
            webServer.close();
        }
    }

    /**
     * 获取包下的所有类名
     * @param packageName
     * @return
     */
    public  List<String > getClassName(String packageName){
        List<String >classNameList=new ArrayList<String >();
        try {
            DexFile df = new DexFile(MyApplication.myApplication.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();
                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classNameList;
    }

}
