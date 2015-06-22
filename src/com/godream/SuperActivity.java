package com.godream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.godream.util.BaseView;
import com.godream.util.LogUtil;

public abstract class SuperActivity extends Activity {
	public static final String TAG = "SuperActivity";
	public BaseView baseView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.getInstance().i("onCreate");
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏
		baseView = new BaseView();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		setStartTransition();
	}
	
	private void setStartTransition() {
		overridePendingTransition(R.anim.activity_right_in,
				R.anim.activity_left_out);
	}

	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.activity_left_in,
				R.anim.activity_right_out);
	}

}
