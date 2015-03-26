package com.nmid.painterdemo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * Created by Toryang on 2015/3/25.
 */
public class ClientChangType {
      public boolean state = false;

    public void ObjectToXML(Object o,Class<?> d,HttpURLConnection connection){
        DataOutputStream outputStream = null;
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("detail",d);
        String www = xStream.toXML(o);
        connection.setConnectTimeout(6*1000);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        try{
            connection.setRequestMethod("POST");
        }catch (ProtocolException e){
            e.printStackTrace();
        }
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","gb2312");
        connection.setRequestProperty("Content-length",String.valueOf(www.length()));
        connection.setRequestProperty("Content-type","text/xml;charset gb2312");

        try{
            outputStream = new DataOutputStream(connection.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            outputStream.write(www.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
