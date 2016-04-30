package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.model.UserParkingEntity;
import com.example.horacechan.parking.util.JSONUtils;
import com.example.horacechan.parking.util.RequestUrlUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParkingLogRequest extends BaseHttpRequestClient{

    public String userId;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userMyParkingLog")
                .addParam("userid",userId)
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){
            List<UserParkingEntity> info = JSONUtils.toList(json.optString("data"), new TypeToken<ArrayList<UserParkingEntity>>() {}.getType());
            response.setArrayList(info);
        }

    }


}
