package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class FavoriteCheckRequest extends BaseHttpRequestClient{

    public String userId;
    public String markerId;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userFavoriteCheck")
                .addParam("userid",userId)
                .addParam("markerid",markerId)
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {


    }


}
