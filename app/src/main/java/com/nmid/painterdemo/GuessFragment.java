package com.nmid.painterdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nmid.adapter.GuessLVAdapter;
import com.nmid.adapter.ListAdapter;
import com.nmid.util.ApkEntity;
import com.nmid.util.ListData;
import com.nmid.util.URLConnect;
import com.nmid.util.Upload;

import java.util.ArrayList;

//import com.nmid.painterdemo.com.nmid.GuessLVAdapter;

/**
 * Created by Toryang on 2015/3/30.
 */
public class GuessFragment extends Fragment implements RefreshListView.IReflashListener{

    RefreshListView listView;
    GuessLVAdapter guessLVAdapter;
    ArrayList<ApkEntity> apk_list;
    View rootView;
    LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_guess,container,false);
        this.inflater = inflater;
        setData();
        showList(apk_list,rootView,inflater);
        return rootView;
    }

    private void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < ListData.list.size(); i++) {
            ApkEntity entity = new ApkEntity();
            entity.setPaintJson(ListData.list.get(i));
            entity.setName(ListData.map.get(entity.getPaintJson()));
            apk_list.add(entity);
        }

    }
    private void showList(ArrayList<ApkEntity> apk_list,View rootView,LayoutInflater inflater) {
        if (guessLVAdapter == null) {
            listView = (RefreshListView)rootView.findViewById(R.id.guessLV);
            listView.setInterface(this);
            guessLVAdapter = new GuessLVAdapter(getActivity(),apk_list);
            listView.setAdapter(guessLVAdapter);
        } else {
            guessLVAdapter.onDateChange(apk_list);
        }
    }
    private void setReflashData() {
        for (int i = 0; i < ListData.list.size(); i++) {
            ApkEntity entity = new ApkEntity();
            entity.setPaintJson(ListData.list.get(i));
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
                // TODO Auto-generated method stub
                //获取最新数据
                setReflashData();
                //通知界面显示
                showList(apk_list,rootView,inflater);
                //通知listview 刷新数据完毕；
                listView.reflashComplete();
            }
        }, 2000);
    }

}
