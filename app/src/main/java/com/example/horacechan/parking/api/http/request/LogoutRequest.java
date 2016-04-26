package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class LogoutRequest extends BaseHttpRequestClient{

    public String userId;
    public String deviceId;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userLogout")
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

//        if (response.getStatus()==200){
//            UserEntity info= JSONUtils.toObject(json.optString("data"), UserEntity.class);
//            response.setData(info);
//        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("userid",userId);
        keyValue.put("deviceid",deviceId);
    }



}
