package com.example.horacechan.parking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.FavoriteMarkerDeleteRequest;
import com.example.horacechan.parking.api.http.request.FavoriteMarkerListRequest;
import com.example.horacechan.parking.api.model.UserFavoriteEntity;
import com.example.horacechan.parking.util.UserFavoriteListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteActivity extends ActionBarActivity implements BaseResponseListener {

    ListView favoriteList;

    private List<UserFavoriteEntity> datas;
    private UserFavoriteListAdapter adapter;

    FavoriteMarkerListRequest favoriteMarkerListRequest;
    FavoriteMarkerDeleteRequest favoriteMarkerDeleteRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        favoriteList = (ListView) findViewById(R.id.favoriteList);

        datas = new ArrayList<>();
        adapter = new UserFavoriteListAdapter(this,datas);
        favoriteList.setAdapter(adapter);

        itemOnClickListener();


        FavoriteMarkerListRequestShow();

    }

    private void FavoriteMarkerListRequestShow() {
        favoriteMarkerListRequest = new FavoriteMarkerListRequest();
        favoriteMarkerListRequest.setOnResponseListener(this);
        favoriteMarkerListRequest.setRequestType(0);
        favoriteMarkerListRequest.userId = LocalHost.INSTANCE.getUserid();
        favoriteMarkerListRequest.execute();
    }

    private void itemOnClickListener() {
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击事件
                //TODO:新Activity导航到目的地-->获取当前位置-->获取marker位置-->导航
            }
        });
        favoriteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //长按事件，取消收藏该停车场
                comfirmdeleteFavorite(i);
                return true;
            }
        });

    }

    private void comfirmdeleteFavorite(final int i){
        AlertDialog.Builder isExitLog = new AlertDialog.Builder(MyFavoriteActivity.this);
        isExitLog.setTitle("提示")
                .setMessage("确定删除此收藏？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        deleteFavorite(i);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteFavorite(int i) {
        favoriteMarkerDeleteRequest = new FavoriteMarkerDeleteRequest();
        favoriteMarkerDeleteRequest.setOnResponseListener(this);
        favoriteMarkerDeleteRequest.setRequestType(1);
        favoriteMarkerDeleteRequest.id = datas.get(i).getId();
        favoriteMarkerDeleteRequest.userid = LocalHost.INSTANCE.getUserid();
        favoriteMarkerDeleteRequest.post();
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        switch (response.getRequestType()){
            case 0:
                if (response.getStatus()==200){
                    List<UserFavoriteEntity> info = (List<UserFavoriteEntity>) response.getArrayList();
                    datas.clear();
                    datas.addAll(info);
                    adapter.notifyDataSetChanged();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (response.getStatus()==200){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                    FavoriteMarkerListRequestShow();
                }else if (response.getStatus()==404){
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
