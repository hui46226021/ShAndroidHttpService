package code.zsh.sh.com.shandroidhttpservice;

import android.app.Application;

import code.zsh.sh.com.shandroidhttpservice.httpservice.HttpServiceHelper;

/**
 * Created by zhush on 2017/7/21.
 * Email 405086805@qq.com
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        /**
         * 启动HTTP_SERVICE服务
         */
        HttpServiceHelper.instance().startHttpService();
    }
}
