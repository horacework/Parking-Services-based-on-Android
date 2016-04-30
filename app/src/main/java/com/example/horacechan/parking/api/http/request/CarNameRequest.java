package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.model.UserCarEntity;
import com.example.horacechan.parking.util.JSONUtils;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class CarNameRequest extends BaseHttpRequestClient {

    public String carid;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userCarName")
                .addParam("carid",carid)
                .build();
    }

    @Override
    public String setTag() {
        return setDefaultTag();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){
            UserCarEntity info= JSONUtils.toObject(json.optString("data"), UserCarEntity.class);
            response.setData(info);
        }

    }

}
