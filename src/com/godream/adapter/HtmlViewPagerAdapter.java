package com.godream.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.godream.R;
import com.godream.util.LogUtil;

/**
 * 
 * 类名称：HtmlViewPagerAdapter
 * 
 * 类描述：
 * 
 * 创建人：lipeng
 * 
 * 创建时间：2013-11-4 下午2:58:38
 * 
 * 备注：
 * 
 */
public class HtmlViewPagerAdapter extends PagerAdapter {
	private List<View> viewList;
	private List<String> urlList;
	private Context context;

	public HtmlViewPagerAdapter(List<View> viewList, List<String> urlList,
			Context context) {
		this.viewList = viewList;
		this.urlList = urlList;
		this.context = context;
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
	public Object instantiateItem(ViewGroup paramViewGroup, int position) {
		int i = position % this.viewList.size();
		View localView = (View) this.viewList.get(i);
		WebView localHtmlView = (WebView) localView.findViewById(R.id.htmlView);
		if (paramViewGroup.indexOfChild(localView) >= 0) {
			paramViewGroup.removeView(localView);
			localHtmlView.loadUrl("");
		}
		localView.setTag(Integer.valueOf(position));
		localHtmlView.loadUrl(urlList.get(position));
		localHtmlView.getSettings().setJavaScriptEnabled(true);
		localHtmlView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				LogUtil.getInstance().i(url);
				if (url.startsWith("mfwsrb://")) {
					try {
						Uri localUri = Uri.parse(url.replace("mfwsrb://",
								"http://"));
						if (localUri.getHost().equals("tel")) {
							String str1 = "";
							if (!TextUtils.isEmpty(localUri
									.getQueryParameter("c_code")))
								str1 = str1 + "+"
										+ localUri.getQueryParameter("c_code");
							if (!TextUtils.isEmpty(localUri
									.getQueryParameter("a_code")))
								str1 = str1
										+ localUri.getQueryParameter("a_code");
							if (!TextUtils.isEmpty(localUri
									.getQueryParameter("num")))
								str1 = str1 + localUri.getQueryParameter("num");
							final String str2 = str1;
							String str3 = localUri.getQueryParameter("ext");
							if (!TextUtils.isEmpty(str3)) {
								AlertDialog.Builder localBuilder = new AlertDialog.Builder(
										context)
										.setMessage("拨通后请拨打分机号：" + str3);
								localBuilder.setPositiveButton("拨打",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface paramDialogInterface,
													int paramInt) {
												if (!TextUtils.isEmpty(str2)) {
													Intent localIntent = new Intent(
															"android.intent.action.DIAL",
															Uri.parse("tel:"
																	+ str2));
													context.startActivity(localIntent);
												}
											}
										}).setNegativeButton("取消", null);
							}
							if (!TextUtils.isEmpty(str2)) {
								Intent localIntent2 = new Intent(
										"android.intent.action.DIAL", Uri
												.parse("tel:" + str2));
								context.startActivity(localIntent2);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				return true;
			}

		});
		paramViewGroup.addView(localView, 0);

		return localView;
	}

	@Override
	public void destroyItem(ViewGroup paramViewGroup, int paramInt,
			Object paramObject) {
		int i = paramInt % this.viewList.size();
		View localView = (View) this.viewList.get(i);
		WebView localHtmlWebView = (WebView) localView
				.findViewById(R.id.htmlView);
		if (((Integer) localView.getTag()).intValue() == paramInt) {
			paramViewGroup.removeView(localView);
			localHtmlWebView.loadUrl("");
		}
	}

}
