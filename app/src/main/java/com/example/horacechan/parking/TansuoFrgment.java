package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baoyz.widget.PullRefreshLayout;
import com.example.horacechan.parking.api.ParkingApp;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.AroundPOIRequest;
import com.example.horacechan.parking.api.model.BaiduPOIEntity;
import com.example.horacechan.parking.util.AroundListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TansuoFrgment extends Fragment implements BaseResponseListener, AMapLocationListener {

	PullRefreshLayout layout;
	ListView aroundList;
	Button changeMode;

	List<BaiduPOIEntity> poiEntities;
	AroundListAdapter adapter;

	AroundPOIRequest aroundPOIRequest;

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	double currentLatitude = 0.0;
	double currentLongitude = 0.0;

	boolean isFirstRequest = true;

	final String[] queryMode = {"酒店", "景点", "美食", "娱乐", "火锅"};
	String queryModeSelected = "景点";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_tansuo, container, false);

		layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		aroundList = (ListView) view.findViewById(R.id.aroundList);
		changeMode = (Button) view.findViewById(R.id.changeMode);

		poiEntities = new ArrayList<>();
		adapter = new AroundListAdapter(getActivity(),poiEntities);

		aroundList.setAdapter(adapter);
		initRequest();

		initOnClickListener();

		//高德定位相关
		locationClient = new AMapLocationClient(getActivity());
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		// 设置定位监听
		locationClient.setLocationListener(this);
		locationClient.startLocation();

		return view;
	}

	private void initOnClickListener() {
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				aroundPOIRequest.query = queryModeSelected;
				aroundPOIRequest.location = currentLatitude+","+currentLongitude;
				aroundPOIRequest.execute();
			}
		});
		aroundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				AlertDialog.Builder megAlert = new AlertDialog.Builder(getActivity());
				megAlert.setTitle("详情")
						.setMessage("电话："+poiEntities.get(i).getTelephone())
						.setPositiveButton("确定",null)
						.show();
			}
		});
		changeMode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("选择搜索周边模式");
				builder.setItems(queryMode, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						queryModeSelected = queryMode[which];
						aroundPOIRequest.query = queryModeSelected;
						aroundPOIRequest.execute();
					}
				});
				builder.show();
			}
		});
	}

	private void initRequest() {
		aroundPOIRequest = new AroundPOIRequest();
		aroundPOIRequest.setOnResponseListener(this);
		aroundPOIRequest.setRequestType(0);
		aroundPOIRequest.query = queryModeSelected;
		aroundPOIRequest.radius = "2000";
		aroundPOIRequest.output = "json";
		aroundPOIRequest.ak = ParkingApp.BaiduAk;
	}

	@Override
	public void onStart(BaseResponse response) {

	}

	@Override
	public void onFailure(BaseResponse response) {
		Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSuccess(BaseResponse response) {
		switch (response.getRequestType()){
			case 0:
				poiEntities.clear();
				poiEntities.addAll ((Collection<? extends BaiduPOIEntity>) response.getArrayList());
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
				layout.setRefreshing(false);
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
	public void onDestroy() {
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
				if (isFirstRequest){
					aroundPOIRequest.location = currentLatitude+","+currentLongitude;
					aroundPOIRequest.execute();
					isFirstRequest = false;
				}
			}else {
				Log.e("AmapError", "location Error, ErrCode:"
						+ aMapLocation.getErrorCode() + ", errInfo:"
						+ aMapLocation.getErrorInfo());
			}
		}
	}
}
