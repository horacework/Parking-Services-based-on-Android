package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.GetMarkIdRequest;
import com.example.horacechan.parking.api.http.request.GetMarkRequest;
import com.example.horacechan.parking.api.model.MarkId;
import com.example.horacechan.parking.api.model.MarkInfo;

import java.util.List;

public class ParkingFrgment extends Fragment implements LocationSource, AMapLocationListener, OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapTouchListener, BaseResponseListener {


    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;

    private MapView mapView;
    //地图实例
    private AMap aMap;

    //定位方向

    private double currentLatitude;
    private double currentLongitude;

    //覆盖物相关
    private Marker marker2;
    private LatLng currentMarkerPosition;
    //覆盖物详细信息的布局
    private RelativeLayout mMarkerInfoLy;
    private ImageView mMarkerInfoImg;
    private ImageView mMarkerZanImg;
    private TextView mMarkerZanNum;
    private TextView mMarkerInfoName;
    private TextView mMarkerInfoDis;


    private ImageButton goToDestBtn;


    private GetMarkRequest mRequest;
    private GetMarkIdRequest markIdRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_daohang, container, false);

        //初始化页面内控件
        initWeight(view);
        //获得地图实例
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //图层显示模式
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        //交通路况图层显示
        aMap.setTrafficEnabled(true);

        //初始化定位服务
        initLocation();

        mRequest = new GetMarkRequest();
        mRequest.id = "b4850fda-047b-11e6-b034-00ff9099da81";
        mRequest.setOnResponseListener(this);
        mRequest.execute();

        markIdRequest = new GetMarkIdRequest();
        markIdRequest.setOnResponseListener(this);
        markIdRequest.setRequestType(4);
        markIdRequest.execute();


        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLng(23.0446140612, 113.3950857036));

        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.maker));

        addOverlays(ParkingInfo.infos);

        Marker marker = aMap.addMarker(markerOptions);


        // 对amap添加单击地图事件监听器
        //aMap.setOnMapClickListener(this);
        // 对amap添加触摸地图事件监听器
        aMap.setOnMapTouchListener(this);
        // 设置点击marker事件监听器
        aMap.setOnMarkerClickListener(this);
        // 设置点击infoWindow事件监听器
        aMap.setOnInfoWindowClickListener(this);
        // 设置自定义InfoWindow样式
        aMap.setInfoWindowAdapter(this);

        return view;
    }

    private void addOverlays(List<ParkingInfo> infos) {
        //批量添加覆盖物，组装addMarkers()
        aMap.clear();
        //ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<MarkerOptions>();
        //ArrayList<Marker> MarkerArrayList;
        //boolean moverToCenter = false;
        Marker marker = null;
        //构造ArrayList<MarkerOptions>
        for (final ParkingInfo info : infos) {
            LatLng latLng = new LatLng(info.getLatitude(), info.getLongitude());
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.maker))
                    .zIndex(5);
            marker = aMap.addMarker(options);
            //marker.setTitle(info.getName());
            ParkingInfo MarkerObject = new ParkingInfo(info.getLatitude(), info.getLongitude(), info.getImgId(), info.getName(), info.getDistance(), info.getZan());
            Object mmm = new Object() {
                int i = info.getImgId();
                String a = info.getName();
            };

            marker.setObject(mmm);
        }
        //MarkerArrayList = aMap.addMarkers(markerOptionsArrayList, moverToCenter);
    }

    private void initWeight(View view) {
        //地图控件
        mapView = (MapView) view.findViewById(R.id.map_view);
        //常规控件
        mMarkerInfoLy = (RelativeLayout) view.findViewById(R.id.id_marker_info);
        mMarkerInfoImg = (ImageView) view.findViewById(R.id.info_img);
        mMarkerZanImg = (ImageView) view.findViewById(R.id.zan_img);
        mMarkerZanNum = (TextView) view.findViewById(R.id.info_zan_num);
        mMarkerInfoName = (TextView) view.findViewById(R.id.info_name);
        mMarkerInfoDis = (TextView) view.findViewById(R.id.info_distance);
        //初始化导航按钮
        goToDestBtn = (ImageButton) view.findViewById(R.id.goToDestination);
        goToDestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(getActivity(), NaviActivity.class);
                //发送当前位置
                intent.putExtra("currentLatitude", currentLatitude);
                intent.putExtra("currentLongitude", currentLongitude);
                //发送目标Marker位置
                double endLatitude = currentMarkerPosition.latitude;
                double endLongitude = currentMarkerPosition.longitude;
                intent.putExtra("endLatitude", endLatitude);
                intent.putExtra("endLongitude", endLongitude);
                //创建提示框
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("提示")
                        .setMessage("选择导航模式")
                        .setPositiveButton("实时导航", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent.putExtra("isGPSNaviMode", true);
                                mMarkerInfoLy.setVisibility(View.GONE);
                                startActivityForResult(intent, 1);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setNeutralButton("模拟导航", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent.putExtra("isGPSNaviMode", false);
                                mMarkerInfoLy.setVisibility(View.GONE);
                                startActivityForResult(intent, 1);
                            }
                        })
                        .show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    Log.d("导航完成", "做点什么");
                    //TODO:自动弹出窗口询问是否要扫码入库
                } else {
                    Log.d("导航停止返回", "doSomething");
                }
        }
    }

    //摸到地图触发的事件
    @Override
    public void onTouch(MotionEvent motionEvent) {
        mMarkerInfoLy.setVisibility(View.GONE);
    }

    //点击地图触发的事件
//	@Override
//	public void onMapClick(LatLng latLng) {
//		mMarkerInfoLy.setVisibility(View.GONE);
//	}

    //点击覆盖物触发的事件
    @Override
    public boolean onMarkerClick(final Marker marker) {

//		if (marker.equals(marker2)) {
//			if (aMap != null) {
//				jumpPoint(marker);
//			}
//		}
        //Object parkingMarker = marker.getObject();
        //mMarkerInfoName.setText(parkingMarker.getClass().);
        //mMarkerInfoDis.setText(marker.getObject().getClass());
        Log.d("FFFFF", String.valueOf(marker.getObject()));
        //获取当前选定的marker的位置信息
        currentMarkerPosition = marker.getPosition();

        //Toast.makeText(getActivity(),marker.getObject(),Toast.LENGTH_SHORT);
        mMarkerInfoImg.setImageResource(R.mipmap.a02);
        mMarkerInfoLy.setVisibility(View.VISIBLE);


        return false;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initLocation() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置小蓝点的图标
//		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//				.fromResource(R.mipmap.navi_map_gps_locked));
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.argb(100, 0, 0, 180));
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
        //myLocationStyle.anchor(0.5,0.5);//设置小蓝点的锚点
        // 设置圆形的边框粗细
        myLocationStyle.strokeWidth(1.0f);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //定位成功后回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                //float x = amapLocation.getBearing();
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                currentLatitude = amapLocation.getLatitude();
                currentLongitude = amapLocation.getLongitude();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    //激活定位
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;

        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(false);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    //====================================================================

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 200) {
            switch (response.getRequestType()) {
                case 0:
                    MarkInfo info = (MarkInfo) response.getData();
                    Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_LONG).show();
                    break;
            }

        }

        if (response.getStatus()==404){
            List<MarkId> id = (List<MarkId>) response.getDatas();
        }

    }
}
