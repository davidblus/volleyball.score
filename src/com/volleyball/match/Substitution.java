package com.volleyball.match;

import java.util.ArrayList;
import java.util.List;

public class Substitution {

	private int initiatorScore;//�����˶���ķ���
	private int receiverScore;//���ܻ��˶���ķ���

	//ע�⣡�����϶�Ա�б�ͱ����¶�Ա�б���һһ��Ӧ�ģ�����һ�ֱ����У�ÿ����Ա������һ�Ρ���һ��
	private List<TeamMember> downTeamMemberList;//�����¶�Ա�б�
	private List<TeamMember> upTeamMemberList;//�����϶�Ա�б�
	
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
