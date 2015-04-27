package com.nmid.painterdemo;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.LinearLayout;



public class MainActivity extends FragmentActivity {

    public DrawerLayout drawerLayout;// 侧边栏布局
    public LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout);

        initViews();

    }
    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        linearLayout = (LinearLayout)findViewById(R.id.slide_menu);

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
        return super.onKeyDown(keyCode, event);
    }


}
