package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
//
//    //声明AMapLocationClient类对象
//    public AMapLocationClient mLocationClient = null;
//    //声明定位回调监听器
//    public AMapLocationListener mLocationListener = new AMapLocationListener();
//    //声明mLocationOption对象
//    public AMapLocationClientOption mLocationOption = null;

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

        itemOnClickListener();

        FavoriteMarkerListRequestShow();

        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationClient.startLocation();

//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(false);
//        //设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //给定位客户端对象设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mlocationClient.startLocation();

    }

    private void FavoriteMarkerListRequestShow() {
        favoriteMarkerListRequest = new FavoriteMarkerListRequest();
        favoriteMarkerListRequest.setOnResponseListener(this);
        favoriteMarkerListRequest.setRequestType(0);
        favoriteMarkerListRequest.userId = LocalHost.INSTANCE.getUserid();
        favoriteMarkerListRequest.execute();
    }

    private void itemOnClickListener() {
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击事件
                //TODO:新Activity导航到目的地-->获取当前位置-->获取marker位置-->导航
                Toast.makeText(MyFavoriteActivity.this, String.valueOf(currentLatitude),Toast.LENGTH_LONG).show();
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
        favoriteMarkerDeleteRequest = new FavoriteMarkerDeleteRequest();
        favoriteMarkerDeleteRequest.setOnResponseListener(this);
        favoriteMarkerDeleteRequest.setRequestType(1);
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
        }
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
