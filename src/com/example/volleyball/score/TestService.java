package com.example.volleyball.score;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.volleyball.score.GlobalConstant;

public class TestService extends Service {

	private String msg = GlobalConstant.msg;
	
	private List<String> jackingList;
	
	private Timer mTimer = new Timer();//需要手动释放！！！onDestory()
	private TimerTask mTask = new TimerTask() {
		
		@Override
		public void run() {
			// 
			Log.d(msg, "TestService timerTask run() event");
			
			ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();//正在运行的进程列表
			for (RunningAppProcessInfo psInfo:infos) {
				if (psInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {//前台进程
					if (getJackingList().contains(psInfo.processName)) {
						System.out.println("劫持进程：" + psInfo.processName);
						Intent intent = new Intent(TestService.this, HomePageActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//从Context打开activity，需要设置flag标志。
						startActivity(intent);
					}
				}
			}
		}
	};
	
	/** indicates how to behave if the service is killed */
	int mStartMode;
	/** interface for clients that bind */
	IBinder mBinder;
	/** indicates whether onRebind should be used */
	boolean mAllowRebind;
	
	/** Called when the service is being created. */
	@Override
	public void onCreate() {
		Log.d(msg, "TestService onCreate() event");
		
		List<String> jackingList = new ArrayList<String>();
		jackingList.add("com.android.music");
		jackingList.add("com.android.browser");
		jackingList.add("com.creditwealth.client");
		this.setJackingList(jackingList);
	}
	
	/** The service is starting, due to a call to startService() */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(msg, "TestService onStartCommand() event");
		this.getmTimer().scheduleAtFixedRate(this.getmTask(), 2000, 1500);
		return mStartMode;
	};
	
	/** A client is binding to the service with bindService() */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(msg, "TestService onBind() event");
		return mBinder;
	}

	/** Called when all clients have unbound with unbindService() */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(msg, "TestService onUnbind() event");
		return mAllowRebind;
	}
	
	/** Called when a client is binding to the service with bindService()*/
	@Override
	public void onRebind(Intent intent) {
		Log.d(msg, "TestService onRebind() event");
		super.onRebind(intent);
	}
	
	/** Called when The service is no longer used and is being destroyed */
	@Override
	public void onDestroy() {
		Log.d(msg, "TestService onDestroy() event");
		this.getmTimer().cancel();
		super.onDestroy();
	}

	public List<String> getJackingList() {
		return jackingList;
	}

	public void setJackingList(List<String> jackingList) {
		this.jackingList = jackingList;
	}

	public Timer getmTimer() {
		return mTimer;
	}

	public void setmTimer(Timer mTimer) {
		this.mTimer = mTimer;
	}

	public TimerTask getmTask() {
		return mTask;
	}

	public void setmTask(TimerTask mTask) {
		this.mTask = mTask;
	}
}
