package com.example.horacechan.parking.api.http.request;

import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseHttpRequestClient;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.util.RequestUrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class FeedbackRequest extends BaseHttpRequestClient {

    public String userid;
    public String tel;
    public String content;

    @Override
    public String setUrl() {
        return new RequestUrlUtils.Builder()
                .setHost(ParkingApp.HOST)
                .setPath("/userFeedback")
                .build();
    }

    @Override
    public String setTag() {
        return setDefaultTag();
    }

    @Override
    public void parseResponse(BaseResponse response, JSONObject json) throws JSONException {

        if (response.getStatus()==200){

        }

    }

    @Override
    public void postValue(Map<String, String> keyValue) {
        keyValue.put("userid",userid);
        keyValue.put("tel",tel);
        keyValue.put("content",content);
    }
}
