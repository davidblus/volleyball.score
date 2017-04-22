package com.example.volleyball.score;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Pause;
import com.volleyball.match.Point;
import com.volleyball.match.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetPauseActivity extends Activity {
	
	private String msg = GlobalConstant.msg;

	private RadioGroup PauseGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ��ͣÿ�����λ���
		Log.d(msg, "SetPauseActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_pause);
		
		this.setPauseGroup((RadioGroup) findViewById(R.id.radioGroupPauseTeam));

		//���ý�����ʾ����
		this.updateView();
	}

	public void updateView() {
		//���ý�����ʾ����
		RadioButton pauseTeamA = (RadioButton) findViewById(R.id.radioA);
		RadioButton pauseTeamB = (RadioButton) findViewById(R.id.radioB);
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		Point lastPoint = set.getLastPointRecord();
		if(lastPoint == null) {
			pauseTeamA.setText("A�ӣ�0:0");
			pauseTeamB.setText("B�ӣ�0:0");
			return;
		}
		
		String pauseTeamAShow = "A�ӣ�";
		String pauseTeamBShow = "B�ӣ�";
		if(lastPoint.getTeam().getId() == match.getTeamA().getId()) {
			pauseTeamAShow = pauseTeamAShow + lastPoint.getServerScore() + ":" + lastPoint.getClientScore();
			pauseTeamBShow = pauseTeamBShow + lastPoint.getClientScore() + ":" + lastPoint.getServerScore();
		}
		else {
			pauseTeamAShow = pauseTeamAShow + lastPoint.getClientScore() + ":" + lastPoint.getServerScore();
			pauseTeamBShow = pauseTeamBShow + lastPoint.getServerScore() + ":" + lastPoint.getClientScore();
		}
		pauseTeamA.setText(pauseTeamAShow);
		pauseTeamB.setText(pauseTeamBShow);
	}
	
	public void endPause(View view) {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		
		RadioButton pauseTeam = (RadioButton) findViewById(this.getPauseGroup().getCheckedRadioButtonId());
		if(pauseTeam == null) {
			Toast.makeText(SetPauseActivity.this, "��ѡ����ͣ���飡����", Toast.LENGTH_SHORT).show();
			return;
		}
		String pauseTeamStr = (String) pauseTeam.getText();
		if(pauseTeamStr.indexOf("A") != -1) {
			if(set.getPauseTeamA() != null && set.getPauseTeamA().size() > 1) {
				Toast.makeText(SetPauseActivity.this, "A�ӽ���ͣʧ�ܣ�A���ѽ�2����ͣ������", Toast.LENGTH_LONG).show();
				finish();
				return;
			}
			pauseTeamStr = pauseTeamStr.replace("A�ӣ�", "");
			Pause pauseTeamA = new Pause(Integer.parseInt(pauseTeamStr.split(":")[0]), Integer.parseInt(pauseTeamStr.split(":")[1]));
			set.addPauseTeamA(pauseTeamA);
		}
		else {
			if(set.getPauseTeamB() != null && set.getPauseTeamB().size() > 1) {
				Toast.makeText(SetPauseActivity.this, "B�ӽ���ͣʧ�ܣ�B���ѽ�2����ͣ������", Toast.LENGTH_LONG).show();
				finish();
				return;
			}
			pauseTeamStr = pauseTeamStr.replace("B�ӣ�", "");
			Pause pauseTeamB = new Pause(Integer.parseInt(pauseTeamStr.split(":")[0]), Integer.parseInt(pauseTeamStr.split(":")[1]));
			set.addPauseTeamB(pauseTeamB);
		}
		
		match.setNowSet(set);
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
		
		finish();
	}

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "SetPauseActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "SetPauseActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "SetPauseActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "SetPauseActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "SetPauseActivity onDestroy() event");
	}

	public RadioGroup getPauseGroup() {
		return PauseGroup;
	}

	public void setPauseGroup(RadioGroup pauseGroup) {
		PauseGroup = pauseGroup;
	}
}
