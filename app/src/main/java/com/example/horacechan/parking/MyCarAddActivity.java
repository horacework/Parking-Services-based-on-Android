package com.example.horacechan.parking;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.CarAddRequest;

public class MyCarAddActivity extends ActionBarActivity implements BaseResponseListener {

    private EditText addCarTextBelong;
    private EditText addCarTextNum;
    private Button addCarConfirmBtn;
    private CarAddRequest carAddRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_add);

        addCarTextBelong = (EditText) findViewById(R.id.addCarTextBelong);
        addCarTextNum = (EditText) findViewById(R.id.addCarTextNum);
        addCarConfirmBtn = (Button) findViewById(R.id.addCarConfirmBtn);

        carAddRequest = new CarAddRequest();
        carAddRequest.setOnResponseListener(this);
        carAddRequest.setRequestType(0);

        addCarConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String belong = String.valueOf(addCarTextBelong.getText());
                String num = String.valueOf(addCarTextNum.getText());
                if (!validation(belong,num)){
                    Toast.makeText(MyCarAddActivity.this,"信息有误。请按照要求填写车牌信息",Toast.LENGTH_LONG).show();
                }else{
                    //发送新增车牌请求
                    carAddRequest.userid = LocalHost.INSTANCE.getUserid();
                    carAddRequest.plate = belong +" "+ num;
                    carAddRequest.post();
                }
            }
        });
    }

    private boolean validation(String belong, String num) {

        String rgxx = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";

        String car = belong + num;

        return car.matches(rgxx);
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(this,response.getMsg(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        Toast.makeText(MyCarAddActivity.this,"添加车牌成功",Toast.LENGTH_LONG).show();
        setResult(200);
        finish();
    }
}
