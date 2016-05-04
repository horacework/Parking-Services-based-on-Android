package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class ScanQRRequest extends BaseHttpRequestClient{

    public String logid;
    public String userid;
    public String carid;
    public String markerid;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userScanQR")
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("logid",logid);
        keyValue.put("userid",userid);
        keyValue.put("plate",carid);
        keyValue.put("markerid",markerid);
    }

}
