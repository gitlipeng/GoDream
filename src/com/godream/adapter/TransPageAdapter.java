package com.godream.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
*
* 类名称：TransPageAdapter
* 
* 类描述：过渡页面ViewPager的adapter
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-12 上午10:56:56
* 
* 备注：
*
*/
public class TransPageAdapter extends PagerAdapter{
	private List<View> viewList;
	public TransPageAdapter(List<View> viewList) {
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

