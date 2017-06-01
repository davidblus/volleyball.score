package com.example.volleyball.score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMatchResultActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private TextView nameTeamA;
	private TextView nameTeamB;
	private List<TextView> pauseTeamA;
	private List<TextView> substitutionTeamA;
	private List<TextView> winFailTeamA;
	private List<TextView> getScoreTeamA;
	private List<TextView> setDuration;
	private List<TextView> getScoreTeamB;
	private List<TextView> winFailTeamB;
	private List<TextView> substitutionTeamB;
	private List<TextView> pauseTeamB;
	private TextView matchPauseA;
	private TextView matchSubstitutionA;
	private TextView matchWinFailA;
	private TextView matchGetScoreA;
	private TextView matchDuration;
	private TextView matchGetScoreB;
	private TextView matchWinFailB;
	private TextView matchSubstitutionB;
	private TextView matchPauseB;
	private TextView matchStartTime;
	private TextView matchEndTime;
	private TextView matchTotalTime;
	private TextView winTeam;
	private TextView overallScore;
	private CheckBox captainDetermine;
	private CheckBox judgeDetermine;
	private CheckBox recorderDetermine;
	private LinearLayout rankingLayout;
	private EditText ranking_win;
	private EditText ranking_fail;
	
	private boolean isJustShowResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 考虑非循环赛时，出现比赛队伍双方名次输入框！
		Log.d(msg, "ShowMatchResultActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_match_result);
		
		this.setNameTeamA((TextView) findViewById(R.id.nameTeamA));
		this.setNameTeamB((TextView) findViewById(R.id.nameTeamB));
		List<TextView> pauseTeamA = new ArrayList<TextView>();
		List<TextView> substitutionTeamA = new ArrayList<TextView>();
		List<TextView> winFailTeamA = new ArrayList<TextView>();
		List<TextView> getScoreTeamA = new ArrayList<TextView>();
		List<TextView> setDuration = new ArrayList<TextView>();
		List<TextView> getScoreTeamB = new ArrayList<TextView>();
		List<TextView> winFailTeamB = new ArrayList<TextView>();
		List<TextView> substitutionTeamB = new ArrayList<TextView>();
		List<TextView> pauseTeamB = new ArrayList<TextView>();
		pauseTeamA.add((TextView) findViewById(R.id.firstPauseA));
		pauseTeamA.add((TextView) findViewById(R.id.secondPauseA));
		pauseTeamA.add((TextView) findViewById(R.id.thirdPauseA));
		pauseTeamA.add((TextView) findViewById(R.id.fourthPauseA));
		pauseTeamA.add((TextView) findViewById(R.id.fifthPauseA));
		substitutionTeamA.add((TextView) findViewById(R.id.firstSubstitutionA));
		substitutionTeamA.add((TextView) findViewById(R.id.secondSubstitutionA));
		substitutionTeamA.add((TextView) findViewById(R.id.thirdSubstitutionA));
		substitutionTeamA.add((TextView) findViewById(R.id.fourthSubstitutionA));
		substitutionTeamA.add((TextView) findViewById(R.id.fifthSubstitutionA));
		winFailTeamA.add((TextView) findViewById(R.id.firstWinFailA));
		winFailTeamA.add((TextView) findViewById(R.id.secondWinFailA));
		winFailTeamA.add((TextView) findViewById(R.id.thirdWinFailA));
		winFailTeamA.add((TextView) findViewById(R.id.fourthWinFailA));
		winFailTeamA.add((TextView) findViewById(R.id.fifthWinFailA));
		getScoreTeamA.add((TextView) findViewById(R.id.firstGetScoreA));
		getScoreTeamA.add((TextView) findViewById(R.id.secondGetScoreA));
		getScoreTeamA.add((TextView) findViewById(R.id.thirdGetScoreA));
		getScoreTeamA.add((TextView) findViewById(R.id.fourthGetScoreA));
		getScoreTeamA.add((TextView) findViewById(R.id.fifthGetScoreA));
		setDuration.add((TextView) findViewById(R.id.firstDuration));
		setDuration.add((TextView) findViewById(R.id.secondDuration));
		setDuration.add((TextView) findViewById(R.id.thirdDuration));
		setDuration.add((TextView) findViewById(R.id.fourthDuration));
		setDuration.add((TextView) findViewById(R.id.fifthDuration));
		getScoreTeamB.add((TextView) findViewById(R.id.firstGetScoreB));
		getScoreTeamB.add((TextView) findViewById(R.id.secondGetScoreB));
		getScoreTeamB.add((TextView) findViewById(R.id.thirdGetScoreB));
		getScoreTeamB.add((TextView) findViewById(R.id.fourthGetScoreB));
		getScoreTeamB.add((TextView) findViewById(R.id.fifthGetScoreB));
		winFailTeamB.add((TextView) findViewById(R.id.firstWinFailB));
		winFailTeamB.add((TextView) findViewById(R.id.secondWinFailB));
		winFailTeamB.add((TextView) findViewById(R.id.thirdWinFailB));
		winFailTeamB.add((TextView) findViewById(R.id.fourthWinFailB));
		winFailTeamB.add((TextView) findViewById(R.id.fifthWinFailB));
		substitutionTeamB.add((TextView) findViewById(R.id.firstSubstitutionB));
		substitutionTeamB.add((TextView) findViewById(R.id.secondSubstitutionB));
		substitutionTeamB.add((TextView) findViewById(R.id.thirdSubstitutionB));
		substitutionTeamB.add((TextView) findViewById(R.id.fourthSubstitutionB));
		substitutionTeamB.add((TextView) findViewById(R.id.fifthSubstitutionB));
		pauseTeamB.add((TextView) findViewById(R.id.firstPauseB));
		pauseTeamB.add((TextView) findViewById(R.id.secondPauseB));
		pauseTeamB.add((TextView) findViewById(R.id.thirdPauseB));
		pauseTeamB.add((TextView) findViewById(R.id.fourthPauseB));
		pauseTeamB.add((TextView) findViewById(R.id.fifthPauseB));
		this.setPauseTeamA(pauseTeamA);
		this.setSubstitutionTeamA(substitutionTeamA);
		this.setWinFailTeamA(winFailTeamA);
		this.setGetScoreTeamA(getScoreTeamA);
		this.setSetDuration(setDuration);
		this.setGetScoreTeamB(getScoreTeamB);
		this.setWinFailTeamB(winFailTeamB);
		this.setSubstitutionTeamB(substitutionTeamB);
		this.setPauseTeamB(pauseTeamB);
		this.setMatchPauseA((TextView) findViewById(R.id.matchPauseA));
		this.setMatchSubstitutionA((TextView) findViewById(R.id.matchSubstitutionA));
		this.setMatchWinFailA((TextView) findViewById(R.id.matchWinFailA));
		this.setMatchGetScoreA((TextView) findViewById(R.id.matchGetScoreA));
		this.setMatchDuration((TextView) findViewById(R.id.matchDuration));
		this.setMatchGetScoreB((TextView) findViewById(R.id.matchGetScoreB));
		this.setMatchWinFailB((TextView) findViewById(R.id.matchWinFailB));
		this.setMatchSubstitutionB((TextView) findViewById(R.id.matchSubstitutionB));
		this.setMatchPauseB((TextView) findViewById(R.id.matchPauseB));
		this.setMatchStartTime((TextView) findViewById(R.id.matchStartTime));
		this.setMatchEndTime((TextView) findViewById(R.id.matchEndTime));
		this.setMatchTotalTime((TextView) findViewById(R.id.matchTotalTime));
		this.setWinTeam((TextView) findViewById(R.id.winTeam));
		this.setOverallScore((TextView) findViewById(R.id.overallScore));
		this.setCaptainDetermine((CheckBox) findViewById(R.id.captainDetermine));
		this.setJudgeDetermine((CheckBox) findViewById(R.id.judgeDetermine));
		this.setRecorderDetermine((CheckBox) findViewById(R.id.recorderDetermine));
		this.setRankingLayout((LinearLayout) findViewById(R.id.rankingLayout));
		this.setRanking_win((EditText) findViewById(R.id.ranking_win));
		this.setRanking_fail((EditText) findViewById(R.id.ranking_fail));

		//如果只是显示比赛结果，则需要把确认复选框都checked
		try {
			Bundle bundle = this.getIntent().getExtras();
			String name = bundle.getString("name");
			System.out.println("Param: name=" + name);
			this.setJustShowResult(true);
		} catch (Exception e) {
			System.out.println("No params!!!");
			this.setJustShowResult(false);
		}
		
		//设置界面显示内容
		this.updateView();
		
	}

	public void determine(View view) {
		if(this.isJustShowResult()) {
			// 只是用于显示比赛结果，结束这个activity
			finish();
			return;
		}
		if(!this.getCaptainDetermine().isChecked() || !this.getJudgeDetermine().isChecked() || !this.getRecorderDetermine().isChecked()) {
			Toast.makeText(ShowMatchResultActivity.this, "请全部确认！！！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 写入文件
		DataApplication app = (DataApplication) getApplication();
		// 如果不是循环赛，需要获取并设置两支队伍的名次
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		if(!match.getCompetitionSystem().equals("循环制")) {
			if(match.getWinTeam().getId() == match.getTeamA().getId()) {
				match.setRankingTeamA(Integer.parseInt(this.getRanking_win().getText().toString()));
				match.setRankingTeamB(Integer.parseInt(this.getRanking_fail().getText().toString()));
			}
			else {
				match.setRankingTeamA(Integer.parseInt(this.getRanking_fail().getText().toString()));
				match.setRankingTeamB(Integer.parseInt(this.getRanking_win().getText().toString()));
			}
			leagueMatch.setNowMatch(match);
			app.setNowLeagueMatch(leagueMatch);
		}
		if(!app.saveLeagueMatchToXml()) {
			Toast.makeText(ShowMatchResultActivity.this, "无法保存本场比赛数据到sd卡！！！", Toast.LENGTH_LONG).show();
			return;
		}
		
		// 跳转到比赛队伍首页
		Intent i = new Intent(ShowMatchResultActivity.this, LeagueMatchHomePageActivity.class);
		startActivity(i);
		finish();
	}
	
	public void updateView() {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();

		this.getNameTeamA().setText(match.getTeamA().getName());
		this.getNameTeamB().setText(match.getTeamB().getName());
		for(int i = 0; i < match.getSets().size(); ++i) {
			try {
				this.getPauseTeamA().get(i).setText("" + match.getSets().get(i).getPauseTeamA().size());
			} catch (Exception e) {
				this.getPauseTeamA().get(i).setText("0");
				e.printStackTrace();
			}
			try {
				this.getPauseTeamB().get(i).setText("" + match.getSets().get(i).getPauseTeamB().size());
			} catch (Exception e) {
				this.getPauseTeamB().get(i).setText("0");
				e.printStackTrace();
			}
			try {
				this.getSubstitutionTeamA().get(i).setText("" + match.getSets().get(i).getSubstitutionTeamA().size());
			} catch (Exception e) {
				this.getSubstitutionTeamA().get(i).setText("0");
				e.printStackTrace();
			}
			try {
				this.getSubstitutionTeamB().get(i).setText("" + match.getSets().get(i).getSubstitutionTeamB().size());
			} catch (Exception e) {
				this.getSubstitutionTeamB().get(i).setText("0");
				e.printStackTrace();
			}
			if(match.getSets().get(i).getWinTeam().getId() == match.getTeamA().getId()) {
				this.getWinFailTeamA().get(i).setText("w");
			}
			else {
				this.getWinFailTeamB().get(i).setText("w");
			}
			this.getGetScoreTeamA().get(i).setText("" + match.getSets().get(i).getFinalScoreTeamA());
			this.getGetScoreTeamB().get(i).setText("" + match.getSets().get(i).getFinalScoreTeamB());
			this.getSetDuration().get(i).setText(match.getSets().get(i).getSetDuration());
		}
		this.getMatchPauseA().setText("" + match.getPauseTimeA());
		this.getMatchPauseB().setText("" + match.getPauseTimeB());
		this.getMatchSubstitutionA().setText("" + match.getSubstitutionTimeA());
		this.getMatchSubstitutionB().setText("" + match.getSubstitutionTimeB());
		if(match.getWinTeam().getId() == match.getTeamA().getId()) {
			this.getMatchWinFailA().setText("w");
			this.getRanking_win().setText("" + match.getRankingTeamA());
			this.getRanking_fail().setText("" + match.getRankingTeamB());
		}
		else {
			this.getMatchWinFailB().setText("w");
			this.getRanking_win().setText("" + match.getRankingTeamB());
			this.getRanking_fail().setText("" + match.getRankingTeamA());
		}
		this.getMatchGetScoreA().setText("" + match.getTotalScoreTeamA());
		this.getMatchGetScoreB().setText("" + match.getTotalScoreTeamB());
		this.getMatchDuration().setText("" + match.getSetsDuration() + "分");
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh点mm分", Locale.CHINA);
		this.getMatchStartTime().setText(sdf.format(match.getMatchStartDate()));
		this.getMatchEndTime().setText(sdf.format(match.getMatchEndDate()));
		
		long durationSecond = (match.getMatchEndDate().getTime() - match.getMatchStartDate().getTime()) / 1000;
		long hour = durationSecond % (24*3600) / 3600;
		long minute = durationSecond % 3600 / 60;
		this.getMatchTotalTime().setText("" + hour + "时" + minute + "分");
		this.getWinTeam().setText(match.getWinTeam().getName());
		this.getOverallScore().setText("3:" + match.getFailedSetTime());
		if(match.getCompetitionSystem().equals("循环制")) {
			this.getRankingLayout().setVisibility(View.INVISIBLE);
		}
		
		if(this.isJustShowResult()) {
			// 只是显示结果
			this.getCaptainDetermine().setChecked(true);
			this.getJudgeDetermine().setChecked(true);
			this.getRecorderDetermine().setChecked(true);
		}
	}

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "ShowMatchResultActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "ShowMatchResultActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "ShowMatchResultActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "ShowMatchResultActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "ShowMatchResultActivity onDestroy() event");
	}

	public TextView getNameTeamA() {
		return nameTeamA;
	}

	public void setNameTeamA(TextView nameTeamA) {
		this.nameTeamA = nameTeamA;
	}

	public TextView getNameTeamB() {
		return nameTeamB;
	}

	public void setNameTeamB(TextView nameTeamB) {
		this.nameTeamB = nameTeamB;
	}

	public List<TextView> getPauseTeamA() {
		return pauseTeamA;
	}

	public void setPauseTeamA(List<TextView> pauseTeamA) {
		this.pauseTeamA = pauseTeamA;
	}

	public List<TextView> getSubstitutionTeamA() {
		return substitutionTeamA;
	}

	public void setSubstitutionTeamA(List<TextView> substitutionTeamA) {
		this.substitutionTeamA = substitutionTeamA;
	}

	public List<TextView> getWinFailTeamA() {
		return winFailTeamA;
	}

	public void setWinFailTeamA(List<TextView> winFailTeamA) {
		this.winFailTeamA = winFailTeamA;
	}

	public List<TextView> getGetScoreTeamA() {
		return getScoreTeamA;
	}

	public void setGetScoreTeamA(List<TextView> getScoreTeamA) {
		this.getScoreTeamA = getScoreTeamA;
	}

	public List<TextView> getSetDuration() {
		return setDuration;
	}

	public void setSetDuration(List<TextView> setDuration) {
		this.setDuration = setDuration;
	}

	public List<TextView> getGetScoreTeamB() {
		return getScoreTeamB;
	}

	public void setGetScoreTeamB(List<TextView> getScoreTeamB) {
		this.getScoreTeamB = getScoreTeamB;
	}

	public List<TextView> getWinFailTeamB() {
		return winFailTeamB;
	}

	public void setWinFailTeamB(List<TextView> winFailTeamB) {
		this.winFailTeamB = winFailTeamB;
	}

	public List<TextView> getSubstitutionTeamB() {
		return substitutionTeamB;
	}

	public void setSubstitutionTeamB(List<TextView> substitutionTeamB) {
		this.substitutionTeamB = substitutionTeamB;
	}

	public List<TextView> getPauseTeamB() {
		return pauseTeamB;
	}

	public void setPauseTeamB(List<TextView> pauseTeamB) {
		this.pauseTeamB = pauseTeamB;
	}

	public TextView getMatchPauseA() {
		return matchPauseA;
	}

	public void setMatchPauseA(TextView matchPauseA) {
		this.matchPauseA = matchPauseA;
	}

	public TextView getMatchSubstitutionA() {
		return matchSubstitutionA;
	}

	public void setMatchSubstitutionA(TextView matchSubstitutionA) {
		this.matchSubstitutionA = matchSubstitutionA;
	}

	public TextView getMatchWinFailA() {
		return matchWinFailA;
	}

	public void setMatchWinFailA(TextView matchWinFailA) {
		this.matchWinFailA = matchWinFailA;
	}

	public TextView getMatchGetScoreA() {
		return matchGetScoreA;
	}

	public void setMatchGetScoreA(TextView matchGetScoreA) {
		this.matchGetScoreA = matchGetScoreA;
	}

	public TextView getMatchDuration() {
		return matchDuration;
	}

	public void setMatchDuration(TextView matchDuration) {
		this.matchDuration = matchDuration;
	}

	public TextView getMatchGetScoreB() {
		return matchGetScoreB;
	}

	public void setMatchGetScoreB(TextView matchGetScoreB) {
		this.matchGetScoreB = matchGetScoreB;
	}

	public TextView getMatchWinFailB() {
		return matchWinFailB;
	}

	public void setMatchWinFailB(TextView matchWinFailB) {
		this.matchWinFailB = matchWinFailB;
	}

	public TextView getMatchSubstitutionB() {
		return matchSubstitutionB;
	}

	public void setMatchSubstitutionB(TextView matchSubstitutionB) {
		this.matchSubstitutionB = matchSubstitutionB;
	}

	public TextView getMatchPauseB() {
		return matchPauseB;
	}

	public void setMatchPauseB(TextView matchPauseB) {
		this.matchPauseB = matchPauseB;
	}

	public TextView getMatchStartTime() {
		return matchStartTime;
	}

	public void setMatchStartTime(TextView matchStartTime) {
		this.matchStartTime = matchStartTime;
	}

	public TextView getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(TextView matchEndTime) {
		this.matchEndTime = matchEndTime;
	}

	public TextView getMatchTotalTime() {
		return matchTotalTime;
	}

	public void setMatchTotalTime(TextView matchTotalTime) {
		this.matchTotalTime = matchTotalTime;
	}

	public TextView getWinTeam() {
		return winTeam;
	}

	public void setWinTeam(TextView winTeam) {
		this.winTeam = winTeam;
	}

	public TextView getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(TextView overallScore) {
		this.overallScore = overallScore;
	}

	public CheckBox getCaptainDetermine() {
		return captainDetermine;
	}

	public void setCaptainDetermine(CheckBox captainDetermine) {
		this.captainDetermine = captainDetermine;
	}

	public CheckBox getJudgeDetermine() {
		return judgeDetermine;
	}

	public void setJudgeDetermine(CheckBox judgeDetermine) {
		this.judgeDetermine = judgeDetermine;
	}

	public CheckBox getRecorderDetermine() {
		return recorderDetermine;
	}

	public void setRecorderDetermine(CheckBox recorderDetermine) {
		this.recorderDetermine = recorderDetermine;
	}

	public boolean isJustShowResult() {
		return isJustShowResult;
	}

	public void setJustShowResult(boolean isJustShowResult) {
		this.isJustShowResult = isJustShowResult;
	}

	public LinearLayout getRankingLayout() {
		return rankingLayout;
	}

	public void setRankingLayout(LinearLayout rankingLayout) {
		this.rankingLayout = rankingLayout;
	}

	public EditText getRanking_win() {
		return ranking_win;
	}

	public void setRanking_win(EditText ranking_win) {
		this.ranking_win = ranking_win;
	}

	public EditText getRanking_fail() {
		return ranking_fail;
	}

	public void setRanking_fail(EditText ranking_fail) {
		this.ranking_fail = ranking_fail;
	}
	
}
