package com.godream.defineview;

import com.godream.util.LogUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HomeViewPager extends ViewPager{
	public HomeViewPager(Context context) {
		super(context);
	}
	public HomeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/* 将MainViewPager  onInterceptTouchEvent在 MotionEvent.ACTION_MOVE时的返回值改变为false，这样滑动处理交给本HomeViewPager，而不是外部的viewpager
	 * @see android.support.v4.view.ViewPager#onInterceptTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			MainViewPager.MOVEByViewPgaer_FLAG = true;
			break;
        case MotionEvent.ACTION_MOVE:
        	break;
        case MotionEvent.ACTION_UP:
        	break;
		}
		boolean res = super.onInterceptTouchEvent(event);
		return res;
	}

	/* 将MainViewPager  onInterceptTouchEvent在 MotionEvent.ACTION_MOVE时的返回值改变为false，恢复默认情况
	 * @see android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			break;
        case MotionEvent.ACTION_MOVE:
        	break;
        case MotionEvent.ACTION_UP:
        	MainViewPager.MOVEByViewPgaer_FLAG = false;
        	break;
		}
		boolean res = super.onTouchEvent(event);
		return res;
	}

	
}
