package com.godream.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.godream.util.LogUtil;

public class MyIntentService extends IntentService{
	private Handler handler;
	public MyIntentService() {
		super("MyIntentService");
	} 

	@Override
	protected void onHandleIntent(Intent intent) {
		LogUtil.getInstance().i(Thread.currentThread().getId());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.getInstance().i("onDestroy");
	}

}
