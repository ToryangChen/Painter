package com.nmid.painterdemo.com.nmid.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.nmid.painterdemo.MainActivity;
import com.nmid.painterdemo.com.nmid.application.MyApplication;

import java.lang.reflect.TypeVariable;

/**
 * Created by Toryang on 2015/3/30.
 */
public class SildeMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    private int mScreenwidth;
    private int mMenuRightPadding =100;

    private int mMenuWidth;

    private boolean once = false;
    private boolean isOpen;

    private MyApplication myApplication;
    /**
     * 为使用自定义属性时
     * @param context
     * @param attrs
     */
    public SildeMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        myApplication = new MyApplication();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenwidth = outMetrics.widthPixels;


        mMenuRightPadding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,
                context.getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            mWapper = (LinearLayout)getChildAt(0);
            mMenu = (ViewGroup)mWapper.getChildAt(0);
            mContent = (ViewGroup)mWapper.getChildAt(1);

            mMenuWidth = mMenu.getLayoutParams().width = mScreenwidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenwidth;
            once = true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action =ev.getAction();
        switch(action){
            case MotionEvent.ACTION_UP:
                int scroll = getScrollX();
                if(scroll>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }else{
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void OpenMenu()
    {
        if(isOpen)return;
        this.smoothScrollTo(0,0);
        isOpen = true;


    }
    public void closeMenu(){
        if(!isOpen)return;

        this.smoothScrollTo(mMenuWidth,0);
        isOpen = false;
    }

    public  void toggle(){
        if(isOpen){
            closeMenu();;
        }else{
            OpenMenu();
        }

    }

}
