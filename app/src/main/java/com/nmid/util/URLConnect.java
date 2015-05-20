package com.nmid.util;



import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.nmid.application.MyApplication;
import com.nmid.painterdemo.GuessFragment;

import org.apache.http.HttpConnection;
import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tornado on 2015/4/22.
 */
public class URLConnect extends Thread{

    public ListData listData  =new ListData();
    Handler handler;
    int MsgWhat;
    private String username;
    public URLConnect(String username,Handler handler,int i){
        MsgWhat = i;
        this.username= username;
        this.handler = handler;
    }
    @Override
    public void run()  {
        String[] json  ;
        List<String> list = new ArrayList<>();
        Map<String,String> map= new HashMap<>();
        try {
            String answerURL = IPAddress.IP+"pushOther.php?username="+username;
            URL getURL = new URL(answerURL);
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
//            connection.setRequestProperty("Content-Type", "application/json");


            InputStream is = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }

            String str = b.toString().trim();
            System.out.println(str+"=buf");
            System.out.println("-----------------------------------------");
            json = str.split("\\</br>");
            for (String s : json){
                System.out.println(s);
                JSONObject json1= new JSONObject(s);
                String answer = json1.getString("answer");
                String path = json1.getString("url");
                System.out.println(answer);
                map.put(path,answer);
                list.add(path);
            }
            is.close();
            connection.disconnect();


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        listData.setDataList(list,map);
        Message message1 = new Message();
        message1.what = MsgWhat;
        message1.obj =  listData;
        handler.sendMessage(message1);

        // {"answer":"pic1","url":"benbenla-04d.jpg"}


    }


}
