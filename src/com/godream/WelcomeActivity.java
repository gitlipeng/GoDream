package com.godream;

import com.godream.common.Constant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
*
* 类名称：WelcomeActivity
* 
* 类描述：欢迎页面
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-12 上午9:32:35
* 
* 备注：
*
*/
public class WelcomeActivity extends Activity{
	private boolean isFirstIn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		SharedPreferences preferences = getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,MODE_PRIVATE);
		isFirstIn = preferences.getBoolean(Constant.FIRST_IN, true);
//		goHome();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(isFirstIn){
					goTransPagerActivity();
				}else{
					goHome();
				}
			}
		}, 0);
	}
	
	private void goTransPagerActivity(){
		Intent i = new Intent(WelcomeActivity.this,TransitionPageActivity.class);
		startActivity(i);
		WelcomeActivity.this.finish();
	}
	
	private void goHome(){
		Intent i = new Intent(WelcomeActivity.this,MainActivity.class);
		startActivity(i);
		WelcomeActivity.this.finish();
	}
	
}
