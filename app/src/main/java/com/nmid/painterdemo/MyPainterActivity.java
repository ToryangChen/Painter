package com.nmid.painterdemo;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.nmid.adapter.ListAdapter;
import com.nmid.application.MyApplication;
import com.nmid.util.ApkEntity;
import com.nmid.util.BaseData;
import com.nmid.util.ListData;
import com.nmid.util.getMyPainter;

import java.util.ArrayList;


/**
 * Created by Toryang on 2015/5/6.
 */
public class MyPainterActivity extends ActionBarActivity implements RefreshListView.IReflashListener {
    private RefreshListView listView;
    BaseData baseData =new BaseData();
    private ListAdapter listAdapter =null;
    ArrayList<ApkEntity> apk_list;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                showList((ArrayList<ApkEntity>)msg.obj);
            }
            if(msg.what == 2){
                showList((ArrayList<ApkEntity>)msg.obj);
                listView.reflashComplete();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypaint);
        MyApplication.getInstance().addActivity(MyPainterActivity.this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new getMyPainter(baseData.getUsername(),handler,1).start();

    }
    private void showList(ArrayList<ApkEntity> apk_list) {
        if (listAdapter == null) {
            listView = (RefreshListView) findViewById(R.id.list_mypaint);
            listView.setInterface(this);
            listAdapter = new ListAdapter(this,apk_list);
            listView.setAdapter(listAdapter);
        } else {
            listAdapter.onDateChange(apk_list);
        }
    }

    @Override
    public void onRelash() {
        new getMyPainter(baseData.getUsername(),handler,2).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
