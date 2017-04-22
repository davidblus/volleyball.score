package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Set;
import com.volleyball.match.Team;
import com.volleyball.match.TeamMember;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FillOutPreSetActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private RadioGroup radioGroupFirstTeam;
	private TextView teamAPositionTitle;
	private List<EditText> teamAPosition;
	private TextView teamBPositionTitle;
	private List<EditText> teamBPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "FillOutPreSetActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_out_pre_set);
		
		this.setRadioGroupFirstTeam((RadioGroup) findViewById(R.id.radioGroupFirstTeam));
		this.setTeamAPositionTitle((TextView) findViewById(R.id.textTeamAPosition));
		List<EditText> teamAPosition = new ArrayList<EditText>();
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition1));
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition2));
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition3));
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition4));
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition5));
		teamAPosition.add((EditText) findViewById(R.id.TeamAPosition6));
		this.setTeamAPosition(teamAPosition);
		this.setTeamBPositionTitle((TextView) findViewById(R.id.textTeamBPosition));
		List<EditText> teamBPosition = new ArrayList<EditText>();
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition1));
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition2));
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition3));
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition4));
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition5));
		teamBPosition.add((EditText) findViewById(R.id.TeamBPosition6));
		this.setTeamBPosition(teamBPosition);
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		int setNum = set.getNum();
		this.getTeamAPositionTitle().setText("A队第" + setNum + "局位置：");
		this.getTeamBPositionTitle().setText("B队第" + setNum + "局位置：");
	}
	
	public void nextStep(View view) {
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Team teamA = match.getTeamA();
		Team teamB = match.getTeamB();
		System.out.println("teamA.normal.size():" + teamA.getNormalTeamMembers().size() + " teamB.normal.size():" + teamB.getNormalTeamMembers().size());
		Set set = match.getNowSet();
		
		RadioButton selectedTeam = (RadioButton) findViewById(this.getRadioGroupFirstTeam().getCheckedRadioButtonId());
		String firstTeamStr = (String) selectedTeam.getText();
		if(firstTeamStr.equals("A")) {
			set.setFirstTeam(teamA);
		}
		else if(firstTeamStr.equals("B")) {
			set.setFirstTeam(teamB);
		}
		else {
			System.out.println("FillOutPreSetActivity nextStep() 未知错误！！！");
		}
		List<TeamMember> teamAInitialPosition = new ArrayList<TeamMember>();
		teamAInitialPosition.add(null);
		for(int i = 0; i < this.getTeamAPosition().size(); ++i) {
			int number = -1;
			try {
				number = Integer.parseInt(this.getTeamAPosition().get(i).getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				Toast.makeText(FillOutPreSetActivity.this, "A队未填写完成!!!", Toast.LENGTH_LONG).show();
				return;
			}
			TeamMember teamAMember = teamA.getTeamMemberByNum(number);
			if(teamAMember == null) {
				System.out.println("number:" + number + " teamA.normalSize():" + teamA.getNormalTeamMembers().size());
				Toast.makeText(FillOutPreSetActivity.this, "A队不存在" + number + "号队员!!!", Toast.LENGTH_LONG).show();
				return;
			}
			else {
				if(teamAInitialPosition.indexOf(teamAMember) != -1) {
					Toast.makeText(FillOutPreSetActivity.this, "A队" + number + "号队员位置重复填写!!!", Toast.LENGTH_LONG).show();
					return;
				}
				else {
					teamAInitialPosition.add(teamAMember);
				}
			}
		}
		set.setTeamAInitialPosition(teamAInitialPosition);
		List<TeamMember> teamBInitialPosition = new ArrayList<TeamMember>();
		teamBInitialPosition.add(null);
		for(int i = 0; i < this.getTeamBPosition().size(); ++i) {
			int number = -1;
			try {
				number = Integer.parseInt(this.getTeamBPosition().get(i).getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				Toast.makeText(FillOutPreSetActivity.this, "B队未填写完成!!!", Toast.LENGTH_LONG).show();
				return;
			}
			TeamMember teamBMember = teamB.getTeamMemberByNum(number);
			if(teamBMember == null) {
				System.out.println("number:" + number + " teamB.normalSize():" + teamB.getNormalTeamMembers().size());
				Toast.makeText(FillOutPreSetActivity.this, "B队不存在" + number + "号队员!!!", Toast.LENGTH_LONG).show();
				return;
			}
			else {
				if(teamBInitialPosition.indexOf(teamBMember) != -1) {
					Toast.makeText(FillOutPreSetActivity.this, "B队" + number + "号队员位置重复填写!!!", Toast.LENGTH_LONG).show();
					return;
				}
				else {
					teamBInitialPosition.add(teamBMember);
				}
			}
		}
		set.setTeamBInitialPosition(teamBInitialPosition);
		
		match.setNowSet(set);
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
		// 跳转到比赛中activity
		Intent i = new Intent(FillOutPreSetActivity.this, SetInPlayActivity.class);
		startActivity(i);
		finish();
	}
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "FillOutPreSetActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "FillOutPreSetActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "FillOutPreSetActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "FillOutPreSetActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "FillOutPreSetActivity onDestroy() event");
	}

	public RadioGroup getRadioGroupFirstTeam() {
		return radioGroupFirstTeam;
	}

	public void setRadioGroupFirstTeam(RadioGroup radioGroupFirstTeam) {
		this.radioGroupFirstTeam = radioGroupFirstTeam;
	}

	public TextView getTeamAPositionTitle() {
		return teamAPositionTitle;
	}

	public void setTeamAPositionTitle(TextView teamAPositionTitle) {
		this.teamAPositionTitle = teamAPositionTitle;
	}

	public List<EditText> getTeamAPosition() {
		return teamAPosition;
	}

	public void setTeamAPosition(List<EditText> teamAPosition) {
		this.teamAPosition = teamAPosition;
	}

	public TextView getTeamBPositionTitle() {
		return teamBPositionTitle;
	}

	public void setTeamBPositionTitle(TextView teamBPositionTitle) {
		this.teamBPositionTitle = teamBPositionTitle;
	}

	public List<EditText> getTeamBPosition() {
		return teamBPosition;
	}

	public void setTeamBPosition(List<EditText> teamBPosition) {
		this.teamBPosition = teamBPosition;
	}
}
