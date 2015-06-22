package com.godream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.godream.common.Common;
import com.godream.services.MoListenerService;
import com.godream.util.LogUtil;

public class MainApplication extends Application {

	private static MainApplication mInstance = null;

	public boolean m_bKeyRight = true;

	public BMapManager mBMapManager = null;

	private static WeakReference<MainApplication> instance;

	public static final String strKey = "kTFEyG3xBeQ0Uoyn4HtOvedh";

	private boolean hasStartListener;

	/*
	 * 注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。
	 * 因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请， 申请及配置流程请参考开发指南的对应章节
	 */

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		instance = new WeakReference<MainApplication>(this);
		initEngineManager(this);
		initCommonData();
		Intent i = new Intent(getContext(), MoListenerService.class);
		startService(i);
	}

	public static MainApplication getContext() {
		return (MainApplication) instance.get();
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(MainApplication.getInstance().getApplicationContext(), "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	public static MainApplication getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(MainApplication.getInstance().getApplicationContext(), "您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(MainApplication.getInstance().getApplicationContext(), "输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(MainApplication.getInstance().getApplicationContext(), "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG)
				        .show();
				MainApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

	private void initCommonData() {
		DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
		Common._ScreenWidth = localDisplayMetrics.widthPixels;
		Common._ScreenHeight = localDisplayMetrics.heightPixels;
		Common._Density = localDisplayMetrics.density;
		try {
			PackageInfo localPackageInfo2 = getPackageManager().getPackageInfo(getPackageName(), 0);
			String _AppVerName = localPackageInfo2.versionName;
			int _AppVerCode = localPackageInfo2.versionCode;
			String appDir = localPackageInfo2.applicationInfo.sourceDir;
			String channel = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("UMENG_CHANNEL");
			LogUtil.getInstance().i(_AppVerName + "," + _AppVerCode + "," + appDir + "," + channel + ",");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 将assert目录下的数据库放到sd卡目录下
		try {
			InputStream is = getAssets().open(Common.DB_NAME);
			File fileDir = new File(Common.PATH_CACHE);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			File file = new File(Common.PATH_CACHE, Common.DB_NAME);
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					int len = 0;
					while((len = is.read()) != -1){
						fos.write(len);
					}
					is.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isHasStartListener() {
		return hasStartListener;
	}

	public void setHasStartListener(boolean hasStartListener) {
		this.hasStartListener = hasStartListener;
	}

}
