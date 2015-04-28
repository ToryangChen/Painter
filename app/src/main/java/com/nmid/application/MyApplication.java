package com.nmid.application;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Toryang on 2015/4/28.
 */
public class MyApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;
    private MyApplication(){

    }
    public static  MyApplication getInstance(){
        if (null == instance){
            instance = new MyApplication();

        }
        return instance;
    }
    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void exit(){
        for (Activity activity:activityList){
            activity.finish();
        }
        System.exit(0);
    }
}
