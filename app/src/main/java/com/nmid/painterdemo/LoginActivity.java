package com.nmid.painterdemo;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Toryang on 2015/3/25.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    ImageButton loginButton;
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
        loginButton = (ImageButton)findViewById(R.id.login_button);
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
        else if(true){
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

      final String params = "username="+str1+"&password="+str2;
        final String[] result = new String[1];
        new Thread(){
            public void run(){
                result[0] = PostUtil.sendPost(IPAddress.IP+"GreatArtist/login.php",params);
            }
        }.start();
        return (result[0] != "");

    }
}
