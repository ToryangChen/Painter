package com.nmid.painterdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nmid.application.MyApplication;
import com.nmid.util.HttpThread;
import com.nmid.util.IPAddress;
import com.nmid.util.URLConnect;

/**
 * Created by Toryang on 2015/5/11.
 */
public class SplashActivity extends Activity {
    private SharedPreferences preferences;
    private String username,passward;
    private int flag;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            flag = msg.arg1;
            intent(flag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_splash);
        MyApplication.getInstance().addActivity(SplashActivity.this);
        preferences = getSharedPreferences("Painter",MODE_WORLD_READABLE);
        username = preferences.getString("username", null);
        passward = preferences.getString("passward",null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                login(username, passward);
            }
        },3000);
    }
    private void login(String username, String passward) {
        if(passward==null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else{
            new HttpThread(IPAddress.IP + "login.php",username,passward,handler).start();

        }
    }
    private void intent(int flag){
        if (flag == 1){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
