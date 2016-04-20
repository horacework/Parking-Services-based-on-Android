package com.example.horacechan.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.NaviLatLng;

public class NaviActivity extends BaseNaviActivity {

    private double Latitude;
    private double Longitude;
    private NaviLatLng mStartLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navi);

        Intent intent = getIntent();
        Latitude = intent.getDoubleExtra("Latitude", 0.0);
        Longitude = intent.getDoubleExtra("Longitude",0.0);

        mStartLatlng = new NaviLatLng(Latitude, Longitude);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        //noStartCalculate();
    }

//    private void noStartCalculate() {
//        //无起点算路须知：
//        //AMapNavi在构造的时候，会startGPS，但是GPS启动需要一定时间
//        //在刚构造好AMapNavi类之后立刻进行无起点算路，会立刻返回false
//        //给人造成一种等待很久，依然没有算路成功 算路失败回调的错觉
//        //因此，建议，提前获得AMapNavi对象实例，并判断GPS是否准备就绪
//
//        if (mAMapNavi.isGpsReady()) {
//            mAMapNavi.calculateDriveRoute(mEndList, null, PathPlanningStrategy.DRIVING_DEFAULT);
//        }else {
//            Log.d("naviSSSS","位置获取失败啦啦啦啦");
//            Toast.makeText(NaviActivity.this,"GPS不够快啊！",Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);

    }

    @Override
    public void onCalculateRouteSuccess() {
        mAMapNavi.startNavi(AMapNavi.GPSNaviMode);
    }

}
