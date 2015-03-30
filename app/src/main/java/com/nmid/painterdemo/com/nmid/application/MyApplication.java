package com.nmid.painterdemo.com.nmid.application;

import android.app.Application;

/**
 * Created by Toryang on 2015/3/30.
 */
public class MyApplication extends Application {
    private  boolean flag;
    public boolean getFlag(){
        return this.flag;
    }
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    @Override
    public void onCreate() {
        flag = false;
        super.onCreate();
    }
}
