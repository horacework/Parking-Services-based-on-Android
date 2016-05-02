package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.GetMarkLocationRequest;
import com.example.horacechan.parking.api.http.request.OrderDeleteRequest;
import com.example.horacechan.parking.api.http.request.OrderListRequest;
import com.example.horacechan.parking.api.model.MarkId;
import com.example.horacechan.parking.api.model.UserOrderEntity;
import com.example.horacechan.parking.util.UserOrderListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends ActionBarActivity implements BaseResponseListener, AMapLocationListener {

    ListView MyOrderList;
    OrderListRequest orderListRequest;
    OrderDeleteRequest orderDeleteRequest;
    GetMarkLocationRequest getMarkLocationRequest;

    private List<UserOrderEntity> datas;
    private UserOrderListAdapter adapter;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        MyOrderList = (ListView) findViewById(R.id.MyOrderList);

        datas = new ArrayList<>();
        adapter = new UserOrderListAdapter(this,datas);
        MyOrderList.setAdapter(adapter);

        orderListRequest = new OrderListRequest();
        orderListRequest.setOnResponseListener(this);
        orderListRequest.setRequestType(0);

        getMarkLocationRequest = new GetMarkLocationRequest();
        getMarkLocationRequest.setOnResponseListener(this);
        getMarkLocationRequest.setRequestType(1);

        orderDeleteRequest = new OrderDeleteRequest();
        orderDeleteRequest.setOnResponseListener(this);
        orderDeleteRequest.setRequestType(2);

        itemOnClickListener();

        orderListRequestShow();

        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationClient.startLocation();

    }

    private void orderListRequestShow() {
        orderListRequest.userid = LocalHost.INSTANCE.getUserid();
        orderListRequest.execute();
    }

    private void itemOnClickListener() {
        MyOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getMarkLocationRequest.id = datas.get(i).getMarkerId();
                getMarkLocationRequest.execute();
            }
        });
        MyOrderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                comfirmDeleteOrder(i);
                return true;
            }
        });
    }

    private void comfirmDeleteOrder(final int i){
        AlertDialog.Builder isExitLog = new AlertDialog.Builder(MyOrderActivity.this);
        isExitLog.setTitle("提示")
                .setMessage("确定删除此收藏？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        deleteFavorite(i);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteFavorite(int i) {

        orderDeleteRequest.orderid = datas.get(i).getOrderId();
        orderDeleteRequest.userid = LocalHost.INSTANCE.getUserid();
        orderDeleteRequest.post();
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
        switch (response.getRequestType()){
            case 0:
                if (response.getStatus()==200){
                    List<UserOrderEntity> info = (List<UserOrderEntity>) response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (response.getStatus()==200){
                    MarkId markId = (MarkId) response.getData();
                    double endLatitude = markId.getLatitude();
                    double endLongitude = markId.getLongitude();
                    startNavi(endLatitude,endLongitude);
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (response.getStatus()==200){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                    orderListRequestShow();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startNavi(double endLatitude, double endLongitude) {
        final Intent intent = new Intent(MyOrderActivity.this, NaviActivity.class);
        //发送当前位置
        intent.putExtra("currentLatitude", currentLatitude);
        intent.putExtra("currentLongitude", currentLongitude);
        //发送目标Marker位置
        intent.putExtra("endLatitude", endLatitude);
        intent.putExtra("endLongitude", endLongitude);
        //创建提示框
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示")
                .setMessage("选择导航模式")
                .setPositiveButton("实时导航", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent.putExtra("isGPSNaviMode", true);
                        startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("取消", null)
                .setNeutralButton("模拟导航", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent.putExtra("isGPSNaviMode", false);
                        startActivityForResult(intent, 1);
                    }
                })
                .show();
    }


    //地图定位功能控制
    @Override
    public void onResume() {
        super.onResume();
        locationClient.startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0){
                currentLatitude = aMapLocation.getLatitude();//获取纬度
                currentLongitude = aMapLocation.getLongitude();//获取经度
            }else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
