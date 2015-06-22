package com.godream.defineview;

import com.godream.util.LogUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MainViewPager extends ViewPager{
	public static boolean MOVEByViewPgaer_FLAG;
	public MainViewPager(Context context) {
		super(context);
	}
	public MainViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			break;
        case MotionEvent.ACTION_MOVE:
        	if(MOVEByViewPgaer_FLAG){
        		//滑动homeviewpager时
        		return false;
        	}else{
        		//滑动homegridview时
        		break;
        	}
        case MotionEvent.ACTION_UP:
        	break;
		}
		boolean res = super.onInterceptTouchEvent(event);
		return res;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			break;
        case MotionEvent.ACTION_MOVE:
        	break;
        case MotionEvent.ACTION_UP:
        	break;
		}
		boolean res = super.onTouchEvent(event);
		return res;
	}

	
}
