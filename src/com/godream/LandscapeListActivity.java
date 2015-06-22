package com.godream;

import java.util.ArrayList;
import java.util.List;

import com.godream.adapter.LandscapeListAdapter;
import com.godream.bean.Landscape;
import com.godream.util.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
*
* 类名称：LandscapeList
* 
* 类描述：景点列表
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-23 上午11:39:34
* 
* 备注：
*
*/
public class LandscapeListActivity extends SuperActivity{
	private ListView mLandscapeList;
	private List<Landscape> list;
	private LandscapeListAdapter adapter;
	/**标题栏控件*/
	private TextView mTopCenterTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseView.setContentView(this,0,R.layout.base_view_titlecenter, 
				0,R.layout.landscapelist);
		setContentView(baseView.getContentView());
		mTopCenterTxt = (TextView) findViewById(R.id.top_title);
		mTopCenterTxt.setText(getString(R.string.lands_title));
		
		list = new ArrayList<Landscape>();
		
		for(int i = 0; i < 50; i++){
			Landscape land = new Landscape(i, "第" + i +"天");
			list.add(land);
		}
		adapter = new LandscapeListAdapter(this, list);
		mLandscapeList = (ListView) findViewById(R.id.landscape_List);
		mLandscapeList.setAdapter(adapter);
		
		mLandscapeList.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_FLING:
					//惯性
					adapter.isBusy = true;
					break;
				case SCROLL_STATE_IDLE:
					//空闲
					adapter.isBusy = false;
					adapter.notifyDataSetChanged();//快速滑动后,页面停止,然后请求此时页面显示的item
					break;
				case SCROLL_STATE_TOUCH_SCROLL:
					//拖动
					adapter.isBusy = false;
					break;
				default:
					break;
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}
}

















