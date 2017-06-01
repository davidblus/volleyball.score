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
				// �����ʾ�����޸Ķ�����Ϣ
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
				// ��ת����������activity
				Intent i = new Intent(LeagueMatchHomePageActivity.this, CreateTeamActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("name", teamName);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		// ���ó���ɾ������
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
				// ���ö����Ƿ���ڱ�����¼����������������û�����ɾ��������
				if (leagueMatch.getMatchesOfTeam(team).size() > 0) {
					Toast.makeText(LeagueMatchHomePageActivity.this, "���ڸö��������¼���޷�ɾ�����飡����", Toast.LENGTH_LONG).show();
					return true;
				}
				new AlertDialog.Builder(view.getContext()).setTitle("ɾ������")
				.setMessage("ȷ��ɾ����" + teamName + " ?")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						leagueMatch.delTeam(team);
						app.setNowLeagueMatch(leagueMatch);
						app.saveLeagueMatchToXml();
						updateData();
					}
				}).setNegativeButton("����", new DialogInterface.OnClickListener() {
					
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
		startActivityForResult(Intent.createChooser(intent, "��ѡ���ļ���"), GlobalConstant.FILE_SELECT_CODE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ����xml�ļ����������Ʊ�����ͬ�����Զ���������ͬ�����ݡ�
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
					Toast.makeText(LeagueMatchHomePageActivity.this, "�������Ʋ�ͬ���������ʧ�ܣ�����", Toast.LENGTH_LONG).show();
					
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
					Toast.makeText(LeagueMatchHomePageActivity.this, "����������ݳɹ�������", Toast.LENGTH_LONG).show();
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
		// ��ת����������activity
		System.out.println("Hit btnCreateTeam");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, CreateTeamActivity.class);
		startActivity(i);
	}
	public void startMatch(View view) {
		// ��� ��ʼ���� ��ť
		String nameTeamA = (String) this.getSpinnerChooseA().getSelectedItem();
		String nameTeamB = (String) this.getSpinnerChooseB().getSelectedItem();
		if(nameTeamA.equals(nameTeamB)) {
			Toast.makeText(LeagueMatchHomePageActivity.this, "A��B�Ӳ�����һ֧���飡����", Toast.LENGTH_LONG).show();
			return;
		}
		System.out.println("nameTeamA:" + nameTeamA + " nameTeamB:" + nameTeamB);

		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		// �����֧�����Ƿ�����ͬһ��group��������ͬ���ܱ���������
		if(!leagueMatch.getTeamByName(nameTeamA).getGroup().equals(leagueMatch.getTeamByName(nameTeamB).getGroup())) {
			Toast.makeText(LeagueMatchHomePageActivity.this, "A��B�����ͬ���޷�����������", Toast.LENGTH_LONG).show();
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
		
		// ����id����index����Ϊ�������������������
		int index = leagueMatch.getIndexOfMatchById(id);
		leagueMatch.setIndexOfMatch(index);
		
		app.setNowLeagueMatch(leagueMatch);
		// ��ת�� ��д������Ϣҳ��
		System.out.println("Test!!!");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, FillOutMatchInfomationActivity.class);
		startActivity(i);
		
	}
	public void showMatchList(View view) {
		// ��� �����б� ��ť
		// ��ת��������¼activity
		System.out.println("Hit showMatchList");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, MatchListActivity.class);
		startActivity(i);
	}
	public void showTeamRank(View view) {
		// �������������ť����ת����������activity
		System.out.println("Hit showTeamRank");
		Intent i = new Intent(LeagueMatchHomePageActivity.this, TeamRankActivity.class);
		startActivity(i);
	}
	/** ����activity������ʽΪsingleTaskʱ�����ڸ���intent��*/
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
