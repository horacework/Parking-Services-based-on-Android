package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.NaviLatLng;

public class NaviActivity extends BaseNaviActivity {

    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private NaviLatLng mStartLatlng;
    private boolean isGPSNaviMode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navi);

        Intent intent = getIntent();
        startLatitude = intent.getDoubleExtra("currentLatitude", 0.0);
        startLongitude = intent.getDoubleExtra("currentLongitude",0.0);

        endLatitude = intent.getDoubleExtra("endLatitude",0.0);
        endLongitude = intent.getDoubleExtra("endLongitude",0.0);

        isGPSNaviMode = intent.getBooleanExtra("isGPSNaviMode",false);

        mStartLatlng = new NaviLatLng(startLatitude, startLongitude);
        mEndLatlng = new NaviLatLng(endLatitude, endLongitude);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "初始化导航失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onInitNaviSuccess() {
        Toast.makeText(this, "初始化导航成功，正在为你规划路线", Toast.LENGTH_SHORT).show();
        mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);

    }
    @Override
    public void onStartNavi(int type) {
        Toast.makeText(this, "开始为您导航", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEndEmulatorNavi() {
        Toast.makeText(this, "路径导航模拟结束", Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }

    @Override
    public void onArriveDestination() {
        Toast.makeText(this, "已到达指定目的地", Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }
    @Override
    public void onCalculateRouteSuccess() {
        Toast.makeText(this, "规划路线成功", Toast.LENGTH_SHORT).show();
        if (isGPSNaviMode){
            mAMapNavi.startNavi(AMapNavi.GPSNaviMode);
        }else {
            mAMapNavi.startNavi(AMapNavi.EmulatorNaviMode);
        }
    }
    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            AlertDialog.Builder isExit = new AlertDialog.Builder(NaviActivity.this);
            isExit.setTitle("提示")
                    .setMessage("确定退出导航？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(0);
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }

        return false;
    }

}
