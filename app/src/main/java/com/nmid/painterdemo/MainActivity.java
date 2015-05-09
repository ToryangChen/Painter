package com.nmid.painterdemo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmid.application.MyApplication;
import com.nmid.util.BaseData;


public class MainActivity extends FragmentActivity implements View.OnClickListener{

    public DrawerLayout drawerLayout;// 侧边栏布局
    public LinearLayout linearLayout,myPaintLayout,mesageLyout,logoutLayout;

    private TextView username,score,number,aboutus;

    BaseData baseData =new BaseData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout);
        MyApplication.getInstance().addActivity(MainActivity.this);
        MyApplication.setFlag(true);
        initViews();
        initEvent();

    }
    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        linearLayout = (LinearLayout)findViewById(R.id.slide_menu);

        myPaintLayout = (LinearLayout)findViewById(R.id.menu_mypaint);
        mesageLyout = (LinearLayout)findViewById(R.id.menu_message);
        logoutLayout = (LinearLayout)findViewById(R.id.menu_logout);

        username = (TextView)findViewById(R.id.getUser);
        score = (TextView)findViewById(R.id.score_paint);
        number = (TextView)findViewById(R.id.mumber_paint);

        aboutus = (TextView)findViewById(R.id.about_us);


        initFragments();

    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DrawerFragment fragment = new DrawerFragment();
        fragment.setDrawerLayout(drawerLayout, linearLayout);
        transaction.add(R.id.main_content, fragment);
        transaction.commit();
    }

    private void initEvent(){
        System.out.println(baseData.getUsername()+baseData.getScore()+baseData.getNumber()+"111");

        username.setText(baseData.getUsername()+"");
        score.setText(baseData.getScore()+"");
        number.setText(baseData.getNumber()+"");

        myPaintLayout.setOnClickListener(this);
        mesageLyout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);
        aboutus.setOnClickListener(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // 使用menu键打开或关闭侧边栏
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (drawerLayout.isDrawerOpen(linearLayout)) {
                drawerLayout.closeDrawer(linearLayout);
            } else {
                drawerLayout.openDrawer(linearLayout);
            }
        }
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dialog();

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_us:
                Dialog();
                break;
            case R.id.menu_mypaint:
                Intent intent = new Intent(MainActivity.this,MyPainterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.menu_message:
                break;
            case R.id.menu_logout:
                System.exit(0);
                break;

        }
    }

    public void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("关于我们");
        builder.setMessage("雏鹰计划三期“大画师”作品！");
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private  void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否退出？");
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MyApplication.getInstance().exit();

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
}
