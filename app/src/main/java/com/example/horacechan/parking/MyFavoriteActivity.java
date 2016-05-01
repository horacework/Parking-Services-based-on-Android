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
import com.example.horacechan.parking.api.http.request.FavoriteMarkerDeleteRequest;
import com.example.horacechan.parking.api.http.request.FavoriteMarkerListRequest;
import com.example.horacechan.parking.api.http.request.GetMarkLocationRequest;
import com.example.horacechan.parking.api.model.MarkId;
import com.example.horacechan.parking.api.model.UserFavoriteEntity;
import com.example.horacechan.parking.util.UserFavoriteListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteActivity extends ActionBarActivity implements BaseResponseListener,AMapLocationListener {

    ListView favoriteList;

    private List<UserFavoriteEntity> datas;
    private UserFavoriteListAdapter adapter;

    FavoriteMarkerListRequest favoriteMarkerListRequest;
    FavoriteMarkerDeleteRequest favoriteMarkerDeleteRequest;
    GetMarkLocationRequest getMarkLocationRequest;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        favoriteList = (ListView) findViewById(R.id.favoriteList);

        datas = new ArrayList<>();
        adapter = new UserFavoriteListAdapter(this,datas);
        favoriteList.setAdapter(adapter);

        //初始化请求
        favoriteMarkerListRequest = new FavoriteMarkerListRequest();
        favoriteMarkerListRequest.setOnResponseListener(this);
        favoriteMarkerListRequest.setRequestType(0);

        favoriteMarkerDeleteRequest = new FavoriteMarkerDeleteRequest();
        favoriteMarkerDeleteRequest.setOnResponseListener(this);
        favoriteMarkerDeleteRequest.setRequestType(1);

        getMarkLocationRequest = new GetMarkLocationRequest();
        getMarkLocationRequest.setOnResponseListener(this);
        getMarkLocationRequest.setRequestType(2);

        itemOnClickListener();

        FavoriteMarkerListRequestShow();




        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationClient.startLocation();

    }

    private void FavoriteMarkerListRequestShow() {

        favoriteMarkerListRequest.userId = LocalHost.INSTANCE.getUserid();
        favoriteMarkerListRequest.execute();
    }

    private void itemOnClickListener() {
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击事件，一键导航
                getMarkLocationRequest.id = datas.get(i).getMarkerId();
                getMarkLocationRequest.execute();
            }
        });
        favoriteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //长按事件，取消收藏该停车场
                comfirmdeleteFavorite(i);
                return true;
            }
        });

    }

    private void comfirmdeleteFavorite(final int i){
        AlertDialog.Builder isExitLog = new AlertDialog.Builder(MyFavoriteActivity.this);
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

        favoriteMarkerDeleteRequest.id = datas.get(i).getId();
        favoriteMarkerDeleteRequest.userid = LocalHost.INSTANCE.getUserid();
        favoriteMarkerDeleteRequest.post();
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
                    List<UserFavoriteEntity> info = (List<UserFavoriteEntity>) response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (response.getStatus()==200){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                    FavoriteMarkerListRequestShow();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (response.getStatus()==200){
                    MarkId markId = (MarkId) response.getData();
                    double endLatitude = markId.getLatitude();
                    double endLongitude = markId.getLongitude();
                    startNavi(endLatitude,endLongitude);
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startNavi(double endLatitude, double endLongitude) {
        final Intent intent = new Intent(MyFavoriteActivity.this, NaviActivity.class);
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
