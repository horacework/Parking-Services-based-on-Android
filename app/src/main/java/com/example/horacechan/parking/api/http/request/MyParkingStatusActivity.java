package com.example.horacechan.parking.api.http.request;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.model.UserStatusEntity;
import com.example.horacechan.parking.util.UserParkingStatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyParkingStatusActivity extends ActionBarActivity implements BaseResponseListener {

    ListView MyParkingStatus;
    ParkingStatusInfoRequest parkingStatusInfoRequest;
    List<UserStatusEntity> datas;
    UserParkingStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_parking_status);

        MyParkingStatus = (ListView) findViewById(R.id.MyParkingStatus);

        datas = new ArrayList<>();
        adapter = new UserParkingStatusAdapter(this,datas);
        MyParkingStatus.setAdapter(adapter);

        parkingStatusInfoRequest = new ParkingStatusInfoRequest();
        parkingStatusInfoRequest.setOnResponseListener(this);
        parkingStatusInfoRequest.setRequestType(0);
        parkingStatusInfoRequest.userId = LocalHost.INSTANCE.getUserid();
        parkingStatusInfoRequest.execute();
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
                    List<UserStatusEntity> info = (List<UserStatusEntity>)response.getArrayList();
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
