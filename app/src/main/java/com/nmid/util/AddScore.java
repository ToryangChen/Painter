package com.nmid.util;

import org.apache.http.HttpConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tornado on 2015/5/9.
 */
public class AddScore implements Runnable{
    @Override
    public void run() {
        BaseData baseData = new BaseData();
        try {
            URL addScoreURL = new URL(IPAddress.IP+"addScore.php?username="+baseData.getUsername());
            URLConnection connection = addScoreURL.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
