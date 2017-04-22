package com.volleyball.match;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private Team team;//��һ�ֵķ������
	private TeamMember teamMember;//��һ�ֵķ����Ա
	private List<TeamMember> teamAPosition;//��һ�ַ���ʱ��A��λ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	private List<TeamMember> teamBPosition;//��һ�ַ���ʱ��B��λ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	private String resultStr;//���򷽵ĵ�ʧ�����
	private int serverScore;//��һ���ж�������ԭ��������ܷ���
	private int clientScore;//��һ���ж�������ԭ��������ܷ���

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
