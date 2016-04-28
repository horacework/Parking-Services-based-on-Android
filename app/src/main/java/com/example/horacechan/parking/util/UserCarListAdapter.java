package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.UserCarEntity;

import java.util.List;


public class UserCarListAdapter extends BaseAdapter {

    private List<UserCarEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public UserCarListAdapter(Context context, List<UserCarEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserCarEntity getItem(int i) {
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
            view=mInflate.inflate(R.layout.mycar_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        vh.mCar.setText(getItem(i).getPlate());

        return view;
    }


    static class ViewHolder{
        public TextView mCar;

        public ViewHolder(View v) {
            mCar = (TextView) v.findViewById(R.id.MyCarString);

        }
    }


}
