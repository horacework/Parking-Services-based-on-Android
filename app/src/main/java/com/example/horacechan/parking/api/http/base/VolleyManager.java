package com.example.horacechan.parking.api.http.base;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.horacechan.parking.api.ParkingApp;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public enum  VolleyManager {
    INSTANCE;


    //volley队列
    private RequestQueue mRequestQueue = null;

    public void initQueue(int maxCacheByte) {
        mRequestQueue = Volley.newRequestQueue(ParkingApp.APP_CONTEXT, maxCacheByte);
    }

    public void addQueue(Request request) {
        mRequestQueue.add(request);
    }

    public void stopRequest(Object tag) {
        if (tag != null) mRequestQueue.cancelAll(tag);
    }

}
