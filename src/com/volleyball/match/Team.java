package com.volleyball.match;

import java.util.List;

public class Team {
	private int id;
	private String name;
	private List<TeamMember> normalTeamMembers;
	private List<TeamMember> liberoTeamMembers;
	private String captainName;
	private String coachName;

	public Team() {
		// TODO Auto-generated constructor stub
		this.setId(-1);
		this.setName("");
		this.setNormalTeamMembers(null);
		this.setLiberoTeamMembers(null);
		this.setCaptainName("");
		this.setCoachName("");
	}
	public Team(int id, String name, List<TeamMember> normalTeamMembers, List<TeamMember> liberoTeamMembers, String captainName, String coachName) {
		this.id = id;
		this.name = name;
		this.normalTeamMembers = normalTeamMembers;
		this.liberoTeamMembers = liberoTeamMembers;
		this.captainName = captainName;
		this.coachName = coachName;
	}
	public Team(Team team) {
		this.id = team.id;
		this.name = team.name;
		this.normalTeamMembers = team.normalTeamMembers;
		this.liberoTeamMembers = team.liberoTeamMembers;
		this.captainName = team.captainName;
		this.coachName = team.coachName;
	}
	public Team getTeam() {
		return this;
	}
	public TeamMember getTeamMemberByNum(int number) {
		for(int i = 0; i < this.getNormalTeamMembers().size(); ++i) {
			if(this.getNormalTeamMembers().get(i).getNumber() == number) {
				return this.getNormalTeamMembers().get(i);
			}
		}
		for(int i = 0; i < this.getLiberoTeamMembers().size(); ++i) {
			if(this.getLiberoTeamMembers().get(i).getNumber() == number) {
				return this.getLiberoTeamMembers().get(i);
			}
		}
		return null;
	}

	public boolean addNormalTeamMembers(TeamMember teamMember) {
		List<TeamMember> normalTeamMembers = this.getNormalTeamMembers();
		normalTeamMembers.add(teamMember);
		this.setNormalTeamMembers(normalTeamMembers);
		return true;
	}
	public boolean delNormalTeamMembers(TeamMember teamMember) {
		List<TeamMember> normalTeamMembers = this.getNormalTeamMembers();
		normalTeamMembers.remove(teamMember);
		this.setNormalTeamMembers(normalTeamMembers);
		return true;
	}
	public boolean addLiberoTeamMembers(TeamMember teamMember) {
		List<TeamMember> liberoTeamMembers = this.getLiberoTeamMembers();
		liberoTeamMembers.add(teamMember);
		this.setLiberoTeamMembers(liberoTeamMembers);
		return true;
	}
	public boolean delLiberoTeamMembers(TeamMember teamMember) {
		List<TeamMember> liberoTeamMembers = this.getLiberoTeamMembers();
		liberoTeamMembers.remove(teamMember);
		this.setLiberoTeamMembers(liberoTeamMembers);
		return true;
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

	public List<TeamMember> getNormalTeamMembers() {
		return normalTeamMembers;
	}

	public void setNormalTeamMembers(List<TeamMember> normalTeamMembers) {
		this.normalTeamMembers = normalTeamMembers;
	}

	public List<TeamMember> getLiberoTeamMembers() {
		return liberoTeamMembers;
	}

	public void setLiberoTeamMembers(List<TeamMember> liberoTeamMembers) {
		this.liberoTeamMembers = liberoTeamMembers;
	}

	public String getCaptainName() {
		return captainName;
	}

	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String toString() {
		return "Team \nid = " + this.id + "\nname = " + this.name + 
				"\nnormalTeamMembers = " + this.normalTeamMembers.toString() + 
				"\nliberoTeamMembers = " + this.liberoTeamMembers.toString() +
				"\ncaptainName = " + this.captainName + "\ncoachName = " + this.coachName + "\n";
	}

}
