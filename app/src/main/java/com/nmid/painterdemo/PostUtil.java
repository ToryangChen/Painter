package com.nmid.painterdemo;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Toryang on 2015/3/27.
 */
public class PostUtil {

    public static String sendPost(String url,String params){
        String result = null;
        PrintWriter out = null;
        BufferedReader in = null;
        URLConnection connection = null;
        URL realurl = null;
        try {
            realurl = new URL(url);

        }catch (MalformedURLException e){
            e.printStackTrace();
            Log.d("qusetion-------------", e.toString());

        }
        try {

            connection = realurl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            out = new PrintWriter(connection.getOutputStream());
            out.print(params);
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result += "\n" +line;
                //System.out.println(line);
            }

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("发送POST请求出现异常！"+e);
        }
        finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
