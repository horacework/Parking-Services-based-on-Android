package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.model.UsermoneyEntity;

import java.util.List;


public class MoneyLogAdapter extends BaseAdapter {

    private List<UsermoneyEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    public MoneyLogAdapter(Context context, List<UsermoneyEntity> datas) {
        this.context = context;
        this.datas = datas;
        mInflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UsermoneyEntity getItem(int i) {
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
            view=mInflate.inflate(R.layout.moneylog_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }


        vh.mPay.setText(getItem(i).getType()==1?"支出":"充值");
        vh.mMoney.setText(getItem(i).getFigure()+"元");



        return view;
    }


    static class ViewHolder{
        public TextView mPay;
        public TextView mMoney;

        public ViewHolder(View v) {
            mPay= (TextView) v.findViewById(R.id.itemType);
            mMoney= (TextView) v.findViewById(R.id.itemNum);
        }
    }


}
