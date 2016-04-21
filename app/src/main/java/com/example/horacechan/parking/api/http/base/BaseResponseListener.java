package com.example.horacechan.parking.api.http.base;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public interface BaseResponseListener {

    void onStart(BaseResponse response);
    void onFailure(BaseResponse response);
    void onSuccess(BaseResponse response);
}
