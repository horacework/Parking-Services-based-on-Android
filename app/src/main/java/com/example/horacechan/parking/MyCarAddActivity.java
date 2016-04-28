package com.example.horacechan.parking;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyCarAddActivity extends ActionBarActivity {

    private EditText addCarTextBelong;
    private EditText addCarTextNum;
    private Button addCarConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_add);

        addCarTextBelong = (EditText) findViewById(R.id.addCarTextBelong);
        addCarTextNum = (EditText) findViewById(R.id.addCarTextNum);
        addCarConfirmBtn = (Button) findViewById(R.id.addCarConfirmBtn);

        addCarConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validation()){
                    Toast.makeText(MyCarAddActivity.this,"请按照要求填写车牌信息",Toast.LENGTH_LONG).show();
                }else{
                    //发送新增车牌请求
                    Toast.makeText(MyCarAddActivity.this,"发送请求",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validation() {

        return false;
    }
}
