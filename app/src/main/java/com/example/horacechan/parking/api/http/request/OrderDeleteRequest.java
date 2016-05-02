package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class OrderDeleteRequest extends BaseHttpRequestClient{

    public String orderid;
    public String userid;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userOrderDelete")
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){

        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("orderid",orderid);
        keyValue.put("userid",userid);
    }
}
