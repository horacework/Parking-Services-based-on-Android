package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.http.request.CarNameRequest;
import com.example.horacechan.parking.api.http.request.GetMarkRequest;
import com.example.horacechan.parking.api.model.UserParkingEntity;

import java.util.List;


public class UserParkingLogAdapter extends BaseAdapter {

    private List<UserParkingEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    private GetMarkRequest getMarkRequest;

    private CarNameRequest carNameRequest;

    public UserParkingLogAdapter(Context context, List<UserParkingEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserParkingEntity getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view==null){
            view=mInflate.inflate(R.layout.parkinglog_item,viewGroup,false);
            vh =new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        vh.parkName.setText(getItem(i).getName());
        vh.enterTime.setText(String.format("入：%s", getItem(i).getEnterTime()));
        vh.leaveTime.setText(String.format("出：%s", getItem(i).getLeaveTime()));
        vh.carNum.setText(getItem(i).getPlate());

        return view;
    }

    static class ViewHolder{
        public TextView parkName;
        public TextView enterTime;
        public TextView leaveTime;
        public TextView carNum;

        public ViewHolder(View v) {
            parkName = (TextView) v.findViewById(R.id.parkName);
            enterTime = (TextView) v.findViewById(R.id.enterTime);
            leaveTime = (TextView) v.findViewById(R.id.leaveTime);
            carNum = (TextView) v.findViewById(R.id.carNum);

        }
    }


}
