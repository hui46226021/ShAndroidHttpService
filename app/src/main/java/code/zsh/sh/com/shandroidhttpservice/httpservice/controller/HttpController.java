package code.zsh.sh.com.shandroidhttpservice.httpservice.controller;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import com.http.annotation.Controller;
import com.http.annotation.RequestMapping;
import com.http.annotation.Requestparam;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import code.zsh.sh.com.shandroidhttpservice.JRDeviceUtils;
import code.zsh.sh.com.shandroidhttpservice.MainActivity;
import code.zsh.sh.com.shandroidhttpservice.MyApplication;
import code.zsh.sh.com.shandroidhttpservice.httpservice.ResultModel;
import code.zsh.sh.com.shandroidhttpservice.httpservice.Utils;


/**
 * Created by zhush on 17-6-7.
 * Email:405086805@qq.com
 * Ps:
 */
@Controller(space = "/api")
public class HttpController {
    /**
     * 获取手机信息
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(path = "/getphoneinof")
    public ResultModel getPhoneInof()  {
        ResultModel resultModel = new ResultModel(true);
        //获取手机号码
        HashMap<String, Object> map = null;
        try {
            TelephonyManager tm = (TelephonyManager) MyApplication.myApplication.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = JRDeviceUtils.getAndroidID(MyApplication.myApplication);
            String te1 = JRDeviceUtils.getPhoneNumber(MyApplication.myApplication);
            String imei = JRDeviceUtils.getIMEI(MyApplication.myApplication);
            String imsi = JRDeviceUtils.getSIMSerial(MyApplication.myApplication);
            String model = JRDeviceUtils.getModel();
            String BuildBrand = JRDeviceUtils.getBuildBrand();
            String getBuildUser = JRDeviceUtils.getBuildUser();
            String[] getSupportedABIS = JRDeviceUtils.getSupportedABIS();
            map = new HashMap<>();
            map.put("deviceid", deviceid);
            map.put("te1", te1);
            map.put("imei", imei);
            map.put("imsi", imsi);
            map.put("immodelsi", model);
            map.put("BuildBrand", BuildBrand);
            map.put("BuildUser", getBuildUser);
            map.put("getSupportedABIS", getSupportedABIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultModel.setData(map);
        return resultModel;
    }

    @RequestMapping(path = "/toast")
    public ResultModel toast(@Requestparam("message") String message)  {
        ResultModel resultModel = new ResultModel(true);
        MainActivity.mainActivity.toast(message);
        return resultModel;
    }
}
