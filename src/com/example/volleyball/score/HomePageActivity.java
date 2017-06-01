package com.example.volleyball.score;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.example.volleyball.score.CreateLeagueMatchActivity;
import com.example.volleyball.score.DataApplication;
import com.example.volleyball.score.GlobalConstant;
import com.example.volleyball.score.TestService;
import com.volleyball.file.DomLeagueMatchParser;
import com.volleyball.file.GetPathFromUri4kitkat;
import com.volleyball.match.LeagueMatch;

public class HomePageActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private ListView leagueMatchList;
	private ArrayAdapter<String> leagueMatchListAdapter;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		Log.d(msg, "HomePageActivity onCreate() event");
		// 创建列表
		this.setLeagueMatchList((ListView) this.findViewById(R.id.matchListView));
		this.setLeagueMatchListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getLeagueMatchData()));
		this.getLeagueMatchList().setAdapter(this.getLeagueMatchListAdapter());
		this.getLeagueMatchList().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Hit matchListView, parent:" + parent.toString() + " view:" + view.toString() + " position:" + position + " id:" + id);
				String leagueMatchName = (String) parent.getItemAtPosition(position);
				final DataApplication app = (DataApplication) getApplication();
				int index = app.getIndexOfLeagueMatchByName(leagueMatchName);
				if (index == -1) {
					return;
				}
				app.setIndexOfLeagueMatch(index);
				// 跳转到比赛首页activity
				Intent i = new Intent(HomePageActivity.this, LeagueMatchHomePageActivity.class);
				startActivity(i);
			}
		});
		// 设置长按删除功能
		this.getLeagueMatchList().setLongClickable(true);
		this.getLeagueMatchList().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Long hit matchListView, parent:" + parent.toString() + " view:" + view.toString() + " position:" + position + " id:" + id);
				
				String leagueMatchName = (String) parent.getItemAtPosition(position);
				final DataApplication app = (DataApplication) getApplication();
				final LeagueMatch leagueMatch = app.getLeagueMatchByName(leagueMatchName);
				if (leagueMatch == null) {
					System.out.println("Can not find leagueMatchName:" + leagueMatchName);
					return true;
				}
				new AlertDialog.Builder(view.getContext()).setTitle("删除比赛")
				.setMessage("确认删除：" + leagueMatchName + " ?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						app.delLeagueMatch(leagueMatch);
						app.saveLeagueMatchToXml();
						updateListView();
					}
				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
				
				return true;
			}
		});
		
		// 设置按钮点击监听事件
		findViewById(R.id.btnImportLeagueMatch).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Hit btnImportLeagueMatch");
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(Intent.createChooser(intent, "请选择文件："), GlobalConstant.FILE_SELECT_CODE);
				
			}
		});
		findViewById(R.id.btnExportLeagueMatch).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Hit btnExportLeagueMatch");
				Intent i = new Intent(HomePageActivity.this, ExportLeagueMatchActivity.class);
				startActivity(i);
			}
		});
		findViewById(R.id.btnCreateLeagueMatch).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Hit btnCreateLeagueMatch");
				Intent i = new Intent(HomePageActivity.this, CreateLeagueMatchActivity.class);
				startActivity(i);
			}
		});
		
	}
	
	private List<String> getLeagueMatchData() {
		// 从DataApplication类中提取已经解析好的数据。
		final DataApplication app = (DataApplication) getApplication();
		List<LeagueMatch> leagueMatches = app.getLeagueMatches();
		List<String> data = new ArrayList<String>();
		for (LeagueMatch leagueMatch:leagueMatches) {
			data.add(leagueMatch.getName());
		}
		return data;
	}
	public void updateListView() {
		this.getLeagueMatchList().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getLeagueMatchData()));
	}

	public ListView getLeagueMatchList() {
		return leagueMatchList;
	}

	public void setLeagueMatchList(ListView leagueMatchList) {
		this.leagueMatchList = leagueMatchList;
	}

	public ArrayAdapter<String> getLeagueMatchListAdapter() {
		return leagueMatchListAdapter;
	}

	public void setLeagueMatchListAdapter(ArrayAdapter<String> leagueMatchListAdapter) {
		this.leagueMatchListAdapter = leagueMatchListAdapter;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 导入xml文件，忽略比赛名称相同的数据。
		Log.d(msg, "HomePageActivity onActivityResult() event");
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			System.out.println("uri:" + uri.toString());
			String absoluteFileName = GetPathFromUri4kitkat.getPath(this, uri);
			System.out.println("absoluteFileName:" + absoluteFileName);
			try {
				FileInputStream fis = new FileInputStream(absoluteFileName);
				DomLeagueMatchParser parser = new DomLeagueMatchParser();
				List<LeagueMatch> importLeagueMatches = parser.parse(fis);
				final DataApplication app = (DataApplication) getApplication();
				List<LeagueMatch> leagueMatches = app.getLeagueMatches();
				for (LeagueMatch importLeagueMatch:importLeagueMatches) {
					if (app.isLeagueMatchNameExist(importLeagueMatch.getName())) {
						continue;
					}
					LeagueMatch newLeagueMatch = new LeagueMatch(app.generateUniqueLeagueMatchId(), importLeagueMatch.getName());
					leagueMatches.add(newLeagueMatch);
				}
				app.saveLeagueMatchToXml();
				Toast.makeText(HomePageActivity.this, "导入比赛数据成功！！！", Toast.LENGTH_LONG).show();
				this.updateListView();
			} catch (FileNotFoundException e) {
				// 
				e.printStackTrace();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/** 当此activity启动方式为singleTask时，用于更新intent。*/
	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(msg, "HomePageActivity onNewIntent() event");
		super.onNewIntent(intent);
		setIntent(intent);
		this.updateListView();
	}
	
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "HomePageActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "HomePageActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "HomePageActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "HomePageActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "HomePageActivity onDestroy() event");
	}
	
	// Method to start the service 
	public void startService(View view) { 
		startService(new Intent(getBaseContext(), TestService.class)); 
	} 
	// Method to stop the service 
	public void stopService(View view) { 
		stopService(new Intent(getBaseContext(), TestService.class)); 
	}
	
	//Method to send the broadcast
	public void broadcastIntent(View view) {
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
		TestBroadcastReceiver testBroadcastReceiver = new TestBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.volleyball.score.CUSTOM_INTENT");
		localBroadcastManager.registerReceiver(testBroadcastReceiver, intentFilter);

		System.out.println("Hit broadcastIntent!!!");
		Intent intent = new Intent(); 
		intent.setAction("com.example.volleyball.score.CUSTOM_INTENT"); 
		boolean result = localBroadcastManager.sendBroadcast(intent);//这种方法发出的广播只能被app自身广播接收器接收
		System.out.println("使用LocalBroadcastManager发送广播结果：" + result);
		localBroadcastManager.unregisterReceiver(testBroadcastReceiver);
//		sendBroadcast(intent);
		try {
//			startActivity(intent);
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
	
}
