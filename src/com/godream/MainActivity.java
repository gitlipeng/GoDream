package com.godream;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.godream.adapter.FragmentViewPagerAdapter;
import com.godream.adapter.MenuListAdapter;
import com.godream.bean.MenuBean;
import com.godream.defineview.MainViewPager;
import com.godream.fragment.GuidFragment;
import com.godream.fragment.HomeFragment;
import com.godream.fragment.MoreFragment;
import com.godream.fragment.SlideFragment;
import com.godream.fragment.TrivalNoteFragment;
import com.godream.listener.ViewPagerSelectListener;
import com.godream.service.AsyncImageLoader;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements ViewPagerSelectListener {

	private MainViewPager viewPager;

	public List<Fragment> fragments = new ArrayList<Fragment>();

	private TabHost mTabHost;

	private ImageView cursor;

	private int offset = 0;

	private int currentIndex = 0;

	private int bmpW;

	private ImageView[] views;

	private int currentTabID = 0;

	private LocalActivityManager manager = null;

	private TabWidget mTabWidget;

	public SlidingMenu slidMenu;

	public static WeakReference<HomeFragment> homeReference;

	public LayoutInflater inflater;

	private ListView mMenuListView;

	private Fragment slideFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);

		initSlidMenu();
		initViewPager();
		initTabhost();
		initImageView();
		initBottomMenu();
	}

	private void initSlidMenu() {
		View menuView = inflater.inflate(R.layout.menu_frame, null); 
		setBehindContentView(menuView); // 设置菜单页
		FragmentTransaction ft=this.getSupportFragmentManager().beginTransaction();  
		slideFragment=new SlideFragment();  
        ft.replace(R.id.menu_frame, slideFragment);
        ft.commit();  
		slidMenu = getSlidingMenu();	//滑动菜单 
		slidMenu.setShadowWidth(15); // 阴影宽度
		slidMenu.setBehindOffset(300); // 菜单与边框的距离,设置菜单占屏幕的比例 
		slidMenu.setShadowDrawable(R.drawable.shadow); // 滑动菜单渐变
		slidMenu.setFadeDegree(0.5f);				//色度
		slidMenu.setMode(SlidingMenu.LEFT);
		slidMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //要使菜单滑动，触碰屏幕的范围 , 边缘滑动菜单
		slidMenu.setFadeEnabled(true);//设置滑动时菜单的是否淡入淡出
		slidMenu.setFadeDegree(0.5f);//设置淡入淡出的比例 
		slidMenu.setBehindScrollScale(0.5f);//设置滑动时拖拽效果 
		slidMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
			@Override
			public void onOpen() {
				homeReference.get().mTopLeftImage.setBackgroundResource(R.drawable.close_menu);
			}
		});
		slidMenu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
			@Override
			public void onClose() {
				homeReference.get().mTopLeftImage.setBackgroundResource(R.drawable.open_menu);
			}
		});
//		slidMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //要使菜单滑动，触碰屏幕的范围 , 边缘滑动菜单
//		sm.setFadeEnabled(true);//设置滑动时菜单的是否淡入淡出
//		sm.setFadeDegree(0.4f);//设置淡入淡出的比例 
//		sm.setBehindScrollScale(0);//设置滑动时拖拽效果 
//		getActionBar().setDisplayHomeAsUpEnabled(true);	

	}

	private void initViewPager() {
		fragments.add(new HomeFragment());
		fragments.add(new GuidFragment());
		fragments.add(new TrivalNoteFragment());
		fragments.add(new MoreFragment());

		viewPager = (MainViewPager) findViewById(R.id.viewPager);
		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), viewPager, fragments);
		adapter.setListener(this);
	}

	private void initTabhost() {
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup();
		mTabHost.setup(manager);

		setIndicator("主页", 0, null, R.drawable.index_home_light, R.drawable.bottom_light);
		setIndicator("导航", 1, null, R.drawable.index_tools, R.drawable.bottom_black);
		setIndicator("游记", 2, null, R.drawable.index_near_by, R.drawable.bottom_black);
		setIndicator("更多", 3, null, R.drawable.index_more, R.drawable.bottom_black);
	}

	private void setIndicator(String ss, int tabId, Intent intent, int image_id, int layoutBg) {
		View localView = LayoutInflater.from(this.mTabHost.getContext()).inflate(R.layout.tab_widget_contact_view, null);
		((LinearLayout) localView.findViewById(R.id.tabsLayout)).setBackgroundResource(layoutBg);
		((ImageView) localView.findViewById(R.id.main_activity_tab_image)).setImageResource(image_id);
		((TextView) localView.findViewById(R.id.main_activity_tab_text)).setText(ss);
		String str = String.valueOf(tabId);
		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(str).setIndicator(localView).setContent(new Intent(this, TransitionPageActivity.class));
		mTabHost.addTab(localTabSpec);
	}

	private void initBottomMenu() {
		int viewCount = mTabWidget.getChildCount();
		views = new ImageView[viewCount];
		for (int i = 0; i < views.length; i++) {
			View v = (LinearLayout) mTabWidget.getChildAt(i);
			views[i] = (ImageView) v.findViewById(R.id.main_activity_tab_image);
		}
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			ImageView image = null;

			public void onTabChanged(String tabId) {
				int tabID = Integer.valueOf(tabId);
				views[currentIndex].setImageResource(getImageId(currentIndex, false));
				views[tabID].setImageResource(getImageId(tabID, true));
				mTabWidget.getChildAt(currentIndex).setBackgroundResource(R.drawable.bottom_black);
				mTabWidget.getChildAt(tabID).setBackgroundResource(R.drawable.bottom_light);
				onPageSelect(tabID);
				viewPager.setCurrentItem(currentIndex);
			}
		});
	}

	private int getImageId(int index, boolean isSelect) {
		int result = -1;
		switch (index) {
			case 0:
				result = isSelect ? R.drawable.index_home_light : R.drawable.index_home;
				break;
			case 1:
				result = isSelect ? R.drawable.index_tools_highlight : R.drawable.index_tools;
				break;
			case 2:
				result = isSelect ? R.drawable.index_near_by_highlight : R.drawable.index_near_by;
				break;
			case 3:
				result = isSelect ? R.drawable.index_more_highlight : R.drawable.index_more;
				break;
		}
		return result;
	}

	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_tab_anim_light).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 4 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	public void onPageSelect(int arg0) {
		int one = offset * 2 + bmpW;
		Animation animation = null;
		animation = new TranslateAnimation(one * currentIndex, one * arg0, 0, 0);
		currentIndex = arg0;
		animation.setFillAfter(true);
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int position, float arg1, int position2) {

	}

	@Override
	public void onPageSelected(int position) {
		mTabHost.setCurrentTab(position);
		if (position == 0) {
			slidMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		} else {
			slidMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		setStartTransition();
	}

	private void setStartTransition() {
		overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
	}
}