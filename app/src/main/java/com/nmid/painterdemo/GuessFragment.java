package com.nmid.painterdemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.nmid.util.URLConnect;
import com.nmid.util.Upload;

//import com.nmid.painterdemo.com.nmid.GuessLVAdapter;

/**
 * Created by Toryang on 2015/3/30.
 */
public class GuessFragment extends Fragment {

    ListView guessLV = null;
    public static GuessLVAdapter guessLVAdapter =null;
    Button update = null;
    Handler Ghandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            guessLVAdapter.notifyDataSetChanged();

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_guess,container,false);
        guessLV = (ListView)rootView.findViewById(R.id.guessLV);
        update = (Button) rootView.findViewById(R.id.test);
        guessLVAdapter = new GuessLVAdapter(inflater);
        guessLV.setAdapter(guessLVAdapter);
        guessLV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    update.setVisibility(View.VISIBLE);
                } else {
                    update.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        update.getBackground().setAlpha(100);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new URLConnect(Ghandler).start();
            }
        });

        return rootView;
    }

}
