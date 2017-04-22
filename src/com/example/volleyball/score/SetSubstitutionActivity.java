package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Point;
import com.volleyball.match.Set;
import com.volleyball.match.Substitution;
import com.volleyball.match.Team;
import com.volleyball.match.TeamMember;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetSubstitutionActivity extends Activity {

	private String msg = GlobalConstant.msg;

	private RadioGroup SubstitutionGroup;
	private RadioButton radioAButton;
	private RadioButton radioBButton;
	private List<TextView> downNumbers;
	private List<Spinner> upNumbers;

	private List<TeamMember> teamAPosition;//当前比赛A队位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	private List<TeamMember> teamBPosition;//当前比赛B队位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "SetSubstitutionActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_substitution);
		
		try {
			Intent intent = getIntent();
			@SuppressWarnings("unchecked")
			ArrayList<TeamMember> teamAPosition = (ArrayList<TeamMember>) intent.getSerializableExtra("teamAPosition");
			@SuppressWarnings("unchecked")
			ArrayList<TeamMember> teamBPosition = (ArrayList<TeamMember>) intent.getSerializableExtra("teamBPosition");
			this.setTeamAPosition(teamAPosition);
			this.setTeamBPosition(teamBPosition);
		} catch (Exception e) {
			// 
			e.printStackTrace();
			System.out.println("没有传入数据，取消换人。");
			finish();
		}
		
		this.setSubstitutionGroup((RadioGroup) findViewById(R.id.radioGroupSubstitution));
		this.setRadioAButton((RadioButton) findViewById(R.id.radioA));
		this.setRadioBButton((RadioButton) findViewById(R.id.radioB));
		List<TextView> downNumbers = new ArrayList<TextView>();
		downNumbers.add((TextView) findViewById(R.id.downNumber1));
		downNumbers.add((TextView) findViewById(R.id.downNumber2));
		downNumbers.add((TextView) findViewById(R.id.downNumber3));
		downNumbers.add((TextView) findViewById(R.id.downNumber4));
		downNumbers.add((TextView) findViewById(R.id.downNumber5));
		downNumbers.add((TextView) findViewById(R.id.downNumber6));
		this.setDownNumbers(downNumbers);
		List<Spinner> upNumbers = new ArrayList<Spinner>();
		upNumbers.add((Spinner) findViewById(R.id.upNumber1));
		upNumbers.add((Spinner) findViewById(R.id.upNumber2));
		upNumbers.add((Spinner) findViewById(R.id.upNumber3));
		upNumbers.add((Spinner) findViewById(R.id.upNumber4));
		upNumbers.add((Spinner) findViewById(R.id.upNumber5));
		upNumbers.add((Spinner) findViewById(R.id.upNumber6));
		this.setUpNumbers(upNumbers);
		
		//设置界面显示内容
		this.updateView();
		
		//监听队伍选择
		this.getSubstitutionGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 
				if(checkedId == -1) {
					return;
				}
				if(checkedId == getRadioAButton().getId()) {
					updateNumView("A");
				}
				else {
					updateNumView("B");
				}
			}
		});
	}

	public List<String> getTeamADownNums() {
		// 
		List<String> data = new ArrayList<String>();
		for (int i = 1; i < this.getTeamAPosition().size(); ++i) {
			data.add("" + this.getTeamAPosition().get(i).getNumber());
		}
		
		return data;
	}
	public List<String> getTeamBDownNums() {
		// 
		List<String> data = new ArrayList<String>();
		for (int i = 1; i < this.getTeamBPosition().size(); ++i) {
			data.add("" + this.getTeamBPosition().get(i).getNumber());
		}
		
		return data;
	}
	
	public List<String> getTeamAUpNums() {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();

		List<String> data = new ArrayList<String>();
		List<TeamMember> remainMembers;
		remainMembers = this.getTeamAPosition();
		
		for (TeamMember teamMember:match.getTeamA().getNormalTeamMembers()) {
			if (!TeamMember.listContainObj(remainMembers, teamMember)) {
				data.add("" + teamMember.getNumber());
			}
		}
		for (TeamMember teamMember:match.getTeamA().getLiberoTeamMembers()) {
			if (!TeamMember.listContainObj(remainMembers, teamMember)) {
				data.add("" + teamMember.getNumber());
			}
		}

		return data;
	}	
	public List<String> getTeamBUpNums() {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();

		List<String> data = new ArrayList<String>();
		List<TeamMember> remainMembers;
		remainMembers = this.getTeamBPosition();
		
		for (TeamMember teamMember:match.getTeamB().getNormalTeamMembers()) {
			if (!TeamMember.listContainObj(remainMembers, teamMember)) {
				data.add("" + teamMember.getNumber());
			}
		}
		for (TeamMember teamMember:match.getTeamB().getLiberoTeamMembers()) {
			if (!TeamMember.listContainObj(remainMembers, teamMember)) {
				data.add("" + teamMember.getNumber());
			}
		}

		return data;
	}

	private void updateNumView(String aOrb) {
		// 设置换下人员textview
		List<String> downNumbers, upNumbers;
		if (aOrb.equals("A")) {
			downNumbers = this.getTeamADownNums();
			upNumbers = this.getTeamAUpNums();
		}
		else {
			downNumbers = this.getTeamBDownNums();
			upNumbers = this.getTeamBUpNums();
		}
		upNumbers.add(0, "");//用于显示空
		for (int i = 0; i < this.getDownNumbers().size(); ++i) {
			this.getDownNumbers().get(i).setText(downNumbers.get(i));
		}
		ArrayAdapter<String> arrayAdapterChoose = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, upNumbers);
		for(Spinner upNumber:this.getUpNumbers()) {
			upNumber.setAdapter(arrayAdapterChoose);
		}
	}

	public void updateView() {
		// 设置界面显示内容
		RadioButton substitutionTeamA = this.getRadioAButton();
		RadioButton substitutionTeamB = this.getRadioBButton();
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		Point lastPoint = set.getLastPointRecord();
		if(lastPoint == null) {
			substitutionTeamA.setText("A队：0:0");
			substitutionTeamB.setText("B队：0:0");
			return;
		}
		else {
			String substitutionTeamAShow = "A队：";
			String substitutionTeamBShow = "B队：";
			if(lastPoint.getTeam().getId() == match.getTeamA().getId()) {
				substitutionTeamAShow = substitutionTeamAShow + lastPoint.getServerScore() + ":" + lastPoint.getClientScore();
				substitutionTeamBShow = substitutionTeamBShow + lastPoint.getClientScore() + ":" + lastPoint.getServerScore();
			}
			else {
				substitutionTeamAShow = substitutionTeamAShow + lastPoint.getClientScore() + ":" + lastPoint.getServerScore();
				substitutionTeamBShow = substitutionTeamBShow + lastPoint.getServerScore() + ":" + lastPoint.getClientScore();
			}
			substitutionTeamA.setText(substitutionTeamAShow);
			substitutionTeamB.setText(substitutionTeamBShow);
		}
	}

	public void substitutionDetermine(View view) {
		// 点击确定按钮
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		
		Intent intent = this.getIntent();
		String substitutionTeamStr;
		Team substitutionTeam;
		if (this.getRadioAButton().isChecked()) {
			intent.putExtra("substitutionTeam", "A");
			substitutionTeamStr = (String) this.getRadioAButton().getText();
			substitutionTeamStr = substitutionTeamStr.replace("A队：", "");
			substitutionTeam = match.getTeamA();
		}
		else if (this.getRadioBButton().isChecked()) {
			intent.putExtra("substitutionTeam", "B");
			substitutionTeamStr = (String) this.getRadioBButton().getText();
			substitutionTeamStr = substitutionTeamStr.replace("B队：", "");
			substitutionTeam = match.getTeamB();
		}
		else {
			Toast.makeText(SetSubstitutionActivity.this, "请选择换人队伍！！！", Toast.LENGTH_SHORT).show();
			return;
		}
		Substitution substitution = new Substitution(Integer.parseInt(substitutionTeamStr.split(":")[0]), Integer.parseInt(substitutionTeamStr.split(":")[1]));
		List<TeamMember> downTeamMembers = new ArrayList<TeamMember>();
		List<TeamMember> upTeamMembers = new ArrayList<TeamMember>();
		List<String> realUpNums = new ArrayList<String>();
		for(int i = 0; i < this.getUpNumbers().size(); ++i) {
			String realUpNum = (String) this.getUpNumbers().get(i).getSelectedItem();
			if(realUpNum.equals("")) {
				continue;
			}
			if(realUpNums.contains(realUpNum)) {
				Toast.makeText(SetSubstitutionActivity.this, "被换上队员号码：" + realUpNum + " 重复！！！", Toast.LENGTH_SHORT).show();
				return;
			}
			realUpNums.add(realUpNum);
			downTeamMembers.add(substitutionTeam.getTeamMemberByNum(Integer.parseInt((String) this.getDownNumbers().get(i).getText())));
			upTeamMembers.add(substitutionTeam.getTeamMemberByNum(Integer.parseInt(realUpNum)));
		}
		substitution.setDownTeamMemberList(downTeamMembers);
		substitution.setUpTeamMemberList(upTeamMembers);
		if(substitutionTeam.getId() == match.getTeamA().getId()) {
			// 检查是否符合排球换人规则
			if (!set.realAddSubstitutionTeamA(substitution)) {
				Toast.makeText(SetSubstitutionActivity.this, "换人不符合规则，本次换人失败！！！", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		else {
			// 检查是否符合排球换人规则
			if (!set.realAddSubstitutionTeamB(substitution)) {
				Toast.makeText(SetSubstitutionActivity.this, "换人不符合规则，本次换人失败！！！", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		
		match.setNowSet(set);
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
		
		// resultCode = 0
		this.setResult(0, intent);
		finish();
	}
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "SetSubstitutionActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "SetSubstitutionActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "SetSubstitutionActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "SetSubstitutionActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "SetSubstitutionActivity onDestroy() event");
	}

	public RadioGroup getSubstitutionGroup() {
		return SubstitutionGroup;
	}

	public void setSubstitutionGroup(RadioGroup substitutionGroup) {
		SubstitutionGroup = substitutionGroup;
	}

	public List<TextView> getDownNumbers() {
		return downNumbers;
	}

	public void setDownNumbers(List<TextView> downNumbers) {
		this.downNumbers = downNumbers;
	}

	public List<Spinner> getUpNumbers() {
		return upNumbers;
	}

	public void setUpNumbers(List<Spinner> upNumbers) {
		this.upNumbers = upNumbers;
	}

	public RadioButton getRadioAButton() {
		return radioAButton;
	}

	public void setRadioAButton(RadioButton radioAButton) {
		this.radioAButton = radioAButton;
	}

	public RadioButton getRadioBButton() {
		return radioBButton;
	}

	public void setRadioBButton(RadioButton radioBButton) {
		this.radioBButton = radioBButton;
	}

	public List<TeamMember> getTeamAPosition() {
		return teamAPosition;
	}

	public void setTeamAPosition(List<TeamMember> teamAPosition) {
		this.teamAPosition = teamAPosition;
	}

	public List<TeamMember> getTeamBPosition() {
		return teamBPosition;
	}

	public void setTeamBPosition(List<TeamMember> teamBPosition) {
		this.teamBPosition = teamBPosition;
	}
}
