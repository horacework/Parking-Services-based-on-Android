package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.model.UserEntity;
import com.example.horacechan.parking.util.JSONUtils;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Map;

public class OrderPostRequest extends BaseHttpRequestClient{

    public String userid;
    public String markerid;
    public Timestamp ordertime;
    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userOrderBook")
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
        keyValue.put("userid",userid);
        keyValue.put("markerid",markerid);
        keyValue.put("ordertime",String.valueOf(ordertime.getTime()));
    }



}
