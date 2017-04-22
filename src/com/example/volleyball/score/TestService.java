package com.example.volleyball.score;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.volleyball.score.GlobalConstant;

public class TestService extends Service {

	private String msg = GlobalConstant.msg;
	
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
	}
	
	/** The service is starting, due to a call to startService() */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(msg, "TestService onStartCommand() event");
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
		super.onDestroy();
	}
}
