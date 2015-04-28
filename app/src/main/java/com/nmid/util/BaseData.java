package com.nmid.util;

/**
 * Created by Toryang on 2015/4/28.
 */
public class BaseData {
    private static String score,number;
    private static String username;
    public void setBaseData(String score,String number){
        this.score = score;
        this.number = number;
    }
    public void setUsername(String username){
        this.username = username;
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
