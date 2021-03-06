package com.nmid.util;

import android.os.Environment;
import android.os.StrictMode;

import com.nmid.util.IPAddress;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by tornado on 2015/4/7.
 */
public class Upload extends Thread{
    String fileName =null;
    String uploadFilename = null;
    BaseData baseData =new BaseData();
    public Upload(String fileName){
        this.fileName = fileName;
    }
    public void run()
    {
        uploadFilename= baseData.getUsername()+"$"+fileName;
        synchronized (fileName) {
            sendName(fileName);
            sendPicture();
        }
    }
    private void sendPicture(){
        File sdCardDir = Environment.getExternalStorageDirectory();
        String path = sdCardDir.getPath()+"/大画师";
        String uploadFile =path+"/"+fileName;
        String postUrl = IPAddress.IP+"uploading.php";
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {

            URL url = new URL(postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
            con.setDoOutput(true);
          /* Read from the connection. Default is true.*/
            con.setDoInput(true);
          /* Post cannot use caches */
            con.setUseCaches(false);
          /* Set the post method. Default is GET*/
            con.setRequestMethod("POST");
          /* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            con.setRequestProperty("Content-Type", "multipart/form-data;;boundary=" + boundary);
          /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
         //   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
          /* 设置DataOutputStream，getOutputStream中默认调用connect()*/
            //String ff = URLEncoder.encode(fileName,"GBK");
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);

            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file\";filename=\"" +
                    uploadFilename + "\"" + end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
          /* 设置每次写入8192bytes */
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
          /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
//            ds.writeBytes(twoHyphens + boundary + end);
//            ds.writeBytes("123" + end);
//            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* 关闭流，写入的东西自动生成Http正文*/
            fStream.close();
          /* 关闭DataOutputStream */
            ds.close();
          /* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
          /* 显示网页响应内容 */
            //      Toast.makeText(MainActivity.this, b.toString().trim(), Toast.LENGTH_SHORT).show();//Post成功
        } catch (Exception e)
        {
            /* 显示异常信息 */
            //       Toast.makeText(MainActivity.this, "Fail:" + e, Toast.LENGTH_SHORT).show();//Post失败
        }

    }

    private void sendName(String fileName){
//        String[] ss = fileName.split("\\.");
//        System.out.println(ss[0]+"1111  ");
//        String url = IPAddress.IP+"GreatArtist/answer.php";;
//        try {
//            URL httpUrl = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setReadTimeout(5000);
//            OutputStream out = conn.getOutputStream();
//            out.write(ss[0].getBytes());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String str ;
//            while((str=reader.readLine())!=null){
//                sb.append(str);
//            }
//            System.out.println("response:"+sb);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
   }
}
