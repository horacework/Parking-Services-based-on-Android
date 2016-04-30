package com.example.horacechan.parking;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.ParkingLogRequest;
import com.example.horacechan.parking.api.model.UserParkingEntity;
import com.example.horacechan.parking.util.UserParkingLogAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyParkinglogActivity extends ActionBarActivity implements BaseResponseListener {

    private ParkingLogRequest parkingLogRequest;
    private ListView parkingLogList;

    private List<UserParkingEntity> datas;
    private UserParkingLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_parkinglog);

        parkingLogList = (ListView) findViewById(R.id.parkingLogList);
        datas = new ArrayList<>();
        adapter = new UserParkingLogAdapter(this,datas);
        parkingLogList.setAdapter(adapter);

        parkingLogRequest = new ParkingLogRequest();
        parkingLogRequest.setOnResponseListener(this);
        parkingLogRequest.setRequestType(0);
        parkingLogRequest.userId = LocalHost.INSTANCE.getUserid();
        parkingLogRequest.execute();
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus()==200){
            switch (response.getRequestType()){
                case 0:
                    List<UserParkingEntity> info = (List<UserParkingEntity>)response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }else if (response.getStatus()==404){
            Toast.makeText(this, response.getMsg(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
