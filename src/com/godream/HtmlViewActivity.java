package com.godream;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.godream.adapter.HtmlViewPagerAdapter;

public class HtmlViewActivity extends SuperActivity implements OnPageChangeListener,OnClickListener{ 
	private ViewPager mHtmlViewPager;
	private RelativeLayout topicBackButtonLayout;
	private ImageView topicBackButton;
	private TextView topicTitleText;
	private RelativeLayout topicShareButtonLayout;
	private ImageView topicShareButton;
	private List<View> viewList;
	private List<String> urlList;
	private List<String> titleList;
	private LayoutInflater inflater;
	
	private String url1 = "http://m.mafengwo.cn/nb/catalog/94/1.html"; 
	private int curPage = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		topicBackButtonLayout = (RelativeLayout) findViewById(R.id.topicBackButtonLayout);
		topicBackButton = (ImageView) findViewById(R.id.topicBackButton);
		topicTitleText = (TextView) findViewById(R.id.topicTitleText);
		topicShareButtonLayout = (RelativeLayout) findViewById(R.id.topicShareButtonLayout);
		topicShareButton = (ImageView) findViewById(R.id.topicShareButton);
		
		inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.webview_item, null);
		View v2 = inflater.inflate(R.layout.webview_item, null);
		View v3 = inflater.inflate(R.layout.webview_item, null);
		View v4 = inflater.inflate(R.layout.webview_item, null);
		View v5 = inflater.inflate(R.layout.webview_item, null);
		viewList = new ArrayList<View>();
		viewList.add(v1);viewList.add(v2);viewList.add(v3);viewList.add(v4);viewList.add(v5);
		
		urlList = new ArrayList<String>();
		String url1="file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/GoDream/.books/94/html/5.html";
		String url2="file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/GoDream/.books/94/html/6.html";
		String url3="file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/GoDream/.books/94/html/4.html";
		String url4="file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/GoDream/.books/94/html/8.html";
		String url5="file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/GoDream/.books/94/html/7.html";
		urlList.add(url1);urlList.add(url2);urlList.add(url3);urlList.add(url4);urlList.add(url5);
		
		titleList = new ArrayList<String>();
		titleList.add(getString(R.string.item4));
		titleList.add(getString(R.string.item5));
		titleList.add(getString(R.string.item6));
		titleList.add(getString(R.string.item7));
		titleList.add(getString(R.string.item8));
		
		HtmlViewPagerAdapter pagerAdapter = new HtmlViewPagerAdapter(viewList,urlList,this);
		mHtmlViewPager = (ViewPager) findViewById(R.id.htmlViewPager);
		mHtmlViewPager.setAdapter(pagerAdapter);
		
		Intent intent = getIntent();
		if(intent != null){
			curPage = intent.getIntExtra("curPage", 0);
		}
		topicTitleText.setText(titleList.get(curPage));
		mHtmlViewPager.setCurrentItem(curPage);
		mHtmlViewPager.setOnPageChangeListener(this);
	
		topicBackButtonLayout.setOnClickListener(this);
		topicShareButtonLayout.setOnClickListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		curPage = arg0;
		topicTitleText.setText(titleList.get(arg0));
	}

	@Override
	public void onClick(View v) {
		if(v.equals(topicBackButtonLayout)){
			this.finish();
		}else if(v.equals(topicShareButtonLayout)){
	        Intent intent=new Intent(Intent.ACTION_SEND);
	        
	        intent.setType("text/html");
	        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(urlList.get(curPage)));  
	        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
	        intent.putExtra(Intent.EXTRA_TEXT, "正计划一场旅行，自助游攻略的最好选择～（分享自 @旅游手册App家族)");
	        startActivity(Intent.createChooser(intent, getTitle()));
		}
	}
    public List<ResolveInfo> getShareTargets(){
    	List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent=new Intent(Intent.ACTION_SEND,null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm=this.getPackageManager();
        mApps=pm.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
      
        return mApps;
    }
}
















