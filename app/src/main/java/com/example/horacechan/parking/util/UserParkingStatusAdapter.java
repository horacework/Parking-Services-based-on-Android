package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.UserStatusEntity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class UserParkingStatusAdapter extends BaseAdapter {

    private List<UserStatusEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public UserParkingStatusAdapter(Context context, List<UserStatusEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserStatusEntity getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if (view==null){
            view=mInflate.inflate(R.layout.myparkingstatus_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        vh.parkName.setText(getItem(i).getMarkerName());
        //计算进库到现在的时间差
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        long currentTimeFormat=0L;
        long enterTimeFormat=0L;
        try {
            currentTimeFormat = timeformat.parse(getTimeStampNumberFormat(currentTime)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            enterTimeFormat = timeformat.parse(getTimeStampNumberFormat(getItem(i).getEnterTime())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hours=(int) ((currentTimeFormat - enterTimeFormat )/3600000);
        int minutes=(int) (((currentTimeFormat - enterTimeFormat)/1000-hours*3600)/60);
        vh.stayTime.setText("计费时间：约"+hours+"小时"+minutes+"分钟");

        //计算费用
        double payMoney;
        if (minutes>0){
            payMoney = (hours+1)*getItem(i).getPrice();
        }else {
            payMoney = hours*getItem(i).getPrice();
        }
        vh.yourPay.setText("预估费用："+payMoney+" 元");

        vh.myCar.setText(getItem(i).getCarName());

        return view;
    }


    static class ViewHolder{
        public TextView parkName;
        public TextView stayTime;
        public TextView yourPay;
        public TextView myCar;

        public ViewHolder(View v) {
            parkName = (TextView) v.findViewById(R.id.parkName);
            stayTime = (TextView) v.findViewById(R.id.stayTime);
            yourPay = (TextView) v.findViewById(R.id.yourPay);
            myCar = (TextView) v.findViewById(R.id.carNum);
        }
    }

    public static String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }

}
