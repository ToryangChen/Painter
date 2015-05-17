package com.nmid.util;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tornado on 2015/5/14.
 */
public class getMyPainter extends Thread {
    ListData listData;
    String username;
    Handler handler;
    int x;
    List<ApkEntity> apk_list = new ArrayList<>();

    public getMyPainter(String username, Handler handler,int i) {
        this.handler = handler;
        this.username = username;
        x= i;
    }


    @Override
    public void run() {

        try {
            String answerURL2 = IPAddress.IP + "GreatArtist/pushMyself.php?username="+username ;
            URL getURL2 = new URL(answerURL2);
            HttpURLConnection connection2 = (HttpURLConnection) getURL2.openConnection();
            connection2.setDoInput(true);
            connection2.setDoOutput(true);
            connection2.setUseCaches(false);
            connection2.setRequestMethod("GET");
            connection2.setRequestProperty("Connection", "Keep-Alive");
            connection2.setRequestProperty("Charset", "UTF-8");
//            connection.setRequestProperty("Content-Type", "application/json");


            InputStream is2 = connection2.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is2.read()) != -1) {
                b.append((char) ch);
            }
            is2.close();
            String str2 = b.toString().trim();
            System.out.println(str2+"=buf");
            String[] json = str2.split("\\</br>");
            for (String s : json) {
                JSONObject json1 = new JSONObject(s);
                String answer = json1.getString("answer");
                String path = json1.getString("url");
                ApkEntity apkEntity = new ApkEntity();
                apkEntity.setName(answer);
                apkEntity.setPaintJson(path);
                apk_list.add(apkEntity);
            }


            } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message msg = new Message();
        msg.what = x;
        msg.obj = apk_list;
        handler.sendMessage(msg);

    }
}
