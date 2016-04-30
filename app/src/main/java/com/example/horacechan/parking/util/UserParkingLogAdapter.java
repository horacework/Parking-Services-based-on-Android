package com.example.horacechan.parking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horacechan.parking.R;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.CarNameRequest;
import com.example.horacechan.parking.api.http.request.GetMarkRequest;
import com.example.horacechan.parking.api.model.MarkInfo;
import com.example.horacechan.parking.api.model.UserCarEntity;
import com.example.horacechan.parking.api.model.UserParkingEntity;

import java.util.List;


public class UserParkingLogAdapter extends BaseAdapter implements BaseResponseListener {

    private List<UserParkingEntity> datas;
    private Context context;
    private LayoutInflater mInflate;

    private ViewHolder vh;

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
        vh=null;
        if (view==null){
            view=mInflate.inflate(R.layout.parkinglog_item,viewGroup,false);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        //vh.mPay.setText(getItem(i).getType()==1?"支出":"充值");
        //vh.parkName.setText(getItem(i).getFigure()+"元");
        getMarkRequest = new GetMarkRequest();
        getMarkRequest.setOnResponseListener(this);
        getMarkRequest.setRequestType(0);
        getMarkRequest.id = getItem(i).getMarkerId();
        Toast.makeText(context,"1"+getItem(i).getMarkerId(),Toast.LENGTH_LONG).show();
        getMarkRequest.execute();

        vh.enterTime.setText(String.format("入：%s", getItem(i).getEnterTime()));
        vh.leaveTime.setText(String.format("出：%s", getItem(i).getLeaveTime()));
        //vh.carNum.setText(getItem(i).getCurrentTime());

        carNameRequest = new CarNameRequest();
        carNameRequest.setOnResponseListener(this);
        carNameRequest.setRequestType(1);
        carNameRequest.carid = getItem(i).getCarId();
        carNameRequest.execute();


        return view;
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onSuccess(BaseResponse response) {
        switch (response.getRequestType()){
            case 0:
                try {
                    MarkInfo markInfo = (MarkInfo) response.getData();
                    vh.parkName.setText(markInfo.getName());
                }catch (NullPointerException e){

                }
                break;
            case 1:
                try {
                    UserCarEntity userCarEntity = (UserCarEntity) response.getData();
                    vh.carNum.setText(userCarEntity.getPlate());
                }catch (NullPointerException e){

                }
                break;
        }

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
