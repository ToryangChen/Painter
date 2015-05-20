package com.nmid.painterdemo;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nmid.application.MyApplication;
import com.nmid.util.HttpThread;
import com.nmid.util.IPAddress;

/**
 * Created by Toryang on 2015/3/25.
 */
public class RegisterActivity extends ActionBarActivity {
    private EditText rUsername,rPassword,rgPassword;
    private Button registerbutton;
    int flag;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            flag = msg.arg1;
            register(flag);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyApplication.getInstance().addActivity(RegisterActivity.this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();

    }
    public void initView(){
        rUsername = (EditText)findViewById(R.id.register_username);
        rPassword = (EditText)findViewById(R.id.register_password);
        rgPassword = (EditText)findViewById(R.id.register_pwagain);

        registerbutton = (Button)findViewById(R.id.register_button);
        registerbutton.setOnClickListener(new MyClickListener());
    }

    private void register(int flag){
        if(flag == 1){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }

   private void judgement(){
       if((rUsername.getText().toString()).equals("") || (rPassword.getText().toString()).equals("")
               || (rgPassword.getText().toString()).equals("")){
           Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
       }
       else if(!(rPassword.getText().toString()).equals(rgPassword.getText().toString())){
           Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
       }else{
           new HttpThread(IPAddress.IP+"reg.php",rUsername.getText().toString(),
                   rPassword.getText().toString(),handler).start();
       }
   }
   public class MyClickListener implements View.OnClickListener{

       @Override
       public void onClick(View v) {
           judgement();
       }
   }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
