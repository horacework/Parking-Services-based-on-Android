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

    public static final String HOST="http://192.168.199.246:8080/client";

    // 读
    public static SharedPreferences sPreferences;
    // 写
    public static SharedPreferences.Editor sEditor;


    @Override
    public void onCreate() {
        super.onCreate();

        LocalHost.INSTANCE.init();

        sPreferences=getSharedPreferences("ParkingApp",MODE_PRIVATE);
        sEditor=sPreferences.edit();

        APP_CONTEXT=getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10<<10<<10);
    }
}
