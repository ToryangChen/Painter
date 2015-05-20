package com.nmid.util;

import android.os.Handler;
import android.os.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
    public static String[]  ss = new String[4];
    public HttpThread(String Url,String username, String password, Handler handler){
        this.Url = Url;
        this.username = username;
        this.password = password;
        this.handler = handler;
    }

    private void doPost(){
        PrintWriter out = null;
        BufferedReader in = null;
        String params = "username="+username+"&password="+password;
        System.out.println(username+password+"  ");
        try {
            URL httpUrl = new URL(Url);
            URLConnection conn = httpUrl.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","keep-Alive");
            conn.setRequestProperty("user_agent",
                    "Mozilla/4.0(compatible; MSIE 6.0 ; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            //String line;
//            while((line = in.readLine())!=null){
//
//            }

//            HttpURLConnection conn =(HttpURLConnection)httpUrl.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setReadTimeout(5000);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while((str = in.readLine())!=null){

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
        doPost();
        try{
            Message message = new Message();
            message.arg1 = Integer.parseInt(ss[0]);
            if (ss.length>1){
                baseData.setBaseData(ss[1],ss[2]);
                baseData.setUsername(username);
            }
            handler.sendMessage(message);
        }catch (NumberFormatException e){
            System.out.println("e");
        }
    }
}
