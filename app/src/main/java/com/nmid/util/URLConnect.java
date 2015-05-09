package com.nmid.util;



import android.app.Application;
import android.os.Handler;

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
    Handler Ghandler;
    private String username;
    public URLConnect(Handler handler,String username){
        Ghandler = handler;
        this.username= username;
    }
    @Override
    public void run()  {
        String[] json =null ;
        List<String> list = new ArrayList<>();
        Map<String,String> map= new HashMap<>();
        try {
          String answerURL = IPAddress.IP+"GreatArtist/pushOther.php?username="+username;
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



        try {
            String answerURL2 = IPAddress.IP+"GreatArtist/pushMyself.php?username="+username;
            URL getURL2 = new URL(answerURL2);
            HttpURLConnection connection = (HttpURLConnection) getURL2.openConnection();
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
            ListData.mypaintermap = map;
            ListData.mypainterlist = list;
        }

    }
}
