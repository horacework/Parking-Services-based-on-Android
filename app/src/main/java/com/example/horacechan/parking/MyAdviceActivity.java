package com.example.horacechan.parking;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.FeedbackRequest;

public class MyAdviceActivity extends ActionBarActivity implements BaseResponseListener {

    private EditText MyAdviceContent;
    private EditText MyAdviceTel;
    private Button MyAdviceCommit;
    private FeedbackRequest feedbackRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advice);

        MyAdviceContent = (EditText) findViewById(R.id.MyAdviceContent);
        MyAdviceTel = (EditText) findViewById(R.id.MyAdviceTel);
        MyAdviceCommit = (Button) findViewById(R.id.MyAdviceCommit);

        feedbackRequest = new FeedbackRequest();
        feedbackRequest.setOnResponseListener(this);
        feedbackRequest.setRequestType(0);

        MyAdviceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackRequest.userid = LocalHost.INSTANCE.getUserid();
                feedbackRequest.tel = String.valueOf(MyAdviceTel.getText());
                feedbackRequest.content = String.valueOf(MyAdviceContent.getText());
                feedbackRequest.post();
            }
        });

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
        if (response.getStatus()==200){
            switch (response.getRequestType()){
                case 0:
                    Toast.makeText(this, response.getMsg(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }else {
            Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
