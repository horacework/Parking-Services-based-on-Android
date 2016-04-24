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

		initOnCilckListener(view);

		return view;
	}

	private void initOnCilckListener(View view) {

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userLogin(view);
			}
		});
		userState.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userStateInfo(view);
			}
		});
		MyMoneyLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkMyMoney(view);
			}
		});
		MyCarLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setMyCar(view);
			}
		});
		MyOrderLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkMyOrder(view);
			}
		});
		MyFavoriteLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkMyFavorite(view);
			}
		});
		MyParkingLy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				myParkingLog(view);
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

	private void myParkingLog(View view) {
		//TODO:新Activity，列出我的停车记录
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyFavorite(View view) {
		//TODO:新Activity，查看我点过赞的停车场记录，顺便可以一键导航
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyOrder(View view) {
		//TODO:新Activity，查看我的预定以及状态 List
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void setMyCar(View view) {
		//TODO:新Activity，设置使用车牌。添加我的车牌
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void checkMyMoney(View view) {
		//TODO:新Activity，金额变动记录
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void userStateInfo(View view) {
		//TODO:新Activity，这是有车子在停，显示在哪里已经停了多久，大概要收多少钱
		Toast.makeText(getActivity(),"正在建设中",Toast.LENGTH_SHORT).show();
	}

	private void userLogin(View view) {
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
