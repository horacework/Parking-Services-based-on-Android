package com.example.horacechan.parking;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.MoneyLogRequest;
import com.example.horacechan.parking.api.model.UsermoneyEntity;
import com.example.horacechan.parking.util.MoneyLogAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoneylogActivity extends ActionBarActivity implements BaseResponseListener {

    MoneyLogRequest moneyLogRequest;
    ListView moneyLogList;

    private List<UsermoneyEntity> datas;
    private MoneyLogAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneylog);

        moneyLogList = (ListView) findViewById(R.id.moneyLogList);
        datas=new ArrayList<>();
        adapter=new MoneyLogAdapter(this,datas);
        moneyLogList.setAdapter(adapter);

        moneyLogRequest = new MoneyLogRequest();
        moneyLogRequest.setOnResponseListener(this);
        moneyLogRequest.setRequestType(0);
        moneyLogRequest.userId = LocalHost.INSTANCE.getUserid();
        moneyLogRequest.execute();
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
                    List<UsermoneyEntity> info = (List<UsermoneyEntity>)response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();


                    Toast.makeText(this, "数据返回成功", Toast.LENGTH_LONG).show();
                    break;
            }
        }else if (response.getStatus()==404){
            Toast.makeText(this, response.getMsg(), Toast.LENGTH_LONG).show();
        }
    }
}
