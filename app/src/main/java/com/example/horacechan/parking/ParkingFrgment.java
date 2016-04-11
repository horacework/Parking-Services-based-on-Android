package com.example.horacechan.parking;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;


import java.util.List;

public class ParkingFrgment extends Fragment {

	//private WebView webView;

//	private TextView positionTextView;
//	private LocationManager locationManager;
//	private String provider;

	//定位的客户端
	private LocationClient mLocationClient;
	//定位的监听器
	public MyLocationListener mMyLocationListener;
	//当前定位的模式
	private LocationMode mCurrentMode = LocationMode.NORMAL;
	//是否是第一次定位
	private volatile boolean isFristLocation = true;
	//地图控件
	private MapView mapView;
	//地图实例
	private BaiduMap mBaiduMap;
	//经纬度
	private double mCurrentLantitude;
	private double mCurrentLongitude;
	//当前精度
	private float mCurrentAccracy;
	//方向传感器的监听器
	private MyOrientationListener myOrientationListener;
	//方向传感器X方向的值
	private int mXDirection;

	//覆盖物相关
	private  BitmapDescriptor mIconMarker;
	//覆盖物详细信息的布局
	private RelativeLayout mMarkerInfoLy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		mBMapMan=new BMapManager(getActivity().getApplication());
//
//		mBMapMan.init("3OGgTnIbjaRo9qiCDcKejjbQjOu9dXzQ", null);
		SDKInitializer.initialize(getActivity().getApplicationContext());
		View view = inflater.inflate(R.layout.tab_daohang, container, false);

		//内置浏览器应用
//		webView = (WebView)view.findViewById(R.id.web_view);
//
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebViewClient(new WebViewClient());
//		webView.loadUrl("http://www.baidu.com");


		//定位功能
//		positionTextView = (TextView)view.findViewById(R.id.position_text);
//		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//		//获取所有可用的位置提供器
//		List<String> providerList = locationManager.getProviders(true);
//		if (providerList.contains(LocationManager.GPS_PROVIDER)){
//			provider = LocationManager.GPS_PROVIDER;
//		}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
//			provider = locationManager.NETWORK_PROVIDER;
//		}else {
//			//当前没有可用的位置提供器时，弹出Toast提醒
//			Toast.makeText(this.getActivity(),"No location provider to use",Toast.LENGTH_SHORT).show();
//		}
//		Location location = locationManager.getLastKnownLocation(provider);
//		if (location!=null){
//			showLocation(location);
//
//		}
//
//		locationManager.requestLocationUpdates(provider,5000,1,locationListener);
//
		isFristLocation = true;

		mapView = (MapView)view.findViewById(R.id.map_view);
		//获得地图实例
		mBaiduMap = mapView.getMap();
		mIconMarker = BitmapDescriptorFactory.fromResource(R.mipmap.maker);
		mMarkerInfoLy = (RelativeLayout)view.findViewById(R.id.id_marker_info);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		//初始化定位
		initMyLocation();
		//初始化传感器
		initOritationListener();

		//initMarkerClickEvent();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// 获得marker中的数据
				Bundle extraInfo = marker.getExtraInfo();
				ParkingInfo info = (ParkingInfo) extraInfo.getSerializable("info");
				ImageView iv = (ImageView) mMarkerInfoLy
						.findViewById(R.id.info_img);
				TextView distance = (TextView) mMarkerInfoLy
						.findViewById(R.id.info_distance);
				TextView name = (TextView) mMarkerInfoLy
						.findViewById(R.id.info_name);
				TextView zan = (TextView) mMarkerInfoLy
						.findViewById(R.id.info_zan);
				iv.setImageResource(info.getImgId());
				distance.setText(info.getDistance());
				name.setText(info.getName());
				zan.setText(info.getZan() + "");

				InfoWindow infoWindow;
				TextView tv = new TextView(getActivity().getBaseContext());
				tv.setBackgroundResource(R.mipmap.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(info.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));
				BitmapDescriptor lobd = BitmapDescriptorFactory.fromView(tv);
				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 47;
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

				infoWindow = new InfoWindow(lobd, ll, 0,
						new OnInfoWindowClickListener()
						{
							@Override
							public void onInfoWindowClick()
							{
								mBaiduMap.hideInfoWindow();
							}
						});

				Log.e("TTTTT", "onMarkerClick: TTTTTT" );
				mBaiduMap.showInfoWindow(infoWindow);
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				return true;
			}
		});
		initMapClickEvent();

		addInfosOverlay(ParkingInfo.infos);

		return view;
	}

	private void initMapClickEvent()
	{
		mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
		{

			@Override
			public boolean onMapPoiClick(MapPoi arg0)
			{
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0)
			{
				mMarkerInfoLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();

			}
		});
	}

//	private void initMarkerClickEvent() {
//		// 对Marker的点击
//		mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//			@Override
//			public boolean onMarkerClick(final Marker marker) {
//				// 获得marker中的数据
//				Bundle extraInfo = marker.getExtraInfo();
//				ParkingInfo info = (ParkingInfo) extraInfo.getSerializable("info");
//				ImageView iv = (ImageView) mMarkerInfoLy
//						.findViewById(R.id.info_img);
//				TextView distance = (TextView) mMarkerInfoLy
//						.findViewById(R.id.info_distance);
//				TextView name = (TextView) mMarkerInfoLy
//						.findViewById(R.id.info_name);
//				TextView zan = (TextView) mMarkerInfoLy
//						.findViewById(R.id.info_zan);
//				iv.setImageResource(info.getImgId());
//				distance.setText(info.getDistance());
//				name.setText(info.getName());
//				zan.setText(info.getZan() + "");

