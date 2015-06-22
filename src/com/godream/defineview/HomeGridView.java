package com.godream.defineview;

import com.godream.util.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class HomeGridView extends GridView{
	public HomeGridView(Context context) {
		super(context);
	}
	public HomeGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public HomeGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			MainViewPager.MOVEByViewPgaer_FLAG = false;
			break;
        case MotionEvent.ACTION_MOVE:
        	break;
        case MotionEvent.ACTION_UP:
        	break;
		}
		boolean res = super.onInterceptTouchEvent(event);
		return res;
	}

	/* 修改了MotionEvent.ACTION_MOVE是返回值,返回false，交给viewpager去处理滑动
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			break;
        case MotionEvent.ACTION_MOVE:
        	return false;
        case MotionEvent.ACTION_UP:
        	performClick();
        	break;
		}
		boolean res = super.onTouchEvent(event);
		return res;
	}
	
}
