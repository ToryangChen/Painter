package com.nmid.painterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Toryang on 2015/3/25.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
    Button loginButton;
    TextView registerView;
    EditText userName,passward;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }
    private void initView(){
        loginButton = (Button)findViewById(R.id.login_button);
        registerView = (TextView)findViewById(R.id.register_view);

        loginButton.setOnClickListener(this);
        registerView.setOnClickListener(this);

        userName = (EditText)findViewById(R.id.user_name);
        passward = (EditText)findViewById(R.id.passward);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                //Toast.makeText(this,"登录按钮",Toast.LENGTH_SHORT).show();
                login();
                break;
            case R.id.register_view:
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(registerIntent);
                break;
        }
    }
    public void login(){
        if((userName.getText().toString()).equals("") || (passward.getText().toString()).equals("")){
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
        if(getUser(userName.getText().toString(),passward.getText().toString())){
            //IPAddress.StaffNo = userName.getText().toString();
            //s = userName.getText().toString();
            Intent intent = new Intent();
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            //IPAddress.userName = s;
            //exitAll.setLoginName(s);
            //passward.setText("");
        }
        else{
            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean getUser(String str1, String str2){

      final String params = "username"+str1+"&password"+str2;
        final String[] result = new String[1];
        new Thread(){
            public void run(){
                result[0] = PostUtil.sendPost(IPAddress.IP,params);
            }
        }.start();
        return (result[0] != "");

    }
}