//				InfoWindow infoWindow;
//				TextView tv = new TextView(getActivity());
//				tv.setBackgroundResource(R.mipmap.location_tips);
//				tv.setPadding(30, 20, 30, 50);
//				tv.setText(info.getName());
//				tv.setTextColor(Color.parseColor("#ffffff"));
//				BitmapDescriptor lobd = BitmapDescriptorFactory.fromView(tv);
//				final LatLng latLng = marker.getPosition();
//				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
//				p.y -= 47;
//				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);
//
//				infoWindow = new InfoWindow(lobd, ll, 0,
//						new OnInfoWindowClickListener()
//						{
//							@Override
//							public void onInfoWindowClick()
//							{
//								mBaiduMap.hideInfoWindow();
//							}
//						});
				//mBaiduMap.showInfoWindow(infoWindow);
//				Log.e("", "onMarkerClick: TTTTTT" );
//				mMarkerInfoLy.setVisibility(View.VISIBLE);
//				return true;
//			}
//		});
//	}

//	private class ViewHolder
//	{
//		ImageView infoImg;
//		TextView infoName;
//		TextView infoDistance;
//		TextView infoZan;
//	}

//	protected void popupInfo(RelativeLayout mMarkerLy, ParkingInfo info)
//	{
//		ViewHolder viewHolder = null;
//		if (mMarkerLy.getTag() == null)
//		{
//			viewHolder = new ViewHolder();
//			viewHolder.infoImg = (ImageView) mMarkerLy
//					.findViewById(R.id.info_img);
//			viewHolder.infoName = (TextView) mMarkerLy
//					.findViewById(R.id.info_name);
//			viewHolder.infoDistance = (TextView) mMarkerLy
//					.findViewById(R.id.info_distance);
//			viewHolder.infoZan = (TextView) mMarkerLy
//					.findViewById(R.id.info_zan);
//
//			mMarkerLy.setTag(viewHolder);
//		}
//		viewHolder = (ViewHolder) mMarkerLy.getTag();
//		viewHolder.infoImg.setImageResource(info.getImgId());
//		viewHolder.infoDistance.setText(info.getDistance());
//		viewHolder.infoName.setText(info.getName());
//		viewHolder.infoZan.setText(info.getZan() + "");
//	}

	public void addInfosOverlay(List<ParkingInfo> infos)
	{
		mBaiduMap.clear();
		LatLng latLng = null;
		OverlayOptions overlayOptions;
		Marker marker;
		for (ParkingInfo info : infos)
		{
			// 位置
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			// 图标
			overlayOptions = new MarkerOptions().position(latLng)
					.icon(mIconMarker).zIndex(5);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);
		}
		// 将地图移到到最后一个经纬度位置
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
	}

	private void initMyLocation() {
		// 定位初始化
		mLocationClient = new LocationClient(getActivity());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		// 设置定位的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mBaiduMap.setTrafficEnabled(true);//开启实时交通
	}

	private void initOritationListener(){
		myOrientationListener = new MyOrientationListener(
				getActivity().getApplicationContext());
		myOrientationListener
				.setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
				{
					@Override
					public void onOrientationChanged(float x)
					{
						mXDirection = (int) x;

						// 构造定位数据
						MyLocationData locData = new MyLocationData.Builder()
								.accuracy(mCurrentAccracy)
										// 此处设置开发者获取到的方向信息，顺时针0-360
								.direction(mXDirection)
								.latitude(mCurrentLantitude)
								.longitude(mCurrentLongitude).build();
						// 设置定位数据
						mBaiduMap.setMyLocationData(locData);
						// 设置自定义图标
						BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
								.fromResource(R.mipmap.navi_map_gps_locked);
						MyLocationConfiguration config = new MyLocationConfiguration(
								mCurrentMode, true, mCurrentMarker);
						mBaiduMap.setMyLocationConfigeration(config);

					}
				});
	}

	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{

			// map view 销毁后不在处理新接收的位置
			if (location == null || mapView == null)
				return;
			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
							// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mXDirection).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mCurrentAccracy = location.getRadius();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);
			mCurrentLantitude = location.getLatitude();
			mCurrentLongitude = location.getLongitude();
			// 设置自定义图标
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.mipmap.navi_map_gps_locked);
			MyLocationConfiguration config = new MyLocationConfiguration(
					mCurrentMode, true, mCurrentMarker);
			mBaiduMap.setMyLocationConfigeration(config);
			// 第一次定位时，将地图位置移动到当前位置
			if (isFristLocation)
			{
				isFristLocation = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

	}

	@Override
	public void onStart()
	{
		// 开启图层定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
		{
			mLocationClient.start();
		}
		// 开启方向传感器
		myOrientationListener.start();
		super.onStart();
	}

	@Override
	public void onStop()
	{
		// 关闭图层定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();

		// 关闭方向传感器
		myOrientationListener.stop();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		mapView = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	//
//	protected void onDestory(){
//		super.onDestroy();
//		if(locationManager != null){
//			locationManager.removeUpdates(locationListener);
//		}
//	}
//
//	LocationListener locationListener = new LocationListener() {
//		@Override
//		public void onLocationChanged(Location location) {
//			showLocation(location);
//		}
//
//		@Override
//		public void onStatusChanged(String s, int i, Bundle bundle) {
//
//		}
//
//		@Override
//		public void onProviderEnabled(String s) {
//
//		}
//
//		@Override
//		public void onProviderDisabled(String s) {
//
//		}
//	};
//
//	private void showLocation(Location location) {
//		String currentPostion = "latitude is" + location.getLatitude() + "/n" + "longitude is " + location.getLongitude();
//
//		positionTextView.setText(currentPostion);
//
//	}
}
