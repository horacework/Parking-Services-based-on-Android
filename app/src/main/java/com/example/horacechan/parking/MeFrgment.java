package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.LogoutRequest;
import com.example.horacechan.parking.api.http.request.MoneyRemainRequest;
import com.example.horacechan.parking.api.model.UsermoneyEntity;
import com.example.horacechan.parking.util.DeviceUtils;

public class MeFrgment extends Fragment implements BaseResponseListener {

	Button loginBtn;
	LinearLayout userInfoLy;
	TextView userName;
	TextView userState;
	LinearLayout MyMoneyLy;
	TextView MyMoneyRemain;
	LinearLayout MyCarLy;
	TextView MyCarNum;
	LinearLayout MyOrderLy;
	LinearLayout MyFavoriteLy;
	LinearLayout MyParkingLy;
	LinearLayout MyAdvice;
	Button logoutBtn;

	String isLoginUserId = null;
	String isLoginUserName = null;
	String isLoginUserState = null;

	LogoutRequest mLogoutRequest;
	MoneyRemainRequest mMoneyRemainRequest;

	SharedPreferences.Editor editor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_me, container, false);
		//初始化控件
		initWeight(view);

		initOnCilckListener();

		if (LocalHost.INSTANCE.getUserid()!= null){
			//检测是否已经登录
			changeToLogin();
		}

		return view;
	}

	//处理登录成功之后的操作
	private void changeToLogin() {

		loginBtn.setVisibility(View.GONE);
		isLoginUserId = LocalHost.INSTANCE.getUserid();
		isLoginUserName = LocalHost.INSTANCE.getUserName();
		userName.setText(isLoginUserName);
		//TODO: 发送请求当前有没有车子在停车并显示
		userState.setText("当前没有车子在停车场停车");
		userInfoLy.setVisibility(View.VISIBLE);
		logoutBtn.setVisibility(View.VISIBLE);
		//TODO: 更新金额与当前车牌
		mMoneyRemainRequest = new MoneyRemainRequest();
		mMoneyRemainRequest.setOnResponseListener(this);
		mMoneyRemainRequest.setRequestType(1);
		mMoneyRemainRequest.userId = isLoginUserId;
		mMoneyRemainRequest.execute();
	}

	//处理退出登录之后操作
	private void changeToUnLogin() {
		//向服务发送退出登录请求
		mLogoutRequest = new LogoutRequest();
		mLogoutRequest.setOnResponseListener(this);
		mLogoutRequest.setRequestType(0);
		mLogoutRequest.userId = isLoginUserId;
		mLogoutRequest.deviceId = DeviceUtils.getImieStatus(getActivity());
		mLogoutRequest.post();
		//不管退出请求有没有成功，客户端也会强行退出
		//清空本地变量
		isLoginUserName = null;
		isLoginUserId = null;
		//清空全局变量
		LocalHost.INSTANCE.setUserid(null);
		LocalHost.INSTANCE.setUserName(null);
		//清空SharePreference
		editor.remove("userId");
		editor.remove("userName");
		editor.commit();
		//布局操作
		userInfoLy.setVisibility(View.GONE);
		logoutBtn.setVisibility(View.GONE);
		loginBtn.setVisibility(View.VISIBLE);
	}

	private void initOnCilckListener() {

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
					userLogin();
			}
		});
		userState.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userStateInfo();
			}
		});
		MyMoneyLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isLoginUserName != null) {
					checkMyMoney();
				} else {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
					userLogin();
				}
			}
		});
		MyCarLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setMyCar();
			}
		});
		MyOrderLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkMyOrder();
			}
		});
		MyFavoriteLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkMyFavorite();
			}
		});
		MyParkingLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				myParkingLog();
			}
		});
		MyAdvice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showAdviceActivity();
			}
		});
		logoutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder isExitLog = new AlertDialog.Builder(getActivity());
				isExitLog.setTitle("提示")
						.setMessage("确定退出登录？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								changeToUnLogin();
							}
						})
						.setNegativeButton("取消", null)
						.show();
			}
		});
	}

	private void showAdviceActivity() {
		//TODO:新Activity，弹出反馈意见Activity
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void myParkingLog() {
		//TODO:新Activity，列出我的停车记录
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyFavorite() {
		//TODO:新Activity，查看我点过赞的停车场记录，顺便可以一键导航
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyOrder() {
		//TODO:新Activity，查看我的预定以及状态 List
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void setMyCar() {
		//TODO:新Activity，设置使用车牌。添加我的车牌
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyMoney() {
		Intent intent = new Intent(getActivity(),MoneylogActivity.class);
		startActivity(intent);

	}

	private void userStateInfo() {
		//TODO:新Activity，这是有车子在停，显示在哪里已经停了多久，大概要收多少钱
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void userLogin() {
		Intent intent = new Intent(getActivity(),LoginActivity.class);
		startActivityForResult(intent,1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case 1:
				//处理LoginActivity返回的信息
				if (resultCode == 200){
					changeToLogin();
				}
				break;
		}
	}

	private void initWeight(View view) {

		loginBtn = (Button) view.findViewById(R.id.loginBtn);
		userInfoLy = (LinearLayout) view.findViewById(R.id.userInfoLy);
		userName = (TextView) view.findViewById(R.id.userName);
		userState = (TextView) view.findViewById(R.id.userState);
		MyMoneyLy = (LinearLayout) view.findViewById(R.id.MyMoneyLy);
		MyMoneyRemain = (TextView) view.findViewById(R.id.MyMoneyRemain);
		MyCarLy = (LinearLayout) view.findViewById(R.id.MyCarLy);
		MyCarNum = (TextView) view.findViewById(R.id.MyCarNum);
		MyOrderLy = (LinearLayout) view.findViewById(R.id.MyOrderLy);
		MyFavoriteLy = (LinearLayout) view.findViewById(R.id.MyFavoriteLy);
		MyParkingLy = (LinearLayout) view.findViewById(R.id.MyParkingLy);
		MyAdvice = (LinearLayout) view.findViewById(R.id.MyAdvice);
		logoutBtn = (Button) view.findViewById(R.id.logoutBtn);

		editor = getActivity().getSharedPreferences("ParkingApp", Context.MODE_PRIVATE).edit();

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
		if (response.getStatus()==200){
			switch (response.getRequestType()){
				case 0:
					Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_LONG).show();
					break;
				case 1:
					//显示当前用户余额
					UsermoneyEntity usermoneyEntity = (UsermoneyEntity) response.getData();
					MyMoneyRemain.setText(usermoneyEntity.getRemain() + "元");
					//Toast.makeText(getActivity(), usermoneyEntity.getUserId(), Toast.LENGTH_LONG).show();
					MyMoneyRemain.setVisibility(View.VISIBLE);
			}
		}else if (response.getStatus()==404){
			Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_LONG).show();
		}
	}
}
