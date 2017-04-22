package com.volleyball.match;

import java.io.Serializable;
import java.util.List;

public class TeamMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int number;
	private String name;

	public static boolean listContainObj(List<TeamMember> listTeamMembers, TeamMember teamMember) {
		for (TeamMember tempTeamMember:listTeamMembers) {
			if (tempTeamMember == null) {
				continue;
			}
			if (tempTeamMember.getNumber() == teamMember.getNumber() && tempTeamMember.getName().equals(teamMember.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public TeamMember() {
		this.setNumber(-1);
		this.setName("");
	}
	public TeamMember(int number, String name) {
		this.number = number;
		this.name = name;
	}
	public TeamMember(TeamMember teamMember) {
		this.number = teamMember.number;
		this.name = teamMember.name;
	}
	public TeamMember getTeamMember() {
		return this;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return "TeamMember \nnumber = " + this.number + "\nname = " + this.name + "\n";
	}

}
