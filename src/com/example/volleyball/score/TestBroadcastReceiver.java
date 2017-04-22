package com.example.volleyball.score;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.volleyball.score.GlobalConstant;
import com.ndk.test.JniClient;

public class TestBroadcastReceiver extends BroadcastReceiver {
	static {
		System.loadLibrary("volleyball.score");
	}

	private String msg = GlobalConstant.msg;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(msg, "TestBroadcastReceiver onReceive()");
		
		String str = JniClient.AddStr("string1 ", " string2");
		System.out.println("Call native AddStr result:" + str);
	}

}
