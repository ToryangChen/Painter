package com.nmid.painterdemo;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.nmid.adapter.ListAdapter;
import com.nmid.application.MyApplication;
import com.nmid.util.ApkEntity;
import com.nmid.util.ListData;

import java.util.ArrayList;


/**
 * Created by Toryang on 2015/5/6.
 */
public class MyPainterActivity extends ActionBarActivity implements RefreshListView.IReflashListener {
    private RefreshListView listView;
    private ListAdapter listAdapter;
    ArrayList<ApkEntity> apk_list;

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
        setData();
        showList(apk_list);

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

    private void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < ListData.mypainterlist.size(); i++) {
            ApkEntity entity = new ApkEntity();
            entity.setPaintJson(ListData.mypainterlist.get(i));
            entity.setName(ListData.map.get(entity.getPaintJson()));
            apk_list.add(entity);
        }
    }
    private void setReflashData() {
        for (int i = 0; i < ListData.mypainterlist.size(); i++) {
            ApkEntity entity = new ApkEntity();
            entity.setPaintJson(ListData.mypainterlist.get(i));
            entity.setName(ListData.map.get(entity.getPaintJson()));
            apk_list.add(entity);
        }
    }

    @Override
    public void onRelash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //获取最新数据
                setReflashData();
                //通知界面显示
                showList(apk_list);
                //通知listview 刷新数据完毕；
                listView.reflashComplete();
            }
        }, 2000);
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
