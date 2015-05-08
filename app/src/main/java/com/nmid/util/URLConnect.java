package com.nmid.util;



import android.app.Application;
import android.os.Handler;

import com.nmid.application.MyApplication;
import com.nmid.painterdemo.GuessFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
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
    Handler Ghandler;
    public URLConnect(Handler handler){
        Ghandler = handler;
    }
    @Override
    public void run()  {
        String[] json =null ;
        List<String> list = new ArrayList<>();
        Map<String,String> map= new HashMap<>();
        try {
          String answerURL = IPAddress.IP+"GreatArtist/push.php";
            URL postURL = new URL(answerURL);
            HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
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
            System.out.println(b.toString().trim());
            String str = b.toString().trim();
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

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        synchronized (list) {
            ListData.map = map;
            ListData.list = list;
        }

        if(MyApplication.getFlag()){
            Ghandler.sendEmptyMessage(1);
        }

        // {"answer":"pic1","url":"benbenla-04d.jpg"}

    }
}
