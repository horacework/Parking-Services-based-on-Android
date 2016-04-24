package com.example.horacechan.parking;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

    EditText Login_username;
    EditText Login_password1;
    EditText Login_password2;
    Button Login_login_btn;
    TextView Login_exchange_btn;

    String regEx = "[a-zA-Z0-9]";

    //状态码，true表示当前在登录页，false表示当前在注册页
    boolean LoginOrSignup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWeight();

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
                        //TODO:发送登录请求
                        
                        Toast.makeText(getApplication(), "登录啦啦啦啦", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //注册
                    if (!password.equals(password2)){
                        Toast.makeText(getApplication(), "两次密码必须一致", Toast.LENGTH_SHORT).show();
                    }else if (checkUsername(username,regEx,20) && checkPassword(password,regEx,20)){
                        //TODO：发送注册请求
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
}
