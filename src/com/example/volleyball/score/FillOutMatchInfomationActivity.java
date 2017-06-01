package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FillOutMatchInfomationActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private TextView matchName;
	private EditText cityEditText, placeEditText, stageEditText;
	private Spinner categoryChoose;
	private Spinner competitionSystem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "FillOutMatchInfomationActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_out_match_infomation);
		
		this.setMatchName((TextView) findViewById(R.id.match_name));
		this.setCityEditText((EditText) findViewById(R.id.cityEditText));
		this.setPlaceEditText((EditText) findViewById(R.id.placeEditText));
		this.setStageEditText((EditText) findViewById(R.id.stageEditText));
		this.setCategoryChoose((Spinner) findViewById(R.id.categoryChoose));
		this.setCompetitionSystem((Spinner) findViewById(R.id.competitionSystem));
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		this.getMatchName().setText(match.getName());
		ArrayAdapter<String> arrayAdapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCategoryData());
		this.getCategoryChoose().setAdapter(arrayAdapterCategory);
		ArrayAdapter<String> arrayAdapterCompetitionSystem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCompetitionSystemData());
		this.getCompetitionSystem().setAdapter(arrayAdapterCompetitionSystem);
	}

	private List<String> getCompetitionSystemData() {
		List<String> data = new ArrayList<String>();
		data.add("循环制");
		data.add("淘汰制");
		data.add("半决赛");
		data.add("复活赛");
		data.add("主决赛");
		return data;
	}

	private List<String> getCategoryData() {
		List<String> data = new ArrayList<String>();
		data.add("成年");
		data.add("青年");
		data.add("少年");
		return data;
	}

	public void nextStep(View view) {
		// 记录填写的信息，跳转到第一局比赛的赛前填写位置信息activity
		String city = this.getCityEditText().getText().toString();
		if (city.equals("")) {
			Toast.makeText(FillOutMatchInfomationActivity.this, "请输入城市名称！！！", Toast.LENGTH_LONG).show();
			return;
		}
		String place = this.getPlaceEditText().getText().toString();
		int index = place.indexOf("/");
		String stadium = "";
		String site = "";
		try {
			stadium = place.substring(0, index);
			site = place.substring(index + 1);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(FillOutMatchInfomationActivity.this, "场馆/场地输入错误，示例：胜利体育馆/3号场地", Toast.LENGTH_LONG).show();
			return;
		}
		if (stadium.equals("")) {
			Toast.makeText(FillOutMatchInfomationActivity.this, "请输入场馆名称！！！", Toast.LENGTH_LONG).show();
			return;
		}
		if (site.equals("")) {
			Toast.makeText(FillOutMatchInfomationActivity.this, "请输入场地名称！！！", Toast.LENGTH_LONG).show();
			return;
		}
		String stage = this.getStageEditText().getText().toString();
		if (stage.equals("")) {
			Toast.makeText(FillOutMatchInfomationActivity.this, "请输入阶段名称！！！", Toast.LENGTH_LONG).show();
			return;
		}
		String category = (String) this.getCategoryChoose().getSelectedItem();
		String competitonSystem = (String) this.getCompetitionSystem().getSelectedItem();

		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		
		match.setCity(city);
		match.setStadium(stadium);
		match.setSite(site);
		match.setStage(stage);
		match.setCategory(category);
		match.setCompetitionSystem(competitonSystem);
		
		match.setMatchStartDate(new Date());
		List<Set> sets = new ArrayList<Set>();
		Set firstSet = new Set();
		firstSet.setNum(1);
		sets.add(firstSet);
		match.setSets(sets);
		match.setSetNumber(1);
		
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
		
		// 跳转到第一局比赛的赛前填写位置信息activity
		Intent i = new Intent(FillOutMatchInfomationActivity.this, FillOutPreSetActivity.class);
		startActivity(i);
		finish();
	}
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "FillOutMatchInfomationActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "FillOutMatchInfomationActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "FillOutMatchInfomationActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "FillOutMatchInfomationActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "FillOutMatchInfomationActivity onDestroy() event");
	}

	public TextView getMatchName() {
		return matchName;
	}

	public void setMatchName(TextView matchName) {
		this.matchName = matchName;
	}

	public EditText getCityEditText() {
		return cityEditText;
	}

	public void setCityEditText(EditText cityEditText) {
		this.cityEditText = cityEditText;
	}

	public EditText getPlaceEditText() {
		return placeEditText;
	}

	public void setPlaceEditText(EditText placeEditText) {
		this.placeEditText = placeEditText;
	}

	public EditText getStageEditText() {
		return stageEditText;
	}

	public void setStageEditText(EditText stageEditText) {
		this.stageEditText = stageEditText;
	}

	public Spinner getCategoryChoose() {
		return categoryChoose;
	}

	public void setCategoryChoose(Spinner categoryChoose) {
		this.categoryChoose = categoryChoose;
	}

	public Spinner getCompetitionSystem() {
		return competitionSystem;
	}

	public void setCompetitionSystem(Spinner competitionSystem) {
		this.competitionSystem = competitionSystem;
	}
}
