package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeamRankActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private ListView teamRankList;
	
	public TeamRankActivity() {
		// 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "TeamRankActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_rank);
		
		this.setTeamRankList((ListView) findViewById(R.id.teamRankListView));
		this.updateView();
		
		this.getTeamRankList().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 
				System.out.println("Hit teamRankListView, parent:" + parent.toString() + " view:" + view.toString() + " position:" + position + " id:" + id);
				String stage = (String) parent.getItemAtPosition(position);
				
				// 跳转到创建队伍activity
				Intent i = new Intent(TeamRankActivity.this, TeamStageRankActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("stage", stage);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
	}
	
	private void updateView() {
		// 
		this.getTeamRankList().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getMatchData()));
	}

	private List<String> getMatchData() {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		List<String> data = new ArrayList<String>();
		if (leagueMatch.getMatches() != null) {
			for (Match match:leagueMatch.getMatches()) {
				if (!data.contains(match.getStage())) {
					data.add(match.getStage());
				}
			}
		}
		return data;
	}

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "TeamRankActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "TeamRankActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "TeamRankActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "TeamRankActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "TeamRankActivity onDestroy() event");
	}

	public ListView getTeamRankList() {
		return teamRankList;
	}

	public void setTeamRankList(ListView teamRankList) {
		this.teamRankList = teamRankList;
	}

}
