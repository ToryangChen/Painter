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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nmid.adapter.GuessLVAdapter;
import com.nmid.adapter.ListAdapter;
import com.nmid.util.ApkEntity;
import com.nmid.util.BaseData;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.nmid.util.URLConnect;
import com.nmid.util.Upload;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.nmid.painterdemo.com.nmid.GuessLVAdapter;

/**
 * Created by Toryang on 2015/3/30.
 */
public class GuessFragment extends Fragment implements RefreshListView.IReflashListener{


    //Handler handler = new Handler();
    ListData listData;
    RefreshListView listView;
    GuessLVAdapter guessLVAdapter;
    ArrayList<ApkEntity> apk_list;
    View rootView;
    LayoutInflater inflater;
    public class Handler2 extends Handler{
        public Handler2(){}
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                listData = (ListData) msg.obj;
                //System.out.println(listData.getList().size()+"= size ");
                guessLVAdapter = new GuessLVAdapter(getActivity(), apk_list, listData);
                listView.setAdapter(guessLVAdapter);
            }
            if (msg.what==2){
                listData = (ListData) msg.obj;
                showList(rootView,inflater);
                listView.reflashComplete();
            }
        }

    };
    Handler2 handler2 =  new Handler2();
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        BaseData baseData= new BaseData();
        new URLConnect(baseData.getUsername(),handler2,1).start();
        rootView = inflater.inflate(R.layout.activity_guess,container,false);
        this.inflater = inflater;
        setData();
        showList(rootView,inflater);
        return rootView;
    }

    private void setData() {
        apk_list = new ArrayList<ApkEntity>();
//        for (int i = 0; i < listData.list.size(); i++) {
//            ApkEntity entity = new ApkEntity();
//            entity.setPaintJson(listData.list.get(i));
//            entity.setName(listData.map.get(entity.getPaintJson()));
//            apk_list.add(entity);
//        }


    }
    private void showList(View rootView,LayoutInflater inflater) {
        if (guessLVAdapter == null) {
            listView = (RefreshListView)rootView.findViewById(R.id.guessLV);

            listView.setInterface(this);

            listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    EditText editText = (EditText) view.findViewById(R.id.answerET);
                    editText.requestFocus();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        } else {
            guessLVAdapter.onDateChange(listData);
        }
    }
    private void setReflashData(String username) {
        apk_list.clear();
    }

    @Override
    public void onRelash() {
        final BaseData baseData = new BaseData();
        new URLConnect(baseData.getUsername(),handler2,2).start();
    }

}
