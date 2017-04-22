package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Team;
import com.volleyball.match.TeamMember;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTeamActivity extends Activity {
	private String msg = GlobalConstant.msg;
	
	private EditText teamName;
	private List<EditText> normalTeamMembersNumbers;
	private List<EditText> normalTeamMembersNames;
	private List<EditText> liberoTeamMembersNumbers;
	private List<EditText> liberoTeamMembersNames;
	private EditText captainName;
	private EditText coachName;
	
	private boolean modify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "CreateTeamActivity onStart() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		
		this.setTeamName((EditText) findViewById(R.id.teamName));
		
		List<EditText> normalTeamMembersNumbers = new ArrayList<EditText>();
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber1));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber2));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber3));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber4));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber5));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber6));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber7));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber8));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber9));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber10));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber11));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber12));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber13));
		normalTeamMembersNumbers.add((EditText) findViewById(R.id.normalTeamMembersNumber14));
		this.setNormalTeamMembersNumbers(normalTeamMembersNumbers);

		List<EditText> normalTeamMembersNames = new ArrayList<EditText>();
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName1));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName2));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName3));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName4));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName5));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName6));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName7));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName8));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName9));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName10));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName11));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName12));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName13));
		normalTeamMembersNames.add((EditText) findViewById(R.id.normalTeamMembersName14));
		this.setNormalTeamMembersNames(normalTeamMembersNames);
		
		List<EditText> liberoTeamMembersNumbers = new ArrayList<EditText>();
		liberoTeamMembersNumbers.add((EditText) findViewById(R.id.liberoTeamMembersNumber1));
		liberoTeamMembersNumbers.add((EditText) findViewById(R.id.liberoTeamMembersNumber2));
		this.setLiberoTeamMembersNumbers(liberoTeamMembersNumbers);

		List<EditText> liberoTeamMembersNames = new ArrayList<EditText>();
		liberoTeamMembersNames.add((EditText) findViewById(R.id.liberoTeamMembersName1));
		liberoTeamMembersNames.add((EditText) findViewById(R.id.liberoTeamMembersName2));
		this.setLiberoTeamMembersNames(liberoTeamMembersNames);
		
		this.setCaptainName((EditText) findViewById(R.id.captainName));
		this.setCoachName((EditText) findViewById(R.id.coachName));
		
		try {
			Bundle bundle = this.getIntent().getExtras();
			String name = bundle.getString("name");
			System.out.println("Param: name=" + name);
			this.setModify(true);
			
			DataApplication app = (DataApplication) getApplication();
			LeagueMatch leagueMatch = app.getNowLeagueMatch();
			Team team = leagueMatch.getTeamByName(name);
			this.getTeamName().setText(team.getName());
			for (int i = 0; i < team.getNormalTeamMembers().size(); i++) {
				this.getNormalTeamMembersNumbers().get(i).setText(team.getNormalTeamMembers().get(i).getNumber() + "");
				this.getNormalTeamMembersNames().get(i).setText(team.getNormalTeamMembers().get(i).getName());
			}
			for (int i = 0; i < team.getLiberoTeamMembers().size(); i++) {
				this.getLiberoTeamMembersNumbers().get(i).setText(team.getLiberoTeamMembers().get(i).getNumber() + "");
				this.getLiberoTeamMembersNames().get(i).setText(team.getLiberoTeamMembers().get(i).getName());
			}
			this.getCaptainName().setText(team.getCaptainName());
			this.getCoachName().setText(team.getCoachName());
			
		} catch (Exception e) {
			System.out.println("No params!!!");
			this.setModify(false);
		}
	}
	
	public void createTeamDetermine(View view) {
		EditText teamName = this.getTeamName();
		List<EditText> normalTeamMembersNumbers = this.getNormalTeamMembersNumbers();
		List<EditText> normalTeamMembersNames = this.getNormalTeamMembersNames();
		List<EditText> liberoTeamMembersNumbers = this.getLiberoTeamMembersNumbers();
		List<EditText> liberoTeamMembersNames = this.getLiberoTeamMembersNames();
		EditText captainName = this.getCaptainName();
		EditText coachName = this.getCoachName();
		
		String teamNameStr = teamName.getText().toString();
		if (teamNameStr.equals("")) {
			Toast.makeText(CreateTeamActivity.this, "请输入队伍名称！！！", Toast.LENGTH_LONG).show();
			return;
		}
		String captainNameStr = captainName.getText().toString();
		if (captainNameStr.equals("")) {
			Toast.makeText(CreateTeamActivity.this, "请输入队长姓名！！！", Toast.LENGTH_LONG).show();
			return;
		}
		String coachNameStr = coachName.getText().toString();
		if (coachNameStr.equals("")) {
			Toast.makeText(CreateTeamActivity.this, "请输入教练姓名！！！", Toast.LENGTH_LONG).show();
			return;
		}
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		if (leagueMatch.isTeamNameExist(teamNameStr) && !this.isModify()) {
			Toast.makeText(CreateTeamActivity.this, "该队伍名称已存在，请重新输入！！！", Toast.LENGTH_LONG).show();
			return;
		}
		Team team = new Team();
		team.setId(leagueMatch.generateUniqueTeamId());
		team.setName(teamNameStr);
		team.setCaptainName(captainNameStr);
		team.setCoachName(coachNameStr);
		
		List<TeamMember> normalTeamMembers = new ArrayList<TeamMember>();
		for (int i = 0; i < 14; i++) {
			String normalTeamMembersNumbersStr = normalTeamMembersNumbers.get(i).getText().toString();
			String normalTeamMembersNamesStr = normalTeamMembersNames.get(i).getText().toString();
			if (normalTeamMembersNumbersStr.equals("") || normalTeamMembersNamesStr.equals("")) {
				continue;
			}
			TeamMember teamMember = new TeamMember(Integer.parseInt(normalTeamMembersNumbersStr), normalTeamMembersNamesStr);
			normalTeamMembers.add(teamMember);
		}
		List<TeamMember> liberoTeamMembers = new ArrayList<TeamMember>();
		for (int i = 0; i < 2; i++) {
			String liberoTeamMembersNumbersStr = liberoTeamMembersNumbers.get(i).getText().toString();
			String liberoTeamMembersNamesStr = liberoTeamMembersNames.get(i).getText().toString();
			if (liberoTeamMembersNumbersStr.equals("") || liberoTeamMembersNamesStr.equals("")) {
				continue;
			}
			TeamMember teamMember = new TeamMember(Integer.parseInt(liberoTeamMembersNumbersStr), liberoTeamMembersNamesStr);
			liberoTeamMembers.add(teamMember);
		}
		
		team.setNormalTeamMembers(normalTeamMembers);
		team.setLiberoTeamMembers(liberoTeamMembers);
		
		if (this.isModify()) {
			leagueMatch.setNowTeam(team);
		}
		else {
			leagueMatch.addTeam(team);
		}
		app.setNowLeagueMatch(leagueMatch);
		
		// 把新的队伍数据写入xml文件
		if (!app.saveLeagueMatchToXml()) {
			if (this.isModify()) {
				Toast.makeText(CreateTeamActivity.this, "修改队伍失败！！！", Toast.LENGTH_LONG).show();				
			}
			else {
				Toast.makeText(CreateTeamActivity.this, "创建队伍失败！！！", Toast.LENGTH_LONG).show();
			}
			return;
		}
		if (this.isModify()) {
			Toast.makeText(CreateTeamActivity.this, "修改队伍成功！！！", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(CreateTeamActivity.this, "创建队伍成功！！！", Toast.LENGTH_LONG).show();			
		}
		
		Intent i = new Intent(CreateTeamActivity.this, LeagueMatchHomePageActivity.class);
		startActivity(i);
		finish();
	}
	
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "CreateTeamActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "CreateTeamActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "CreateTeamActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "CreateTeamActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "CreateTeamActivity onDestroy() event");
	}

	public EditText getTeamName() {
		return teamName;
	}

	public void setTeamName(EditText teamName) {
		this.teamName = teamName;
	}

	public List<EditText> getNormalTeamMembersNumbers() {
		return normalTeamMembersNumbers;
	}

	public void setNormalTeamMembersNumbers(List<EditText> normalTeamMembersNumbers) {
		this.normalTeamMembersNumbers = normalTeamMembersNumbers;
	}

	public List<EditText> getNormalTeamMembersNames() {
		return normalTeamMembersNames;
	}

	public void setNormalTeamMembersNames(List<EditText> normalTeamMembersNames) {
		this.normalTeamMembersNames = normalTeamMembersNames;
	}

	public List<EditText> getLiberoTeamMembersNumbers() {
		return liberoTeamMembersNumbers;
	}

	public void setLiberoTeamMembersNumbers(List<EditText> liberoTeamMembersNumbers) {
		this.liberoTeamMembersNumbers = liberoTeamMembersNumbers;
	}

	public List<EditText> getLiberoTeamMembersNames() {
		return liberoTeamMembersNames;
	}

	public void setLiberoTeamMembersNames(List<EditText> liberoTeamMembersNames) {
		this.liberoTeamMembersNames = liberoTeamMembersNames;
	}

	public EditText getCaptainName() {
		return captainName;
	}

	public void setCaptainName(EditText captainName) {
		this.captainName = captainName;
	}

	public EditText getCoachName() {
		return coachName;
	}

	public void setCoachName(EditText coachName) {
		this.coachName = coachName;
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

}
