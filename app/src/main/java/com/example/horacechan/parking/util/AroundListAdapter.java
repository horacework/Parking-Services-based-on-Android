package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.BaiduPOIEntity;

import java.util.List;


public class AroundListAdapter extends BaseAdapter {

    private List<BaiduPOIEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public AroundListAdapter(Context context, List<BaiduPOIEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public BaiduPOIEntity getItem(int i) {
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
            view=mInflate.inflate(R.layout.aroundlist_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        vh.mName.setText(getItem(i).getName());
        vh.mAddress.setText("地址："+getItem(i).getAddress());

        return view;
    }


    static class ViewHolder{
        public TextView mName;
        public TextView mAddress;

        public ViewHolder(View v) {
            mName = (TextView) v.findViewById(R.id.itemName);
            mAddress = (TextView) v.findViewById(R.id.itemAddress);

        }
    }


}
