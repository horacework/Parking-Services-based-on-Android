package com.example.horacechan.parking;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ParkingFrgment extends Fragment {

	//private WebView webView;

	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_daohang, container, false);
		//内置浏览器应用
//		webView = (WebView)view.findViewById(R.id.web_view);
//
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebViewClient(new WebViewClient());
//		webView.loadUrl("http://www.baidu.com");

		positionTextView = (TextView)view.findViewById(R.id.position_text);
		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		//获取所有可用的位置提供器
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)){
			provider = LocationManager.GPS_PROVIDER;
		}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider = locationManager.NETWORK_PROVIDER;
		}else {
			//当前没有可用的位置提供器时，弹出Toast提醒
			Toast.makeText(this.getActivity(),"No location provider to use",Toast.LENGTH_SHORT).show();
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location!=null){
			showLocation(location);

		}

		locationManager.requestLocationUpdates(provider,5000,1,locationListener);

		return view;
	}

	protected void onDestory(){
		super.onDestroy();
		if(locationManager != null){
			locationManager.removeUpdates(locationListener);
		}
	}

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			showLocation(location);
		}

		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {

		}

		@Override
		public void onProviderEnabled(String s) {

		}

		@Override
		public void onProviderDisabled(String s) {

		}
	};

	private void showLocation(Location location) {
		String currentPostion = "latitude is" + location.getLatitude() + "/n" + "longitude is " + location.getLongitude();

		positionTextView.setText(currentPostion);

	}
}
