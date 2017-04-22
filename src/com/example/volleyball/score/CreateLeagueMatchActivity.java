package com.example.volleyball.score;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.volleyball.score.GlobalConstant;
import com.volleyball.match.LeagueMatch;

public class CreateLeagueMatchActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(msg, "CreateLeagueMatchActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_league_match);
		
		final EditText leagueMatchNameEditText = (EditText) findViewById(R.id.leagueMatchName);
		// 设置按钮点击监听事件
		findViewById(R.id.btnDetermine).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				System.out.println("Hit btnDetermine");
				
				final DataApplication app = (DataApplication) getApplication();
				String leagueMatchName = leagueMatchNameEditText.getText().toString();
				if (leagueMatchName.equals("")) {
					Toast.makeText(CreateLeagueMatchActivity.this, "请输入比赛名称！！！", Toast.LENGTH_LONG).show();
					return;
				}
				if (app.isLeagueMatchNameExist(leagueMatchName)) {
					Toast.makeText(CreateLeagueMatchActivity.this, "该比赛名称已存在，请重新输入！！！", Toast.LENGTH_LONG).show();
					return;
				}
				int newId = app.generateUniqueLeagueMatchId();
				if (!app.addLeagueMatch(new LeagueMatch(newId, leagueMatchName))) {
					Toast.makeText(CreateLeagueMatchActivity.this, "创建比赛失败！！！", Toast.LENGTH_LONG).show();
					return;
				}
				// 把新的比赛数据写入xml文件
				if (!app.saveLeagueMatchToXml()) {
					Toast.makeText(CreateLeagueMatchActivity.this, "创建比赛失败！！！", Toast.LENGTH_LONG).show();
					return;
				}
				Toast.makeText(CreateLeagueMatchActivity.this, "创建比赛成功！！！", Toast.LENGTH_LONG).show();
				
				Intent i = new Intent(CreateLeagueMatchActivity.this, HomePageActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "CreateLeagueMatchActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "CreateLeagueMatchActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "CreateLeagueMatchActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "CreateLeagueMatchActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "CreateLeagueMatchActivity onDestroy() event");
	}

}
