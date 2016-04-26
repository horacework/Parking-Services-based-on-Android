package com.example.horacechan.parking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horacechan.parking.api.LocalHost;
import com.example.horacechan.parking.api.http.base.BaseResponse;
import com.example.horacechan.parking.api.http.base.BaseResponseListener;
import com.example.horacechan.parking.api.http.request.LoginRequest;
import com.example.horacechan.parking.api.http.request.SignupRequest;
import com.example.horacechan.parking.api.model.UserEntity;
import com.example.horacechan.parking.util.DeviceUtils;
import com.example.horacechan.parking.util.MD5Utils;

public class LoginActivity extends ActionBarActivity implements BaseResponseListener {

    EditText Login_username;
    EditText Login_password1;
    EditText Login_password2;
    Button Login_login_btn;
    TextView Login_exchange_btn;

    String regEx = "[a-zA-Z0-9]";

    //状态码，true表示当前在登录页，false表示当前在注册页
    boolean LoginOrSignup = true;
    String deviceId;

    //请求
    LoginRequest mLoginRequest;
    SignupRequest mSignupRequest;

    //SharePreference
    SharedPreferences.Editor ShareEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取设备ID
        deviceId = DeviceUtils.getImieStatus(this);
        ShareEditor = getSharedPreferences("ParkingApp",MODE_PRIVATE).edit();

        initWeight();

        //Login请求初始化
        mLoginRequest = new LoginRequest();
        mLoginRequest.setOnResponseListener(this);
        mLoginRequest.setRequestType(0);
        //Signup请求初始化
        mSignupRequest = new SignupRequest();
        mSignupRequest.setOnResponseListener(this);
        mSignupRequest.setRequestType(1);

        initOnCilckListener();

    }

    private void initOnCilckListener() {

        Login_exchange_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginOrSignup) {
                    Login_password2.setVisibility(View.VISIBLE);
                    Login_login_btn.setText("注册");
                    LoginOrSignup = false;
                } else {
                    Login_password2.setVisibility(View.GONE);
                    Login_login_btn.setText("登录");
                    LoginOrSignup = true;
                }

            }
        });

        Login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = String.valueOf(Login_username.getText());
                String password = String.valueOf(Login_password1.getText());
                String password2 = String.valueOf(Login_password2.getText());
                if (LoginOrSignup) {
                    //登录
                    if (checkUsername(username,regEx,20) && checkPassword(password,regEx,20)){
                        //发送登录请求
                        mLoginRequest.username = username;
                        mLoginRequest.password = MD5Utils.getMD5(password);
//                        mLoginRequest.password = password;
                        mLoginRequest.deviceId = deviceId;
                        mLoginRequest.post();
                        Toast.makeText(getApplication(), "登录啦啦啦啦", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //注册
                    if (!password.equals(password2)){
                        Toast.makeText(getApplication(), "两次密码必须一致", Toast.LENGTH_SHORT).show();
                    }else if (checkUsername(username,regEx,20) && checkPassword(password,regEx,20)){
                        //发送注册请求
                        mSignupRequest.username = username;
                        mSignupRequest.password = MD5Utils.getMD5(password);
                        mSignupRequest.password2 = MD5Utils.getMD5(password2);
//                        mSignupRequest.password = password;
//                        mSignupRequest.password2 = password2;
                        mSignupRequest.deviceId = deviceId;
                        mSignupRequest.post();
                        Toast.makeText(getApplication(), "注册啦啦啦啦", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private boolean checkUsername(String username , String regEx , int maxLength){
        if (username.equals("")){
            Toast.makeText(getApplication(), "用户名不能为空", Toast.LENGTH_SHORT).show();
        }else if (username.length()>maxLength) {
            Toast.makeText(getApplication(), "用户名不能超过" + maxLength + "字符", Toast.LENGTH_SHORT).show();
        }else if (!fitString(username,regEx)){
            Toast.makeText(getApplication(), "用户名只能为英文字母与数字", Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }
        return false;
    }

    private boolean checkPassword(String username , String regEx , int maxLength){
        if (username.equals("")){
            Toast.makeText(getApplication(), "密码不能为空", Toast.LENGTH_SHORT).show();
        }else if (username.length()>maxLength) {
            Toast.makeText(getApplication(), "密码不能超过" + maxLength + "字符", Toast.LENGTH_SHORT).show();
        }else if (!fitString(username,regEx)){
            Toast.makeText(getApplication(), "密码只能为英文字母与数字", Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }
        return false;
    }

    private boolean fitString(String str, String regEx) {
        return str.replaceAll(regEx, "").length() == 0;
    }


    private void initWeight() {

        Login_username = (EditText) findViewById(R.id.Login_username);
        Login_password1 = (EditText) findViewById(R.id.Login_password1);
        Login_password2 = (EditText) findViewById(R.id.Login_password2);
        Login_login_btn = (Button) findViewById(R.id.Login_login_btn);
        Login_exchange_btn = (TextView) findViewById(R.id.Login_exchange_btn);
    }

    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 200){
            switch (response.getRequestType()){

                case 0://发送登录请求
                    UserEntity user = (UserEntity) response.getData();
                    //设置全局userID
                    LocalHost.INSTANCE.setUserid(user.getId());
                    LocalHost.INSTANCE.setUserName(user.getName());
                    //数据持久化到SharePreference中
                    ShareEditor.putString("userId",user.getId());
                    ShareEditor.putString("userName", user.getName());
                    ShareEditor.commit();
                    setResult(200);
                    finish();
                    break;

                case 1://发送注册请求
                    UserEntity userEntity = (UserEntity) response.getData();
                    LocalHost.INSTANCE.setUserid(userEntity.getId());
                    LocalHost.INSTANCE.setUserName(userEntity.getName());
                    ShareEditor.putString("userId", userEntity.getId());
                    ShareEditor.putString("userName", userEntity.getName());
                    ShareEditor.commit();
                    Toast.makeText(getApplication(), "登录啦啦啦啦", Toast.LENGTH_SHORT).show();
                    setResult(200);
                    finish();
                    break;

            }
        }else if (response.getStatus()==404){
            Toast.makeText(this, response.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //返回键监听
            setResult(404);
            finish();
        }
        return false;

    }
}
