package com.example.horacechan.parking.api;

import android.app.Application;
import android.content.Context;

import com.example.horacechan.parking.api.http.base.VolleyManager;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class ParkingApp extends Application {

    public static Context APP_CONTEXT;

    public static final String HOST="http://192.168.199.246:8080/client";

    @Override
    public void onCreate() {
        super.onCreate();

        APP_CONTEXT=getApplicationContext();
        VolleyManager.INSTANCE.initQueue(10<<10<<10);
    }
}
