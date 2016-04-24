package com.example.horacechan.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MeFrgment extends Fragment {

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_me, container, false);
		//初始化控件
		initWeight(view);

		initOnCilckListener();

		if (false){
			//检测是否已经登录
			//TODO: 设置一个全局变量放是否登录和用户资料
			loginBtn.dispatchSystemUiVisibilityChanged(View.GONE);
			//TODO: userName userState 状态改变
			userInfoLy.dispatchSystemUiVisibilityChanged(View.VISIBLE);
		}

		return view;
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
				if (false){
					checkMyMoney();
				}else {
					Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
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
		//TODO:新Activity，金额变动记录
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
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

	}
}
