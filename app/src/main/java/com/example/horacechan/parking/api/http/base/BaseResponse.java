package com.example.horacechan.parking.api.http.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoraceChan on 2016/4/21.
 * 裝json返回常見字段對應成員變量
 */
public class BaseResponse {

    // 請求識別碼
    private int status;
    // 多個請求區分嗎
    private int requestType;
    // json返回信息->String
    private String jsonStr;
    // 消息
    private String msg;
    // jsonArray保存
    private ArrayList<Object> datas=new ArrayList<>();
    // jsonObject保存
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
