package com.nmid.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.nmid.util.BaseData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Toryang on 2015/4/27.
 */
public class HttpThread extends Thread{
    String str;

    String Url;

    String username;

    String password;
    Handler handler;
    BaseData baseData = new BaseData();
    Context context;
    public static String[]  ss = new String[4];
    public HttpThread(String Url,String username, String password, Handler handler){
        this.Url = Url;
        this.username = username;
        this.password = password;
        this.handler = handler;
    }

    private void doGet(){
        Url = Url+"?username="+username+"&password="+password;
        try {
            URL httpUrl = new URL(Url);
            HttpURLConnection conn =(HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while((str = reader.readLine())!=null){

                sb.append(str);
                sb.append(",");
            }
            str = sb.toString();
            ss = str.split(",");

            System.out.println("result="+str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doGet();
        try{
            Message message = new Message();
            message.arg1 = Integer.parseInt(ss[0]);
            System.out.println("score..."+ss[1]);
            System.out.println("number..."+ss[2]);
            baseData.setBaseData(ss[1],ss[2]);
            System.out.println("message"+message.arg1);
            handler.sendMessage(message);
        }catch (NumberFormatException e){
            Toast.makeText(context,"网络连接错误",Toast.LENGTH_SHORT).show();
        }


    }


}
