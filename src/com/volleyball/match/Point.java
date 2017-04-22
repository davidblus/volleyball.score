package com.volleyball.match;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private Team team;//这一分的发球队伍
	private TeamMember teamMember;//这一分的发球队员
	private List<TeamMember> teamAPosition;//这一分发球时的A队位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	private List<TeamMember> teamBPosition;//这一分发球时的B队位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	private String resultStr;//发球方的得失分情况
	private int serverScore;//这一分判定结束后原发球队伍总分数
	private int clientScore;//这一分判定结束后原接球队伍总分数

	public Point() {
		// TODO Auto-generated constructor stub
	}
	
	public void addTeamAPosition(TeamMember teamMember) {
		List<TeamMember> teamAPosition = this.getTeamAPosition();
		if (teamAPosition == null) {
			teamAPosition = new ArrayList<TeamMember>();
		}
		teamAPosition.add(teamMember);
		this.setTeamAPosition(teamAPosition);
	}
	public void addTeamBPosition(TeamMember teamMember) {
		List<TeamMember> teamBPosition = this.getTeamBPosition();
		if (teamBPosition == null) {
			teamBPosition = new ArrayList<TeamMember>();
		}
		teamBPosition.add(teamMember);
		this.setTeamBPosition(teamBPosition);
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public TeamMember getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(TeamMember teamMember) {
		this.teamMember = teamMember;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public int getServerScore() {
		return serverScore;
	}

	public void setServerScore(int serverScore) {
		this.serverScore = serverScore;
	}

	public int getClientScore() {
		return clientScore;
	}

	public void setClientScore(int clientScore) {
		this.clientScore = clientScore;
	}

	public List<TeamMember> getTeamAPosition() {
		return teamAPosition;
	}

	public void setTeamAPosition(List<TeamMember> teamAPosition) {
		List<TeamMember> teamMemberList = new ArrayList<TeamMember>();
		for(TeamMember teamMember:teamAPosition) {
			teamMemberList.add(teamMember);
		}
		this.teamAPosition = teamMemberList;
	}

	public List<TeamMember> getTeamBPosition() {
		return teamBPosition;
	}

	public void setTeamBPosition(List<TeamMember> teamBPosition) {
		List<TeamMember> teamMemberList = new ArrayList<TeamMember>();
		for(TeamMember teamMember:teamBPosition) {
			teamMemberList.add(teamMember);
		}
		this.teamBPosition = teamMemberList;
	}

}
