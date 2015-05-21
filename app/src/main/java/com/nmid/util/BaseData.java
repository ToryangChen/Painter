package com.nmid.util;

/**
 * Created by Toryang on 2015/4/28.
 */
public class BaseData {
    private static String score,number;
    private static String username;
    private static int ScreenWidth,ScreenHeight;
    public void setBaseData(String score,String number){
        this.score = score;
        this.number = number;
    }
    public void setSreen(int ScreenWidth,int ScreenHeight){
        this.ScreenWidth = ScreenWidth;
        this.ScreenHeight = ScreenHeight;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public int getScreenWidth(){
        return  ScreenWidth;
    }
    public int getScreenHeight(){
        return ScreenWidth;
    }

    public String getNumber() {
        return number;
    }

    public String getScore() {
        return score;
    }

    public String getUsername(){
        return username;
    }
}
