package com.nmid.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tornado on 2015/5/9.
 */
public class AddProduction extends Thread {
    @Override
    public void run() {
        BaseData baseData = new BaseData();
        try {
            //http://125.81.59.144/GreatArtist/addScore.php?username=zhangsan
            URL addScoreURL = new URL(IPAddress.IP+"addProduction.php?username="+baseData.getUsername());
            HttpURLConnection connection = (HttpURLConnection) addScoreURL.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            InputStream inputStream = connection.getInputStream();
            int ch;
            StringBuffer buffer = new StringBuffer();
            while((ch=inputStream.read())!=-1){
                buffer.append((char)ch);
            }
            inputStream.close();
            connection.disconnect();
            baseData.setBaseData(baseData.getScore(),buffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
