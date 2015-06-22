package com.godream.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

import com.godream.R;
import com.godream.adapter.MenuListAdapter;
import com.godream.bean.MenuBean;
import com.godream.service.AsyncImageLoader;
import com.godream.util.LogUtil;
import com.slidingmenu.lib.SlidingMenu;


public class SlideFragment extends Fragment{
	private ListView mMenuListView;
	public SlidingMenu slidMenu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
		LogUtil.getInstance().i("onCreate");
	    super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtil.getInstance().i("onCreateView");
		View menuView = inflater.inflate(R.layout.menu_layout, null);
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		MenuBean bean1 = new MenuBean(1,R.drawable.menu_sequence_play,"清除缓存");
		MenuBean bean2 = new MenuBean(2,R.drawable.menu_setting,"设置");
		menuList.add(bean1);menuList.add(bean2);
		mMenuListView = (ListView) menuView.findViewById(R.id.menu_list);
		mMenuListView.setSelector(R.drawable.menu_listitem_selector);
		LayoutParams lp = (LinearLayout.LayoutParams)mMenuListView.getLayoutParams();
		mMenuListView.setAdapter(new MenuListAdapter(getActivity(), menuList,lp.height));
		mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				case 0:
					AsyncImageLoader.getInstance(getActivity()).clearCache();
					Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(getActivity(), "点击了设置按钮", Toast.LENGTH_SHORT).show();
//					slidMenu.showContent();
					break;
				default:
					break;
				}
			}
		});
		return menuView;
	}
}
