package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.UserOrderEntity;

import java.util.List;


public class UserOrderListAdapter extends BaseAdapter {

    private List<UserOrderEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public UserOrderListAdapter(Context context, List<UserOrderEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserOrderEntity getItem(int i) {
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
            view=mInflate.inflate(R.layout.myorder_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        //vh.mCar.setText(getItem(i).getPlate());
        vh.MyOrderName.setText(getItem(i).getMarkName());
        vh.MyOrderTime.setText(getItem(i).getOrderTime());
        //vh.MyFavoritePlace.setText(String.valueOf(getItem(i).getPrice())+"元/小时");

        return view;
    }


    static class ViewHolder{
        public TextView MyOrderName;
        public TextView MyOrderTime;

        public ViewHolder(View v) {
            MyOrderName = (TextView) v.findViewById(R.id.MyOrderName);
            MyOrderTime = (TextView) v.findViewById(R.id.MyOrderTime);

        }
    }


}
