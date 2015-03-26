package com.nmid.painterdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Toryang on 2015/3/25.
 */
public class RegisterActivity extends ActionBarActivity {
    private EditText rUsername,rPassword,rgPassword;
    private Button registerbutton;
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
            sendData(rUsername.getText().toString(),rPassword.getText().toString());
       }

   }
   public class MyClickListener implements View.OnClickListener{

       @Override
       public void onClick(View v) {
          register();

       }
   }

    private void sendData(String str1, String str2){
        final String params = "username="+str1+"&password="+str2;
        new Thread(){
            public void run(){
                String result = PostUtil.sendPost(IPAddress.IP,params);
            }
        }.start();
       }

}
