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
import android.widget.Spinner;
import android.widget.Toast;

public class LeagueMatchHomePageActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private ListView teamList;
	private Spinner spinnerChooseA;
	private Spinner spinnerChooseB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(msg, "LeagueMatchHomePageActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league_match_home_page);
		this.setTeamList((ListView) this.findViewById(R.id.teamListView));
		this.setSpinnerChooseA((Spinner) findViewById(R.id.spinnerChooseA));
		this.setSpinnerChooseB((Spinner) findViewById(R.id.spinnerChooseB));
		this.updateData();
		this.getTeamList().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 点击显示或者修改队伍信息
				System.out.println("Hit teamListView, parent:" + parent.toString() + " view:" + view.toString() + " position:" + position + " id:" + id);
				String teamName = (String) parent.getItemAtPosition(position);
				DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				int index = leagueMatch.getIndexOfTeamByName(teamName);
				if (index == -1) {
					return;
				}
				leagueMatch.setIndexOfTeam(index);
				app.setNowLeagueMatch(leagueMatch);
				// 跳转到创建队伍activity
				Intent i = new Intent(LeagueMatchHomePageActivity.this, CreateTeamActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("name", teamName);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		// 设置长按删除功能
		this.getTeamList().setLongClickable(true);
		this.getTeamList().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				String teamName = (String) parent.getItemAtPosition(position);
				final DataApplication app = (DataApplication) getApplication();
				final LeagueMatch leagueMatch = app.getNowLeagueMatch();
				final Team team = leagueMatch.getTeamByName(teamName);
				if (team == null) {
					System.out.println("Can not find teamName:" + teamName);
					return true;
				}
				// 检查该队伍是否存在比赛记录，如果存在则提醒用户不能删除！！！
				if (leagueMatch.getMatchesOfTeam(team).size() > 0) {
					Toast.makeText(LeagueMatchHomePageActivity.this, "存在该队伍比赛记录，无法删除队伍！！！", Toast.LENGTH_LONG).show();
					return true;
				}
				new AlertDialog.Builder(view.getContext()).setTitle("删除队伍")
				.setMessage("确认删除：" + teamName + " ?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						leagueMatch.delTeam(team);
						app.setNowLeagueMatch(leagueMatch);
						app.saveLeagueMatchToXml();
						updateData();
					}
				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
				
				return true;
			}
		});
		
	}
	
	private List<String> getTeamData() {
		final DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		List<String> data = new ArrayList<String>();
		if (leagueMatch.getTeams() != null) {
			for (Team team:leagueMatch.getTeams()) {
				data.add(team.getName());
			}
		}
		return data;
	}
	public void updateData() {
		this.getTeamList().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getTeamData()));
		ArrayAdapter<String> arrayAdapterChooseA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getTeamData());
		ArrayAdapter<String> arrayAdapterChooseB = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getTeamData());
		this.getSpinnerChooseA().setAdapter(arrayAdapterChooseA);
		this.getSpinnerChooseB().setAdapter(arrayAdapterChooseB);
	}
	public void importTeam(View view) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(Intent.createChooser(intent, "请选择文件："), GlobalConstant.FILE_SELECT_CODE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 导入xml文件，比赛名称必须相同，忽略队伍名称相同的数据。
		Log.d(msg, "LeagueMatchHomePageActivity onActivityResult() event");
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			System.out.println("uri:" + uri.toString());
			String absoluteFileName = GetPathFromUri4kitkat.getPath(this, uri);
			System.out.println("absoluteFileName:" + absoluteFileName);
			try {
				FileInputStream fis = new FileInputStream(absoluteFileName);
				DomLeagueMatchParser parser = new DomLeagueMatchParser();
				List<LeagueMatch> importLeagueMatches = parser.parse(fis);
				LeagueMatch importLeagueMatch = importLeagueMatches.get(0);
				final DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				if (!importLeagueMatch.getName().equals(leagueMatch.getName())) {
					Toast.makeText(LeagueMatchHomePageActivity.this, "比赛名称不同，导入队伍失败！！！", Toast.LENGTH_LONG).show();
					
				}
				else {
					for (Team team:importLeagueMatch.getTeams()) {
						if (leagueMatch.isTeamNameExist(team.getName())) {
							continue;
						}
						team.setId(leagueMatch.generateUniqueTeamId());
						leagueMatch.addTeam(team);
					}
					app.setNowLeagueMatch(leagueMatch);
					app.saveLeagueMatchToXml();
					Toast.makeText(LeagueMatchHomePageActivity.this, "导入队伍数据成功！！！", Toast.LENGTH_LONG).show();
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
	public void exportTeam(View view) {
		Intent i = new Intent(LeagueMatchHomePageActivity.this, ExportTeamActivity.class);
		startActivity(i);
	}
	public void createTeam(View view) {
		// 跳转到创建队伍activity
		System.out.println("Hit btnCreateTeam");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, CreateTeamActivity.class);
		startActivity(i);
	}
	public void startMatch(View view) {
		// 点击 开始比赛 按钮
		String nameTeamA = (String) this.getSpinnerChooseA().getSelectedItem();
		String nameTeamB = (String) this.getSpinnerChooseB().getSelectedItem();
		if(nameTeamA.equals(nameTeamB)) {
			Toast.makeText(LeagueMatchHomePageActivity.this, "A队B队不能是一支队伍！！！", Toast.LENGTH_LONG).show();
			return;
		}
		System.out.println("nameTeamA:" + nameTeamA + " nameTeamB:" + nameTeamB);

		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		// 检查两支队伍是否属于同一组group，如果组别不同则不能比赛！！！
		if(!leagueMatch.getTeamByName(nameTeamA).getGroup().equals(leagueMatch.getTeamByName(nameTeamB).getGroup())) {
			Toast.makeText(LeagueMatchHomePageActivity.this, "A队B队组别不同，无法比赛！！！", Toast.LENGTH_LONG).show();
			return;
		}
		
		Match match = new Match();
		
		int id = leagueMatch.generateUniqueMatchId();
		String name = nameTeamA + "VS" + nameTeamB;
		match.setId(id);
		match.setName(name);
		match.setTeamA(leagueMatch.getTeamByName(nameTeamA));
		System.out.println("teamA.normalSize():" + match.getTeamA().getNormalTeamMembers().size());
		match.setTeamB(leagueMatch.getTeamByName(nameTeamB));
		System.out.println("teamB.normalSize():" + match.getTeamB().getNormalTeamMembers().size());
		leagueMatch.addMatch(match);
		
		// 根据id来找index，因为可能有重名比赛的情况
		int index = leagueMatch.getIndexOfMatchById(id);
		leagueMatch.setIndexOfMatch(index);
		
		app.setNowLeagueMatch(leagueMatch);
		// 跳转到 填写比赛信息页面
		System.out.println("Test!!!");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, FillOutMatchInfomationActivity.class);
		startActivity(i);
		
	}
	public void showMatchList(View view) {
		// 点击 比赛列表 按钮
		// 跳转到比赛记录activity
		System.out.println("Hit showMatchList");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, MatchListActivity.class);
		startActivity(i);
	}
	public void showTeamRank(View view) {
		// 点击队伍排名按钮，跳转到队伍排名activity
		System.out.println("Hit showTeamRank");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, TeamRankActivity.class);
		startActivity(i);
	}
	/** 当此activity启动方式为singleTask时，用于更新intent。*/
	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(msg, "LeagueMatchHomePageActivity onNewIntent() event");
		super.onNewIntent(intent);
		setIntent(intent);
		this.updateData();
	}
	
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		this.updateData();
		super.onStart();
		Log.d(msg, "LeagueMatchHomePageActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "LeagueMatchHomePageActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "LeagueMatchHomePageActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "LeagueMatchHomePageActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "LeagueMatchHomePageActivity onDestroy() event");
	}

	public ListView getTeamList() {
		return teamList;
	}

	public void setTeamList(ListView teamList) {
		this.teamList = teamList;
	}

	public Spinner getSpinnerChooseA() {
		return spinnerChooseA;
	}

	public void setSpinnerChooseA(Spinner spinnerChooseA) {
		this.spinnerChooseA = spinnerChooseA;
	}

	public Spinner getSpinnerChooseB() {
		return spinnerChooseB;
	}

	public void setSpinnerChooseB(Spinner spinnerChooseB) {
		this.spinnerChooseB = spinnerChooseB;
	}


}
