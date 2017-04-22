package com.volleyball.match;

import java.util.ArrayList;
import java.util.List;

public class Substitution {

	private int initiatorScore;//发起换人队伍的分数
	private int receiverScore;//接受换人队伍的分数

	//注意！被换上队员列表和被换下队员列表是一一对应的，而且一局比赛中，每个队员可以上一次、下一次
	private List<TeamMember> downTeamMemberList;//被换下队员列表
	private List<TeamMember> upTeamMemberList;//被换上队员列表
	
	public void addDownTeamMemberList(TeamMember teamMember) {
		List<TeamMember> downTeamMemberList = this.getDownTeamMemberList();
		if (downTeamMemberList == null) {
			downTeamMemberList = new ArrayList<TeamMember>();
		}
		downTeamMemberList.add(teamMember);
		this.setDownTeamMemberList(downTeamMemberList);
	}
	
	public void addUpTeamMemberList(TeamMember teamMember) {
		List<TeamMember> upTeamMemberList = this.getUpTeamMemberList();
		if (upTeamMemberList == null) {
			upTeamMemberList = new ArrayList<TeamMember>();
		}
		upTeamMemberList.add(teamMember);
		this.setUpTeamMemberList(upTeamMemberList);
	}
	
	public Substitution(int initiatorScore, int receiverScore) {
		this.initiatorScore = initiatorScore;
		this.receiverScore = receiverScore;
	}

	public Substitution() {
		// 
	}

	public int getInitiatorScore() {
		return initiatorScore;
	}

	public void setInitiatorScore(int initiatorScore) {
		this.initiatorScore = initiatorScore;
	}

	public int getReceiverScore() {
		return receiverScore;
	}

	public void setReceiverScore(int receiverScore) {
		this.receiverScore = receiverScore;
	}

	public List<TeamMember> getDownTeamMemberList() {
		return downTeamMemberList;
	}

	public void setDownTeamMemberList(List<TeamMember> downTeamMemberList) {
		this.downTeamMemberList = downTeamMemberList;
	}

	public List<TeamMember> getUpTeamMemberList() {
		return upTeamMemberList;
	}

	public void setUpTeamMemberList(List<TeamMember> upTeamMemberList) {
		this.upTeamMemberList = upTeamMemberList;
	}

}
