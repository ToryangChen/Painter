package com.nmid.painterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Toryang on 2015/3/25.
 */
public class RegisterActivity extends ActionBarActivity {
    private EditText rUsername,rPassword,rgPassword;
    private Button registerbutton;

    private Person user = new Person();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }
    public void initView(){
        rUsername = (EditText)findViewById(R.id.register_username);
        rPassword = (EditText)findViewById(R.id.register_password);
        rgPassword = (EditText)findViewById(R.id.register_pwagain);

        registerbutton = (Button)findViewById(R.id.register_button);
        registerbutton.setOnClickListener(new MyClickListener());
    }

   private void register(){
       if((rUsername.getText().toString()).equals("") || (rPassword.getText().toString()).equals("")
               || (rgPassword.getText().toString()).equals("")){
           Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
       }
       else if(!(rPassword.getText().toString()).equals(rgPassword.getText().toString())){
           Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();

       }
       else{
            sendData();
       }

   }
   public class MyClickListener implements View.OnClickListener{

       @Override
       public void onClick(View v) {
          register();

       }
   }
    private void getData(){
        try{
            user.setUsername(URLEncoder.encode(rUsername.getText().toString(),"gb2312"));
            user.setPassword(URLEncoder.encode(rPassword.getText().toString(),"gb2312"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }
    private void sendData(){
        getData();
        String ss = IPAddress.IP+"ResetServlet";
        URL url = null;
        HttpURLConnection connection = null;
        boolean b = true;
        try {
            url = new URL(ss);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        try{
            connection = (HttpURLConnection)url.openConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            ClientChangType cc = new ClientChangType();
            cc.ObjectToXML(user,Person.class,connection);

            if(connection.getResponseCode()!=200){
                Toast.makeText(getApplicationContext(),"请求失败!",Toast.LENGTH_SHORT).show();
                throw new RuntimeException("请求URL失败");
            }
            InputStream is = connection.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String line = " ";
            while((line=bf.readLine()) != null){
                if("false".equals(line))
                    b=false;
            }
            is.close();
        }catch (IOException e){
            b = false;
            e.printStackTrace();
        }
        connection.disconnect();
        if(b){
            Intent intent = new Intent();
            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
            intent.setClass(this,LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
        }
    }



}
