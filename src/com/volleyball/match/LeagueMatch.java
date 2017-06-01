package com.volleyball.match;

import java.util.ArrayList;
import java.util.List;

public class LeagueMatch {
	private int id;
	private String name;
	private List<Team> teams;//队伍列表
	private int indexOfTeam;//当前正在处理的队伍索引，teams的索引
	private List<Match> matches;//场比赛 的列表
	private int indexOfMatch;//当前正在处理的场比赛索引，matches的索引

	//获取比赛列表中最晚结束比赛的match
	public static Match getLastMatchByEndDate(List<Match> matches) {
		Match retMatch = matches.get(0);
		for(Match match:matches) {
			if(match.getMatchEndDate().after(retMatch.getMatchEndDate())) {
				retMatch = match;
			}
		}
		return retMatch;
	}
	//获取比赛列表中非循环制列表
	public static List<Match> getMatchesNotRound(List<Match> matches) {
		List<Match> matchesNotRound = new ArrayList<Match>();
		for(Match match:matches) {
			if(!match.getCompetitionSystem().equals("循环制")) {
				matchesNotRound.add(match);
			}
		}
		return matchesNotRound;
	}
	//获取比赛列表中循环制列表
	public static List<Match> getMatchesRound(List<Match> matches) {
		List<Match> matchesRound = new ArrayList<Match>();
		for(Match match:matches) {
			if(match.getCompetitionSystem().equals("循环制")) {
				matchesRound.add(match);
			}
		}
		return matchesRound;
	}
	//根据传入的stage阶段来过滤比赛记录
	public static List<Match> getMatchesByStage(List<Match> matches, String stage) {
		List<Match> matchesStage = new ArrayList<Match>();
		for(Match match:matches) {
			if(match.getStage().equals(stage)) {
				matchesStage.add(match);
			}
		}
		return matchesStage;
	}
	//根据传入的group组别来过滤队伍列表
	public static List<Team> getTeamsByGroup(List<Team> teams, String group) {
		List<Team> teamsGroup = new ArrayList<Team>();
		for(Team team:teams) {
			if(team.getGroup().equals(group)) {
				teamsGroup.add(team);
			}
		}
		return teamsGroup;
	}
	//根据传入的sex（男/女）来过滤队伍列表
	public static List<Team> getTeamsBySex(List<Team> teams, String sex) {
		List<Team> teamsSex = new ArrayList<Team>();
		for(Team team:teams) {
			if(team.getName().contains(sex)) {
				teamsSex.add(team);
			}
		}
		return teamsSex;
	}
	public Match getMatchByTwoTeamName(String teamName1, String teamName2) {
		for(Match match:this.getMatches()) {
			if(match.getTeamA().getName().equals(teamName1) && match.getTeamB().getName().equals(teamName2)) {
				return match;
			}
			if(match.getTeamB().getName().equals(teamName1) && match.getTeamA().getName().equals(teamName2)) {
				return match;
			}
		}
		return null;
	}
	public List<Match> getMatchesOfTeam(Team team) {
		//得到某个队伍的所有比赛记录
		List<Match> matches = new ArrayList<Match>();
		for(Match match:this.getMatches()) {
			if(match.getTeamByName(team.getName()) != null) {
				matches.add(match);
			}
		}
		return matches;
	}
	public LeagueMatch() {
		this.id = -1;
		this.name = "";
	}
	public LeagueMatch(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public LeagueMatch(LeagueMatch leagueMatch) {
		this.id = leagueMatch.id;
		this.name = leagueMatch.name;
		this.teams = leagueMatch.teams;
		this.matches = leagueMatch.matches;
	}
	public LeagueMatch getLeagueMatch() {
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int generateUniqueTeamId() {
		int max = 0;
		try {
			for (Team team:this.getTeams()) {
				if (max < team.getId()) {
					max = team.getId();
				}
			}
		} catch (Exception e) {
			// this.getTeams() == null
			e.printStackTrace();
		}
		return (max + 1);
	}
	public int generateUniqueMatchId() {
		int max = 0;
		try {
			for (Match match:this.getMatches()) {
				if (max < match.getId()) {
					max = match.getId();
				}
			}
		} catch (Exception e) {
			// this.getMatches() == null
			e.printStackTrace();
		}
		return (max + 1);
	}
	public boolean isTeamNameExist(String name) {
		try {
			for (Team team:this.getTeams()) {
				if (name.equals(team.getName())) {
					return true;
				}
			}
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public boolean isMatchNameExist(String name) {
		for (Match match:this.getMatches()) {
			if (name.equals(match.getName())) {
				return true;
			}
		}
		return false;
	}
	public boolean isMatchStageNameExist(String stageName) {
		for (Match match:this.getMatches()) {
			if (stageName.equals(match.getStageName())) {
				return true;
			}
		}
		return false;
	}
	public int getIndexOfTeamByName(String name) {
		int result = -1;
		for (int i = 0; i < this.getTeams().size(); i++) {
			if (name.equals(this.getTeams().get(i).getName())) {
				return i;
			}
		}
		return result;
	}
	public int getIndexOfMatchByStageName(String name) {
		int result = -1;
		for (int i = 0; i < this.getMatches().size(); i++) {
			if (this.getMatches().get(i).getStageName().equals(name)) {
				return i;
			}
		}
		return result;
	}
	public int getIndexOfMatchById(int id) {
		int result = -1;
		for (int i = 0; i < this.getMatches().size(); i++) {
			if (this.getMatches().get(i).getId() == id) {
				return i;
			}
		}
		return result;
	}
	public int getIndexOfMatchByName(String name) {
		int result = -1;
		for (int i = 0; i < this.getMatches().size(); i++) {
			if (name.equals(this.getMatches().get(i).getName())) {
				return i;
			}
		}
		return result;
	}
	public Team getTeamById(int id) {
		for (Team team:this.getTeams()) {
			if (team.getId() == id) {
				return team;
			}
		}
		return null;
	}
	public Team getTeamByName(String name) {
		for (Team team:this.getTeams()) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}
	public Match getMatchByName(String name) {
		for (Match match:this.getMatches()) {
			if (match.getName().equals(name)) {
				return match;
			}
		}
		return null;
	}
	public Match getMatchByStageName(String name) {
		for (Match match:this.getMatches()) {
			if (match.getStageName().equals(name)) {
				return match;
			}
		}
		return null;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public boolean addTeam(Team team) {
		List<Team> teams = this.getTeams();
		teams.add(team);
		this.setTeams(teams);
		return true;
	}
	public boolean addMatch(Match match) {
		List<Match> matches = this.getMatches();
		if (matches == null) {
			matches = new ArrayList<Match>();
		}
		matches.add(match);
		this.setMatches(matches);
		return true;
	}
	public boolean delTeam(Team team) {
		List<Team> teams = this.getTeams();
		teams.remove(team);
		this.setTeams(teams);
		return true;
	}
	public boolean delMatch(Match match) {
		List<Match> matches = this.getMatches();
		matches.remove(match);
		this.setMatches(matches);
		return true;
	}
	public Team getNowTeam() {
		return this.getTeams().get(this.getIndexOfTeam());
	}
	public Match getNowMatch() {
		return this.getMatches().get(this.getIndexOfMatch());
	}
	public void setNowTeam(Team team) {
		List<Team> teams = this.getTeams();
		teams.set(this.getIndexOfTeam(), team);
		this.setTeams(teams);
	}
	public void setNowMatch(Match match) {
		List<Match> matches = this.getMatches();
		matches.set(this.getIndexOfMatch(), match);
		this.setMatches(matches);
	}
	public int getIndexOfTeam() {
		return indexOfTeam;
	}
	public void setIndexOfTeam(int indexOfTeam) {
		this.indexOfTeam = indexOfTeam;
	}
	public String toString() {
		return "LeagueMatch \nid = " + this.id + "\nname = " + this.name + "\n";
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	public int getIndexOfMatch() {
		return indexOfMatch;
	}
	public void setIndexOfMatch(int indexOfMatch) {
		this.indexOfMatch = indexOfMatch;
	}
}
