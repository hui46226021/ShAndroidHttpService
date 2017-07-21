package code.zsh.sh.com.shandroidhttpservice.httpservice.intercepts;


import android.util.Log;

import com.http.intercept.ShInterceptor;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import java.net.URLDecoder;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps:
 */

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
