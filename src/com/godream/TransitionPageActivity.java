package com.godream;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

import com.godream.adapter.TransPageAdapter;
import com.godream.common.Constant;
import com.godream.util.LogUtil;

/**
*
* 类名称：TransitionPageActivity
* 
* 类描述：过渡页面
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-12 上午9:13:56
* 
* 备注：
*
*/
public class TransitionPageActivity extends Activity{
	private ViewPager mViewPager;
	private ViewGroup mViewPoints;
	/** 创建一个imageview类型的数组，用来表示导航小圆点 */
	private ImageView[] imageViews;
	/** 每一个小圆点是一个ImageView */
	private ImageView mImageView;
	private List<View> viewList;
	private LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.transitionpage);
		inflater = LayoutInflater.from(this);
		
		initViewPager();
		initViewPoint();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.getInstance().i("comin");
	}
	
	/**
	 *描述：初始化ViewPager
	 *创建人：lipeng
	 *创建时间：2013-10-12 上午10:09:01
	 *备注：
	 */
	private void initViewPager(){
		mViewPager = (ViewPager) findViewById(R.id.guidePagers);
		viewList = new ArrayList<View>();
		viewList.add(inflater.inflate(R.layout.viewpager01, null));
		viewList.add(inflater.inflate(R.layout.viewpager02, null));
		viewList.add(inflater.inflate(R.layout.viewpager03, null));
		viewList.add(inflater.inflate(R.layout.viewpager04, null));
		viewList.add(inflater.inflate(R.layout.viewpager05, null));
		viewList.add(inflater.inflate(R.layout.viewpager06, null));
		mViewPager.setAdapter(new TransPageAdapter(viewList));
		mViewPager.setOnPageChangeListener(new TransOnPageChangeListener());
	}
	
	/**
	 *描述：初始化ViewPoint
	 *创建人：lipeng
	 *创建时间：2013-10-12 上午10:09:18
	 *备注：
	 */
	private void initViewPoint(){
		mViewPoints = (ViewGroup) findViewById(R.id.viewPoints);
		imageViews = new ImageView[viewList.size()];
		//添加小图片到导航中
		for(int i = 0; i < imageViews.length; i++){
			mImageView = new ImageView(this);
			mImageView.setPadding(5, 0, 5, 0);
			mImageView.setLayoutParams(new LayoutParams(20, 20));
			if(i == 0){
				//默认选中第一张图片
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
			}else{
				mImageView.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
			}
			imageViews[i] = mImageView;
			mViewPoints.addView(mImageView);
		}
	}
	
	/**
	 *描述：点击开始按钮，进入主页
	 *@param v
	 *创建人：lipeng
	 *创建时间：2013-10-12 上午11:41:47
	 *备注：
	 */
	public void startbutton(View v) {
		Intent intent = new Intent(TransitionPageActivity.this, MainActivity.class);
		startActivity(intent);
		
		SharedPreferences preferences = getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(Constant.FIRST_IN, false);
		editor.commit();
		TransitionPageActivity.this.finish();
	}
	
	class TransOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int position1, float arg1, int position2) {
		}

		@Override
		public void onPageSelected(int position) {
			for(int i = 0; i < imageViews.length; i++){
				if(i == position){
					imageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				}else{
					imageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				}
			}
		}
	}
}
