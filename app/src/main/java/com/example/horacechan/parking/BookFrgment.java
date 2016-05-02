package com.example.horacechan.parking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookFrgment extends Fragment {

	Spinner markerSpinner;
	TextView bookDate;
	TextView bookTime;
	Button bookBtn;

	private static final String[] m={"未选中","A型","B型","O型","AB型","其他"};
	private ArrayAdapter<String> adapter;

	private String selectedMarkId;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_book, container, false);

		markerSpinner = (Spinner) view.findViewById(R.id.markerSpinner);
		bookDate = (TextView) view.findViewById(R.id.bookDate);
		bookTime = (TextView) view.findViewById(R.id.bookTime);
		bookBtn = (Button) view.findViewById(R.id.bookActionBtn);


		adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,m);
		markerSpinner.setAdapter(adapter);
		//markerSpinner.setPrompt("请选择停车场");

		initCalendar();

		initOnClickListener();

		return view;
	}

	private void initCalendar() {
		Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
		Date mydate=new Date(); //获取当前日期Date对象
		mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

		year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
		month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
		day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
		hour=mycalendar.get(Calendar.HOUR_OF_DAY);
		minute=mycalendar.get(Calendar.MINUTE);
		bookDate.setText(year+"-"+(month+1)+"-"+day); //显示当前的年月日
		bookTime.setText(hour+":"+minute);
	}

	private void initOnClickListener() {

		markerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getActivity(), m[i], Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				Toast.makeText(getActivity(),"lalalal",Toast.LENGTH_LONG).show();
			}
		});

		bookDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
						year = i;
						month = i1;
						day = i2;
						bookDate.setText(year+"-"+(month+1)+"-"+day); //显示当前的年月日
					}
				}, year, month, day);
				dpd.show();
			}
		});
		bookTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TimePickerDialog tpd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int i, int i1) {
						hour = i;
						minute = i1;
						bookTime.setText(hour+":"+minute);
					}
				},hour,minute,true);
				tpd.show();
			}
		});
		bookBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO：组装timestamp，markId，userid,---->post到数据库返回Toast显示
			}
		});

	}
}
