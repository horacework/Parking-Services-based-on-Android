package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.model.BaiduPOIEntity;
import com.example.horacechan.parking.util.JSONUtils;
import com.example.horacechan.parking.util.RequestUrlUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AroundPOIRequest extends BaseHttpRequestClient{

    public String query;
    public String location;
    public String radius;
    public String output;
    public String ak;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost("http://api.map.baidu.com")
                .setPath("/place/v2/search")
                .addParam("query",query)
                .addParam("location",location)
                .addParam("radius",radius)
                .addParam("output",output)
                .addParam("ak",ak)
                .build();
    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==0){
            List<BaiduPOIEntity> info= JSONUtils.toList(json.optString("results"), new TypeToken<ArrayList<BaiduPOIEntity>>() {}.getType());
            response.setArrayList(info);
        }

    }


}
