package com.nmid.painterdemo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class RefreshListView extends ListView {
	View header;
	int headerHeight;
	int firstVisibleItem;
	boolean isMark;
	int scrollState;
	int starty;
	IReflashListener iReflashListener;
	int state;
	final int NONE = 0;
	final int PULL = 1;
	final int RELESE = 2;
	final int REFLASHING = 3;
	public RefreshListView(Context context) {
		super(context);
		initView(context);
	}
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	/**
	 * 初始化界面，添加顶部布局文件到listView
	 * @param context
	 */
	private void initView(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(new MyScrollListener());
		
	}
	
	/**
	 * 通知父布局,占用的宽、高
	 * @param view
	 */
	private void measureView(View view){
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if(p==null){
			p= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if(tempHeight>0){
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		}else{
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}
	
	/**
	 * 设置header布局的上边距
	 * @param topPadding
	 */
	
	private void topPadding(int topPadding){
		header.setPadding(header.getPaddingLeft(), topPadding, 
				header.getPaddingRight(), header.getPaddingBottom());
	}

	public class MyScrollListener implements OnScrollListener{

		@Override
		public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			firstVisibleItem = arg1;
			
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			scrollState = arg1;
			
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(firstVisibleItem == 0){
				isMark = true;
				starty = (int)ev.getY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if(state ==  RELESE){
				state = REFLASHING;
				reflashViewByState();
				iReflashListener.onRelash();
				
			}else if(state == PULL){
				state = NONE;
				isMark = true;
				reflashViewByState();
			}
		}
		
			
	
		return super.onTouchEvent(ev);
	}
	/**
	 * 判断移动过程中的操作
	 * @param ev
	 */
	private void onMove(MotionEvent ev){
		if(!isMark){
			return;
		}
		int tempy= (int)ev.getY();
		int space = tempy - starty;
		int topPadding = space - headerHeight;
		switch(state){
		case NONE:
			
			if(space>0){
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);
			if(space > headerHeight+30 
					//&& scrollState == SCROLL_STATE_TOUCH_SCROLL
					){
				state = RELESE;
				reflashViewByState();
			}
			break;
		case RELESE:
			topPadding(topPadding);
			if(space < headerHeight+30 ){
				state = PULL;
				reflashViewByState();
			}else if(space<=0){
				state = NONE;
				isMark = false;
				reflashViewByState();
			}
			break;
		case REFLASHING:
			break;
		}
		
	}
	
	private void reflashViewByState(){
		TextView tip = (TextView)header.findViewById(R.id.tip);
		ImageView arrow = (ImageView)header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar)header.findViewById(R.id.progress);
		RotateAnimation anim = new RotateAnimation(0,180,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180,0,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		switch(state){
		case NONE:
			arrow.clearAnimation();
			topPadding(-headerHeight);
			break;
		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("下拉可以刷新");
			arrow.setAnimation(anim1);
			break;
		case RELESE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("松开可以刷新");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFLASHING:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("正在刷新．．．");
			arrow.clearAnimation();
			break;
		}
	}
	/**
	 * 获取完数据
	 */
	public void reflashComplete(){
		state = NONE;
		isMark = false;
		reflashViewByState();
		TextView lastUpdateTime = (TextView)header.findViewById(R.id.lastupdate_text);
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastUpdateTime.setText(time);
	}
	public void setInterface(IReflashListener iReflashListener){
		this.iReflashListener = iReflashListener;
	}
	/**
	 * 刷新数据接口
	 * @author Toryang
	 *
	 */
	public interface IReflashListener{
		public void onRelash();
	}
}
