package com.nmid.painterdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.nmid.application.MyApplication;
import com.nmid.util.BaseData;
import com.nmid.util.HttpThread;
import com.nmid.util.IPAddress;
import com.nmid.util.URLConnect;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Toryang on 2015/3/25.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    ImageButton loginButton;
    TextView registerView;
    EditText userName, passward;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static boolean mBackKeyPressed = false;

    int flag;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            flag = msg.arg1;
            login(flag);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyApplication.getInstance().addActivity(LoginActivity.this);
        preferences = getSharedPreferences("Painter", MODE_WORLD_READABLE);
        editor = preferences.edit();


        initView();

    }

    private void initView() {
        loginButton = (ImageButton) findViewById(R.id.login_button);
        registerView = (TextView) findViewById(R.id.register_view);

        loginButton.setOnClickListener(this);
        registerView.setOnClickListener(this);

        userName = (EditText) findViewById(R.id.user_name);
        passward = (EditText) findViewById(R.id.passward);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                //Toast.makeText(this,"登录按钮",Toast.LENGTH_SHORT).show();
                new HttpThread(IPAddress.IP + "GreatArtist/login.php", userName.getText().toString(),
                        passward.getText().toString(), handler).start();
                //new URLConnect(handler, userName.getText().toString()).start();

                break;
            case R.id.register_view:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(registerIntent);
                break;
        }
    }

    public void login(int flag) {
        if ((userName.getText().toString()).equals("") || (passward.getText().toString()).equals("")) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (flag == 1) {
            BaseData baseData = new BaseData();
            System.out.println("username=" + userName.getText().toString());
            baseData.setUsername(userName.getText().toString());
            editor.putString("username",userName.getText().toString());
            editor.putString("passward", passward.getText().toString());
            editor.commit();
            Intent intent = new Intent();
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            intent.setClass(LoginActivity.this, MainActivity.class);
            passward.setText("");
            startActivity(intent);

        } else {
            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            },2000);
           }else{

            MyApplication.getInstance().exit();
        }
    }
}