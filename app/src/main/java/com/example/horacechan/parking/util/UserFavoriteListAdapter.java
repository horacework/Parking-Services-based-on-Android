package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.UserFavoriteEntity;

import java.util.List;


public class UserFavoriteListAdapter extends BaseAdapter {

    private List<UserFavoriteEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public UserFavoriteListAdapter(Context context, List<UserFavoriteEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserFavoriteEntity getItem(int i) {
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
            view=mInflate.inflate(R.layout.myfavorite_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        //vh.mCar.setText(getItem(i).getPlate());
        vh.MyFavoriteName.setText(getItem(i).getName());
        vh.MyFavoritePlace.setText(String.valueOf(getItem(i).getPrice())+"元/小时");

        return view;
    }


    static class ViewHolder{
        public TextView MyFavoriteName;
        public TextView MyFavoritePlace;

        public ViewHolder(View v) {
            MyFavoriteName = (TextView) v.findViewById(R.id.MyFavoriteName);
            MyFavoritePlace = (TextView) v.findViewById(R.id.MyFavoritePlace);

        }
    }


}
