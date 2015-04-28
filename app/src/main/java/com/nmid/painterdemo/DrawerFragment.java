package com.nmid.painterdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Toryang on 2015/4/26.
 */
public class DrawerFragment extends Fragment implements View.OnClickListener {

    private LinearLayout paintLayout;
    private LinearLayout guessLayout;

    private ImageButton mImgguess;
    private ImageButton mImgpaint;


    private Fragment paintFragment;
    private Fragment guessFragment;

    private Button change;


    private DrawerLayout layout;
    private View view;


    public  DrawerFragment(){
        super();
    }

    public void setDrawerLayout(DrawerLayout layout, View view) {
        this.layout = layout;
        this.view = view;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container,
                false);

        paintLayout = (LinearLayout)rootView.findViewById(R.id.mypaint);
        guessLayout = (LinearLayout)rootView.findViewById(R.id.myguess);

        mImgguess = (ImageButton)rootView.findViewById(R.id.button_guess);
        mImgpaint = (ImageButton)rootView.findViewById(R.id.button_paint);

        change = (Button)rootView.findViewById(R.id.slidebt);

        paintLayout.setOnClickListener(this);
        guessLayout.setOnClickListener(this);
        change.setOnClickListener(this);

        setSelect(0);

        return rootView;
    }

    private void setSelect(int i){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch(i){
            case 0:

                if(guessFragment == null){
                    guessFragment = new GuessFragment();
                    transaction.add(R.id.id_content,guessFragment);

                }else{
                    transaction.show(guessFragment);

                }
                mImgguess.setImageResource(R.drawable.border);

                break;
            case 1:

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
                setSelect(0);
                break;
            case R.id.mypaint:
                resetImgs();
                setSelect(1);
                break;

            case R.id.slidebt:
                if (!layout.isDrawerOpen(view)) {
                    layout.openDrawer(view);
                }


        }

    }
    private void resetImgs() {
        mImgguess.setImageResource(R.drawable.border_null);
        System.out.println("set-------------------");
        mImgpaint.setImageResource(R.drawable.border_null);

    }
}
