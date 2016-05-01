package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.CarDeleteRequest;
import com.example.horacechan.parking.api.http.request.CarListRequest;
import com.example.horacechan.parking.api.model.UserCarEntity;
import com.example.horacechan.parking.util.UserCarListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCarActivity extends ActionBarActivity implements BaseResponseListener {

    ListView MyCaritem;
    CarListRequest carListRequest;
    CarDeleteRequest carDeleteRequest;
    Button addCarBtn;

    private List<UserCarEntity> datas;
    private UserCarListAdapter adapter;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        MyCaritem = (ListView) findViewById(R.id.MyCaritem);
        addCarBtn = (Button) findViewById(R.id.addCarBtn);

        datas = new ArrayList<>();
        adapter = new UserCarListAdapter(this,datas);
        MyCaritem.setAdapter(adapter);

        carDeleteRequest = new CarDeleteRequest();
        carDeleteRequest.setOnResponseListener(this);
        carDeleteRequest.setRequestType(1);

        editor = getSharedPreferences("ParkingApp", MODE_PRIVATE).edit();

        itemOnClickListener();

        carListRequestShow();

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCarActivity.this, MyCarAddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void itemOnClickListener() {
        //点击选中
        MyCaritem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserCarEntity userCarEntity = datas.get(i);
                //持久化存储
                //SharedPreferences.Editor editor = getSharedPreferences("ParkingApp", MODE_PRIVATE).edit();
                editor.putString("userCar", userCarEntity.getPlate());
                editor.apply();
                //全局变量
                LocalHost.INSTANCE.setUserCar(userCarEntity.getPlate());
                setResult(200);
                finish();
            }
        });
        //长按删除
        MyCaritem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deleteCar(i);
                return true;
            }
        });
    }

    private void deleteCar(final int i) {
        AlertDialog.Builder isExitLog = new AlertDialog.Builder(MyCarActivity.this);
        isExitLog.setTitle("提示")
                .setMessage("确定删除此车牌？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        userCarDelete(i);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void userCarDelete(int i) {

        UserCarEntity userCar = datas.get(i);

        carDeleteRequest.userid = LocalHost.INSTANCE.getUserid();
        carDeleteRequest.carid = userCar.getCarId();
        if (userCar.getPlate().equals(LocalHost.INSTANCE.getUserCar())){
            editor.remove("userCar");
            editor.apply();
            LocalHost.INSTANCE.setUserCar("");
            setResult(300);
        }
        carDeleteRequest.post();

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
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
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
                case 1:
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;

            }
        }else if (response.getStatus()==404){
            Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
