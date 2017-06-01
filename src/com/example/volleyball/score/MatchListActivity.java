package com.example.volleyball.score;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.volleyball.file.DomLeagueMatchParser;
import com.volleyball.file.GetPathFromUri4kitkat;
import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Team;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MatchListActivity extends Activity {

	private String msg = GlobalConstant.msg;

	private ListView matchList;
	
	public MatchListActivity() {
		// 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "MatchListActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_list);
		
		this.setMatchList((ListView) findViewById(R.id.matchListView));
		this.updateData();
		// 设置点击比赛记录列表显示事件
		this.getMatchList().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 
				System.out.println("Hit matchListView, parent:" + parent.toString() + " view:" + view.toString() + " position:" + position + " id:" + id);
				String stageMatchName = (String) parent.getItemAtPosition(position);
				DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				int index = leagueMatch.getIndexOfMatchByStageName(stageMatchName);
				if (index == -1) {
					return;
				}
				leagueMatch.setIndexOfMatch(index);
				app.setNowLeagueMatch(leagueMatch);
				// 跳转到创建队伍activity
				Intent i = new Intent(MatchListActivity.this, ShowMatchResultActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("name", stageMatchName);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		
		// 设置长按删除功能
		this.getMatchList().setLongClickable(true);
		this.getMatchList().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// 
				String stageMatchName = (String) parent.getItemAtPosition(position);
				final DataApplication app = (DataApplication) getApplication();
				final LeagueMatch leagueMatch = app.getNowLeagueMatch();
				final Match match = leagueMatch.getMatchByStageName(stageMatchName);
				if (match == null) {
					System.out.println("Can not find stageMatchName:" + stageMatchName);
					return true;
				}
				new AlertDialog.Builder(view.getContext()).setTitle("删除比赛记录")
				.setMessage("确认删除：" + stageMatchName + " ?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 
						leagueMatch.delMatch(match);
						app.setNowLeagueMatch(leagueMatch);
						app.saveLeagueMatchToXml();
						updateData();
					}
				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 						
					}
				}).show();
				return true;
			}
		});
	}
	
	private List<String> getMatchData() {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		List<String> data = new ArrayList<String>();
		if (leagueMatch.getMatches() != null) {
			for (Match match:leagueMatch.getMatches()) {
				data.add(match.getStageName());
			}
		}
		return data;
	}

	public void updateData() {
		// 
		this.getMatchList().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getMatchData()));
	}

	public void importMatchRecords(View view) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(Intent.createChooser(intent, "请选择文件："), GlobalConstant.FILE_SELECT_CODE);
	}
	
	public void exportMatchRecords(View view) {
		// 
		Intent i = new Intent(MatchListActivity.this, ExportMatchActivity.class);
		startActivity(i);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 导入xml文件，比赛名称必须相同，导入队伍和比赛记录，忽略队伍名称相同或者比赛记录阶段名称相同的数据。
		Log.d(msg, "MatchListActivity onActivityResult() event");
		if (requestCode == GlobalConstant.FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			System.out.println("uri:" + uri.toString());
			String absoluteFileName = GetPathFromUri4kitkat.getPath(this, uri);
			System.out.println("absoluteFileName:" + absoluteFileName);
			try {
				FileInputStream fis = new FileInputStream(absoluteFileName);
				DomLeagueMatchParser parser = new DomLeagueMatchParser();
				List<LeagueMatch> importLeagueMatches = parser.parse(fis);
				LeagueMatch importLeagueMatch = importLeagueMatches.get(0);
				DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				if (!importLeagueMatch.getName().equals(leagueMatch.getName())) {
					Toast.makeText(MatchListActivity.this, "比赛名称不同，导入队伍失败！！！", Toast.LENGTH_LONG).show();
					
				}
				else {
					for (Team importTeam:importLeagueMatch.getTeams()) {
						if (leagueMatch.isTeamNameExist(importTeam.getName())) {
							continue;
						}
						importTeam.setId(leagueMatch.generateUniqueTeamId());
						leagueMatch.addTeam(importTeam);
					}
					for (Match importMatch:importLeagueMatch.getMatches()) {
						if (leagueMatch.isMatchStageNameExist(importMatch.getStageName())) {
							continue;
						}
						importMatch.setId(leagueMatch.generateUniqueMatchId());
						leagueMatch.addMatch(importMatch);
					}
					app.setNowLeagueMatch(leagueMatch);
					app.saveLeagueMatchToXml();
					Toast.makeText(MatchListActivity.this, "导入队伍数据成功！！！", Toast.LENGTH_LONG).show();
					this.updateData();
				}
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
		Log.d(msg, "MatchListActivity onNewIntent() event");
		super.onNewIntent(intent);
		setIntent(intent);
		this.updateData();
	}
	
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "MatchListActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "MatchListActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "MatchListActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "MatchListActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "MatchListActivity onDestroy() event");
	}

	public ListView getMatchList() {
		return matchList;
	}

	public void setMatchList(ListView matchList) {
		this.matchList = matchList;
	}
}
