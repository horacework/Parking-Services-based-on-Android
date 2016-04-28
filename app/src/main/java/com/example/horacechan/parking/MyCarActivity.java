package com.example.horacechan.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.CarListRequest;
import com.example.horacechan.parking.api.model.UserCarEntity;
import com.example.horacechan.parking.util.UserCarListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCarActivity extends ActionBarActivity implements BaseResponseListener {

    private ListView MyCaritem;
    private CarListRequest carListRequest;
    private Button addCarBtn;

    private List<UserCarEntity> datas;
    private UserCarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        MyCaritem = (ListView) findViewById(R.id.MyCaritem);
        addCarBtn = (Button) findViewById(R.id.addCarBtn);

        datas = new ArrayList<>();
        adapter = new UserCarListAdapter(this,datas);
        MyCaritem.setAdapter(adapter);

        carListRequestShow();

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCarActivity.this,MyCarAddActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    private void carListRequestShow() {
        carListRequest = new CarListRequest();
        carListRequest.setOnResponseListener(this);
        carListRequest.setRequestType(0);
        carListRequest.userId = LocalHost.INSTANCE.getUserid();
        carListRequest.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200){
            //刷新本Activity的Listview数据
            carListRequestShow();
        }
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus()==200){
            switch (response.getRequestType()){
                case 0:
                    List<UserCarEntity> info = (List<UserCarEntity>)response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
