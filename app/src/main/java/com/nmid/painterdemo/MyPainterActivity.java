package com.nmid.painterdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.AbsListView;

import com.nmid.adapter.MyPaintAdapter;

/**
 * Created by Toryang on 2015/5/6.
 */
public class MyPainterActivity extends ActionBarActivity {
    private CustomListView listView;
    private MyPaintAdapter myPaintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypaint);
        initView();
    }

    private void initView() {
        listView = (CustomListView)findViewById(R.id.list_mypaint);
        myPaintAdapter = new MyPaintAdapter(getLayoutInflater());
        listView.setAdapter(myPaintAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
}
