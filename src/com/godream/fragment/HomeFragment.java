package com.godream.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.godream.B2C_CatActivity;
import com.godream.HtmlViewActivity;
import com.godream.LandscapeListActivity;
import com.godream.MainActivity;
import com.godream.MovieSelectSeatActivity;
import com.godream.NewUserGuiderActivity;
import com.godream.NfcTestActivity;
import com.godream.R;
import com.godream.RouteMapActivity;
import com.godream.adapter.HomeGridViewAdapter;
import com.godream.adapter.HomeViewPagerAdapter;
import com.godream.bean.HomeGridItem;
import com.godream.defineview.HomeViewPager;
import com.godream.util.BaseView;
import com.godream.util.LogUtil;

public class HomeFragment extends Fragment implements OnClickListener{
	private GridView mHomeGridView;
	private ArrayList<HomeGridItem> gridItemList;
	private MainActivity activity;
	private HomeViewPager mHomeViewPager;
	private LayoutInflater inflater;
	private List<View> homePagerViewList;
	
	private ViewGroup mViewPoints;
	/** 创建一个imageview类型的数组，用来表示导航小圆点 */
	private ImageView[] imageViews;
	/** 每一个小圆点是一个ImageView */
	private ImageView mImageView;
	
	//ViewPager的图片
	ImageView mImage1;
	ImageView mImage2;
	ImageView mImage3;
	ImageView mImage4;
	
	public BaseView baseView;
	
