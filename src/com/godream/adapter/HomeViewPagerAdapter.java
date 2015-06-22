package com.godream.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
*
* 类名称：HomeViewPagerAdapter
* 
* 类描述：
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-19 下午12:01:37
* 
* 备注：
*
*/
public class HomeViewPagerAdapter extends PagerAdapter{
	private List<View> viewList;
	public HomeViewPagerAdapter(List<View> viewList) {
		this.viewList = viewList;
	}
	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(viewList.get(position));
		return viewList.get(position);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(viewList.get(position));
	}
	
}

