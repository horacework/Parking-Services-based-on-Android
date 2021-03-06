package com.example.horacechan.parking.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.horacechan.parking.api.http.base.VolleyManager;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class ParkingApp extends Application {

    public static Context APP_CONTEXT;

    public static final String HOST="http://119.29.163.234:8080/parking/client/";

    public static final String BaiduAk="tca2mmjlwcGmPnRcbEu9t7n3mecqUYKa";

    // 读
    public static SharedPreferences sPreferences;
    // 写
    public static SharedPreferences.Editor sEditor;


    @Override
    public void onCreate() {
        super.onCreate();

        LocalHost.INSTANCE.init();

        //读取SharePreference内数据到全局
        sPreferences = getSharedPreferences("ParkingApp", MODE_PRIVATE);
        //sEditor = sPreferences.edit();
        String userIdTemp = sPreferences.getString("userId", "");
        String userNameTemp = sPreferences.getString("userName","");
        String userCarTemp = sPreferences.getString("userCar","");
        if (userIdTemp.equals("")){
            LocalHost.INSTANCE.setUserid(null);
            LocalHost.INSTANCE.setUserName(null);
        }else {
            LocalHost.INSTANCE.setUserid(userIdTemp);
            LocalHost.INSTANCE.setUserName(userNameTemp);
        }
        LocalHost.INSTANCE.setUserCar(userCarTemp);


        APP_CONTEXT=getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10<<10<<10);
    }
}
