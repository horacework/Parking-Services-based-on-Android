package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.model.UserEntity;
import com.example.horacechan.parking.util.JSONUtils;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HoraceChan on 2016/4/22.
 */
public class SignupRequest extends BaseHttpRequestClient{

    public String username;
    public String password;
    public String password2;
    public String deviceId;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userSignup")
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){
            UserEntity info= JSONUtils.toObject(json.optString("data"), UserEntity.class);
            response.setData(info);
        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("username",username);
        keyValue.put("password",password);
        keyValue.put("password2",password2);
        keyValue.put("deviceid",deviceId);
    }



}
