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
		for (Team team:this.getTeams()) {
			if (max < team.getId()) {
				max = team.getId();
			}
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
		for (Team team:this.getTeams()) {
			if (name.equals(team.getName())) {
				return true;
			}
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
