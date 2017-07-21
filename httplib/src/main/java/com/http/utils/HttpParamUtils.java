package com.http.utils;


import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps:
 */

public class HttpParamUtils {
    private static final String PARAM_PATTERN = "([^&=]+)(=.*?)?(&|$)";
    /**
     * uri解码
     *
     * @param c
     * @return
     */
    public static String decodeUriComponent(String c) {
        try {
            return URLDecoder.decode(c, "utf-8");
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从queryString中取出参数(只提取第一个参数)
     *
     * @param query
     * @param paramName
     * @return
     */
    public static String getQueryParameter(String query, String paramName) {
        if (query == null || query.length() == 0)
            return null;
        Pattern p = Pattern.compile(PARAM_PATTERN);
        Matcher m = p.matcher(query);
        while (m.find()) {
            String key = decodeUriComponent(m.group(1));
            if (key.equals(paramName))
                return m.group(2).substring(1);
        }
        return null;
    }
}
