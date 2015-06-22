package com.godream.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.godream.listener.ViewPagerSelectListener;

/**
*
* 类名称：FragmentViewPagerAdapter
* 
* 类描述：
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-14 上午9:46:53
* 
* 备注：
*
*/
public class FragmentViewPagerAdapter extends PagerAdapter implements OnPageChangeListener{
	private List<Fragment> fragmentList;
	private FragmentManager fragmentManager;
	private ViewPager viewPager;
	private int currentPagerIndex = 0;
	private ViewPagerSelectListener listener;
	
	public FragmentViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager , List<Fragment> fragments){
		this.fragmentList = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
		this.viewPager.setOnPageChangeListener(this);
	}
	
	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fragmentList.get(position).getView()); 
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragmentList.get(position);
		if(!fragment.isAdded()){
			FragmentTransaction ft =  fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commit();
			fragmentManager.executePendingTransactions();
		}
		
		if(fragment.getView().getParent() == null){
			container.addView(fragment.getView());
		}
		
		return fragment.getView();
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int i) {
		fragmentList.get(currentPagerIndex).onPause();
		if(fragmentList.get(i).isAdded()){
			fragmentList.get(i).onResume();
		}
		currentPagerIndex = i;
		if(listener != null)
			listener.onPageSelected(i);
	}

	public int getCurrentPagerIndex() {
		return currentPagerIndex;
	}

	public ViewPagerSelectListener getListener() {
		return listener;
	}

	public void setListener(ViewPagerSelectListener listener) {
		this.listener = listener;
	}

	
	
}
