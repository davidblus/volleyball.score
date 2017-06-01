package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Team;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class TeamStageRankActivity extends Activity {
	
	private String msg = GlobalConstant.msg;
	private String stage;
	
	private Spinner chooseSex;
	private Spinner chooseGroup;
	private ListView teamStageRankListView;

	public TeamStageRankActivity() {
		// 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "TeamStageRankActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_stage_rank);
		
		this.setChooseSex((Spinner) findViewById(R.id.chooseSex));
		this.setChooseGroup((Spinner) findViewById(R.id.chooseGroup));
		this.setTeamStageRankListView((ListView) findViewById(R.id.teamStageRankListView));
		
		//获取传入stage数据
		try {
			Bundle bundle = this.getIntent().getExtras();
			String stage = bundle.getString("stage");
			System.out.println("Param: stage=" + stage);
			this.setStage(stage);
		} catch (Exception e) {
			System.out.println("No params!!!");
			finish();
		}

		this.updateView();
		
		// 设置spinner监听事件，当spinner发生改变之后要更新listview的数据
		this.getChooseSex().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 
				System.out.println("Hit chooseSex spinner onItemSelected!!!");
				updateListView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// 
				System.out.println("Hit chooseSex spinner onNothingSelected!!!");
				
			}
		});
		this.getChooseGroup().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 
				System.out.println("Hit chooseGroup spinner onItemSelected!!!");
				updateListView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// 
				System.out.println("Hit chooseGroup spinner onNothingSelected!!!");
			}
		});
	}
	
	private void updateView() {
		// 
		ArrayAdapter<String> arrayAdapterChooseSex = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getSexData());
		ArrayAdapter<String> arrayAdapterChooseGroup = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getGroupData());
		this.getChooseSex().setAdapter(arrayAdapterChooseSex);
		this.getChooseGroup().setAdapter(arrayAdapterChooseGroup);

		this.updateListView();
	}

	private List<String> calRankString(List<Team> rankTeams, final LeagueMatch leagueMatch) {
		// 
		List<String> rankString = new ArrayList<String>();
		// 先考虑非循环赛
		List<String> notRoundRankString = new ArrayList<String>();
		for(Team team:rankTeams) {
			List<Match> notRoundMatches = LeagueMatch.getMatchesNotRound(LeagueMatch.getMatchesByStage(leagueMatch.getMatchesOfTeam(team), this.getStage()));
			if(notRoundMatches.isEmpty()) {
				break;
			}
			Match lastMatch = LeagueMatch.getLastMatchByEndDate(notRoundMatches);
			int rankTeam;
			if(lastMatch.getTeamA().getId() == team.getId()) {
				rankTeam = lastMatch.getRankingTeamA();
			}
			else {
				rankTeam = lastMatch.getRankingTeamB();
			}
			String showRankString = team.getName() + " " + lastMatch.getCompetitionSystem() + " 名次：" + rankTeam;
			notRoundRankString.add(showRankString);
		}
		Collections.sort(notRoundRankString, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// 
				int io1 = Integer.parseInt(o1.split(" 名次：")[1]);
				int io2 = Integer.parseInt(o2.split(" 名次：")[1]);
				return io1 - io2;
			}
		});
		rankString.addAll(notRoundRankString);
		
		// 考虑循环赛
		List<String> roundRankString = new ArrayList<String>();
		List<Map<String, String>> roundMatchData = new ArrayList<Map<String,String>>();
		for(Team team:rankTeams) {
			Map<String, String> roundMatch = new HashMap<String, String>();
			List<Match> roundMatches = LeagueMatch.getMatchesRound(LeagueMatch.getMatchesByStage(leagueMatch.getMatchesOfTeam(team), this.getStage()));
			if(roundMatches.isEmpty()) {
				break;
			}
			
			roundMatch.put("teamName", team.getName());
			int integrating = 0;//总积分
			int winCount = 0;//总胜局次数
			int failCount = 0;//总负局次数
			int winScore = 0;//总得分
			int failScore = 0;//总失分
			for(Match match:roundMatches) {
				if(team.getId() == match.getTeamA().getId()) {
					integrating += match.getIntegratingTeamA();
					winScore += match.getTotalScoreTeamA();
					failScore += match.getTotalScoreTeamB();
				}
				else {
					integrating += match.getIntegratingTeamB();
					winScore += match.getTotalScoreTeamB();
					failScore += match.getTotalScoreTeamA();
				}
				if(team.getId() == match.getWinTeam().getId()) {
					winCount += 3;
					failCount += match.getFailedSetTime();
				}
				else {
					winCount += match.getFailedSetTime();
					failCount += 3;
				}
			}
			roundMatch.put("integrating", "" + integrating);
			double valueC = (double)winCount / failCount;
			double valueZ = (double)winScore / failScore;
			roundMatch.put("valueC", String.format("%.2f", valueC));
			roundMatch.put("valueZ", String.format("%.2f", valueZ));
			roundMatchData.add(roundMatch);			
		}
		Collections.sort(roundMatchData, new Comparator<Map<String, String>>() {

			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				// 
				int integrating1 = Integer.parseInt(o1.get("integrating"));
				int integrating2 = Integer.parseInt(o2.get("integrating"));
				double valueC1 = Double.parseDouble(o1.get("valueC"));
				double valueC2 = Double.parseDouble(o2.get("valueC"));
				double valueZ1 = Double.parseDouble(o1.get("valueZ"));
				double valueZ2 = Double.parseDouble(o2.get("valueZ"));
				if(integrating1 > integrating2) {
					return -1;
				}
				else if (integrating1 < integrating2) {
					return 1;
				}
				else {
					if(valueC1 > valueC2) {
						return -1;
					}
					else if (valueC1 < valueC2) {
						return 1;
					}
					else {
						if(valueZ1 > valueZ2) {
							return -1;
						}
						else if (valueZ1 < valueZ2) {
							return 1;
						}
						else {
							// 考虑极端情况，只能根据两支队伍的胜负关系来判断
							String teamName1 = o1.get("teamName");
							String teamName2 = o2.get("teamName");
							Match match = leagueMatch.getMatchByTwoTeamName(teamName1, teamName2);
							try {
								if(match.getWinTeam().getName().equals(teamName1)) {
									return -1;
								}
								else {
									return 1;
								}
							} catch (Exception e) {
								// 
								e.printStackTrace();
								System.out.println("这两队还未比赛！" + teamName1 + ":" + teamName2);
								return 0;
							}
						}
					}
				}
			}
		});
		for(int i = 0; i < roundMatchData.size(); ++i) {
			String showRankString = roundMatchData.get(i).get("teamName") + " 循环制" + " 名次：" + (i + 1) + "，积分：" + roundMatchData.get(i).get("integrating");
			System.out.println("showRoundRankString:" + showRankString);
			roundRankString.add(showRankString);
		}
		rankString.addAll(roundRankString);
		
		return rankString;
	}
	private void updateListView() {
		String choosedSex = (String) this.getChooseSex().getSelectedItem();
		String choosedGroup = (String) this.getChooseGroup().getSelectedItem();
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		
		List<Team> rankTeams = leagueMatch.getTeams();
		System.out.println("List rankTeams size():" + rankTeams.size());
		if(choosedSex.equals("男队"))
			rankTeams = LeagueMatch.getTeamsBySex(rankTeams, "男");
		else if (choosedSex.equals("女队")) {
			rankTeams = LeagueMatch.getTeamsBySex(rankTeams, "女");
		}
		if(!choosedGroup.equals("全部")) {
			rankTeams = LeagueMatch.getTeamsByGroup(rankTeams, choosedGroup);
		}
		System.out.println("List filtered rankTeams size():" + rankTeams.size());
		List<String> rankString = this.calRankString(rankTeams, leagueMatch);
		System.out.println("List rankString size():" + rankString.size());
		this.getTeamStageRankListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, rankString));
	}

	private List<String> getGroupData() {
		// 通过读取队伍的所有group组来获取数据
		List<String> data = new ArrayList<String>();
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		if (leagueMatch.getTeams() != null) {
			for (Team team:leagueMatch.getTeams()) {
				if (!data.contains(team.getGroup())) {
					data.add(team.getGroup());
				}
			}
		}
		data.add("全部");
		return data;
	}

	private List<String> getSexData() {
		// 
		List<String> data = new ArrayList<String>();
		data.add("男队");
		data.add("女队");
		data.add("全部");
		return data;
	}

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "TeamStageRankActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "TeamStageRankActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "TeamStageRankActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "TeamStageRankActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "TeamStageRankActivity onDestroy() event");
	}

	public Spinner getChooseSex() {
		return chooseSex;
	}

	public void setChooseSex(Spinner chooseSex) {
		this.chooseSex = chooseSex;
	}

	public Spinner getChooseGroup() {
		return chooseGroup;
	}

	public void setChooseGroup(Spinner chooseGroup) {
		this.chooseGroup = chooseGroup;
	}

	public ListView getTeamStageRankListView() {
		return teamStageRankListView;
	}

	public void setTeamStageRankListView(ListView teamStageRankListView) {
		this.teamStageRankListView = teamStageRankListView;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

}