	/**标题栏控件*/
	private TextView mTopCenterTxt;
	public ImageView mTopLeftImage;
	private RelativeLayout mTop_left_layout;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.getInstance().i("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.getInstance().i("onCreate");
		activity = (MainActivity)getActivity();
		inflater = LayoutInflater.from(activity);
		activity.homeReference = new WeakReference<HomeFragment>(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.getInstance().i("onCreateView");
		baseView = new BaseView();
		baseView.setContentView(activity,R.layout.base_view_titleleft_image,R.layout.base_view_titlecenter, 
				0,R.layout.homefragment);
		View v = baseView.getContentView();
		
		mHomeGridView = (GridView) v.findViewById(R.id.home_gridview);
		mHomeViewPager = (HomeViewPager) v.findViewById(R.id.home_viewpager);
		mTopCenterTxt = (TextView) v.findViewById(R.id.top_title);
		mTopCenterTxt.setText(getString(R.string.hometitle));
		mTopLeftImage = (ImageView) v.findViewById(R.id.top_left);
		mTopLeftImage.setBackgroundResource(R.drawable.open_menu);
		mTop_left_layout = (RelativeLayout) v.findViewById(R.id.top_left_layout);
		mTop_left_layout.setOnClickListener(this);
		
		initViewPgaer();
		initGridView();
		initViewPoint(v);
		return v;
	}
	
	private void initViewPgaer(){
		homePagerViewList = new ArrayList<View>();
		View v1 = inflater.inflate(R.layout.homeviewpager_item, null);
		mImage1 = (ImageView) v1.findViewById(R.id.home_imageview);
		mImage1.setBackgroundResource(R.drawable.homepager1);
		mImage1.setOnClickListener(this);
		View v2 = inflater.inflate(R.layout.homeviewpager_item, null);
		mImage2 = (ImageView) v2.findViewById(R.id.home_imageview);
		mImage2.setBackgroundResource(R.drawable.homepager2);
		mImage2.setOnClickListener(this);
		View v3 = inflater.inflate(R.layout.homeviewpager_item, null);
		mImage3 = (ImageView) v3.findViewById(R.id.home_imageview);
		mImage3.setBackgroundResource(R.drawable.homepager3);
		mImage3.setOnClickListener(this);
		View v4 = inflater.inflate(R.layout.homeviewpager_item, null);
		mImage4 = (ImageView) v4.findViewById(R.id.home_imageview);
		mImage4.setBackgroundResource(R.drawable.homepager4);
		mImage4.setOnClickListener(this);
		homePagerViewList.add(v1);
		homePagerViewList.add(v2);
		homePagerViewList.add(v3);
		homePagerViewList.add(v4);
		mHomeViewPager.setAdapter(new HomeViewPagerAdapter(homePagerViewList));
		mHomeViewPager.setOnPageChangeListener(new HomeViewPagerChangeListent());
	}
	
	private void initGridView(){
		HomeGridItem item1 = new HomeGridItem(1,getString(R.string.item1), R.drawable.item1);
		HomeGridItem item2 = new HomeGridItem(2,getString(R.string.item2), R.drawable.item2);
		HomeGridItem item3 = new HomeGridItem(3,getString(R.string.item3), R.drawable.item3);
		HomeGridItem item4 = new HomeGridItem(4,getString(R.string.item4), R.drawable.item4);
		HomeGridItem item5 = new HomeGridItem(5,getString(R.string.item5), R.drawable.item5);
		HomeGridItem item6 = new HomeGridItem(6,getString(R.string.item6), R.drawable.item6);
		HomeGridItem item7 = new HomeGridItem(7,getString(R.string.item7), R.drawable.item7);
		HomeGridItem item8 = new HomeGridItem(8,getString(R.string.item8), R.drawable.item8);
		HomeGridItem item9 = new HomeGridItem(9,getString(R.string.item9), R.drawable.item9);
		gridItemList = new ArrayList<HomeGridItem>();
		gridItemList.add(item1);gridItemList.add(item2);gridItemList.add(item3);gridItemList.add(item4);
		gridItemList.add(item5);gridItemList.add(item6);gridItemList.add(item7);gridItemList.add(item8);gridItemList.add(item9);
		
		mHomeGridView.setAdapter(new HomeGridViewAdapter(activity,gridItemList));
		mHomeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HomeGridItem item = gridItemList.get(arg2);
				switch (item.getId()) {
				case 1:
					//导航
					Intent i1 = new Intent(activity,NewUserGuiderActivity.class);
					activity.startActivity(i1);
					break;
				case 2:
					//线路
					Intent i2 = new Intent(activity,RouteMapActivity.class);
					activity.startActivity(i2);
					break;
				case 3:
					//图片
					Intent i3 = new Intent(activity,LandscapeListActivity.class);
					activity.startActivity(i3);
					break;
				case 4:
					//影院
					Intent i4 = new Intent(activity,MovieSelectSeatActivity.class);
//					i4.putExtra("curPage", 0);
					activity.startActivity(i4);
					break;
				case 5:
					//联系人
					Intent i5 = new Intent("android.intent.action.SEARCH",Uri.parse("content://com.godream.provider.Contacts/contacts"));
					activity.startActivity(i5);
					break;
				case 6:
					Intent i6 = new Intent(activity,NfcTestActivity.class);
//					i6.putExtra("curPage", 2);
					activity.startActivity(i6);
					break;
				case 7:
					Intent i7 = new Intent(activity,HtmlViewActivity.class);
					i7.putExtra("curPage", 3);
					activity.startActivity(i7);
					break;
				case 8:
					Intent i8 = new Intent(activity,HtmlViewActivity.class);
					i8.putExtra("curPage", 4);
					activity.startActivity(i8);
//					Intent i8 = new Intent(activity,MyIntentService.class);
//					activity.startService(i8);
//					Intent i9 = new Intent(activity,MyIntentService.class);
//					activity.startService(i);
					break;
//				case 5:
//					Intent i5 = new Intent(activity,TrivalNoteList.class);
//					activity.startActivity(i5);
//					break;
				case 9:
					Intent i9 = new Intent(activity,B2C_CatActivity.class);
					activity.startActivity(i9);
				default:
					break;
				}
//				Toast.makeText(activity, item.getName(), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	private void initViewPoint(View v){
		mViewPoints = (ViewGroup) v.findViewById(R.id.viewPoints);
		imageViews = new ImageView[homePagerViewList.size()];
		//添加小图片到导航中
		for(int i = 0; i < imageViews.length; i++){
			mImageView = new ImageView(activity);
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
	
	public class HomeViewPagerChangeListent implements OnPageChangeListener{
		public void onPageScrollStateChanged(int arg0) {
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.getInstance().i("onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.getInstance().i("onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		mHomeGridView.requestFocus();
		LogUtil.getInstance().i("onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.getInstance().i("onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.getInstance().i("onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.getInstance().i("onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.getInstance().i("onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.getInstance().i("onDetach");
	}

	@Override
	public void onClick(View v) {
		if(v.equals(mImage1)){
			Toast.makeText(activity, "点击了第一张图", Toast.LENGTH_SHORT).show();
		}else if(v.equals(mImage2)){
			Toast.makeText(activity, "点击了第二张图", Toast.LENGTH_SHORT).show();
		}else if(v.equals(mImage3)){
			Toast.makeText(activity, "点击了第三张图", Toast.LENGTH_SHORT).show();
		}else if(v.equals(mImage4)){
			Toast.makeText(activity, "点击了第四张图", Toast.LENGTH_SHORT).show();
		}else if(v.equals(mTop_left_layout)){
			mTopLeftImage.setBackgroundResource(R.drawable.close_menu);
			activity.slidMenu.showMenu();
		}
	}
}
