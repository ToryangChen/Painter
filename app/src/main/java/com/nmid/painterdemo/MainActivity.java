package com.nmid.painterdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nmid.painterdemo.com.nmid.application.MyApplication;
import com.nmid.painterdemo.com.nmid.view.SildeMenu;


public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout paintLayout;
    private LinearLayout guessLayout;

    private ImageButton mImgguess;
    private ImageButton mImgpaint;


    private Fragment paintFragment;
    private Fragment guessFragment;

    private Button change;
    private SildeMenu mLeftMenu;

    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        myApplication = new MyApplication();
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        paintLayout.setOnClickListener(this);
        guessLayout.setOnClickListener(this);
        change.setOnClickListener(this);
    }

    private void initView(){
        paintLayout = (LinearLayout)findViewById(R.id.mypaint);
        guessLayout = (LinearLayout)findViewById(R.id.myguess);

        mImgguess = (ImageButton)findViewById(R.id.button_guess);
        mImgpaint = (ImageButton)findViewById(R.id.button_paint);

        change = (Button)findViewById(R.id.slidebt);
        mLeftMenu = (SildeMenu) findViewById(R.id.id_menu);

    }
    private void setSelect(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch(i){
            case 0:
                myApplication.setFlag(false);
                if(guessFragment == null){
                    guessFragment = new GuessFragment();
                    transaction.add(R.id.id_content,guessFragment);

                }else{
                    transaction.show(guessFragment);

                }
                mImgguess.setImageResource(R.drawable.border);

                break;
            case 1:
                myApplication.setFlag(true);
                //System.out.println(myApplication.getFlag()+"");
                if(paintFragment == null){
                    paintFragment = new PaintFragment();
                    transaction.add(R.id.id_content,paintFragment);
                }else{
                    transaction.show(paintFragment);

                }
                mImgpaint.setImageResource(R.drawable.border);
                break;

        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(guessFragment != null){
            fragmentTransaction.hide(guessFragment);
        }
        if (paintFragment != null){
            fragmentTransaction.hide(paintFragment);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.myguess:
                resetImgs();
                //Toast.makeText(MainActivity.this,"myguess clicked",Toast.LENGTH_SHORT).show();
                setSelect(0);
                break;
            case R.id.mypaint:
                resetImgs();
                //Toast.makeText(MainActivity.this,"mypaint clicked",Toast.LENGTH_SHORT).show();
                setSelect(1);
                break;
            case R.id.slidebt:
                mLeftMenu.toggle();
                break;
        }

    }
    private void resetImgs() {
        mImgguess.setImageResource(R.drawable.border_null);
        mImgpaint.setImageResource(R.drawable.border_null);

    }
}
