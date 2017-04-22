package com.volleyball.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Set {

	private int num;//第几局，1-5
	private Team firstTeam;//首发队伍
	private List<TeamMember> teamAInitialPosition;//A队起始位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	private List<TeamMember> teamBInitialPosition;//B队起始位置，下标从1-6分别代表1-6号位，0为空冗余空间null
	private Date setStartTime;//这局比赛开始时间
	private Date setEndTime;//这局比赛结束时间
	private List<Point> pointRecords;//这局比赛比分记录列表，按实际比赛情况顺序索引记录
	private List<Pause> pauseTeamA;//这局比赛A队叫的暂停
	private List<Pause> pauseTeamB;//这局比赛B队叫的暂停
	private List<Substitution> substitutionTeamA;//这局比赛A队叫的换人
	private List<Substitution> substitutionTeamB;//这局比赛B队叫的换人
	private Team winTeam;//这局比赛的获胜队伍
	private int finalScoreTeamA;//这局比赛A队的最终得分
	private int finalScoreTeamB;//这局比赛B队的最终得分
	private String setDuration;//这局比赛时长=这局比赛结束时间-这局比赛开始时间，示例：1'20''25表示1小时20分钟25秒
	
	public Set() {
		// 
	}
	public Substitution getLastSubstitutionTeamA() {
		List<Substitution> substitutionTeamA = this.getSubstitutionTeamA();
		try {
			Substitution substitution = substitutionTeamA.get(substitutionTeamA.size()-1);
			return substitution;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return null;
		}
	}
	public Substitution getLastSubstitutionTeamB() {
		List<Substitution> substitutionTeamB = this.getSubstitutionTeamB();
		try {
			Substitution substitution = substitutionTeamB.get(substitutionTeamB.size()-1);
			return substitution;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return null;
		}
	}
	public boolean realAddSubstitutionTeamA(Substitution substitution) {
		List<Substitution> substitutionsTeamA = this.getSubstitutionTeamA();
		if (substitutionsTeamA != null) {
			if (substitutionsTeamA.size() > 1) {
				return false;
			}
			else {
				Substitution oldSubstitution = substitutionsTeamA.get(0);
				for (int i = 0; i < substitution.getDownTeamMemberList().size(); ++i){
					int index = oldSubstitution.getUpTeamMemberList().indexOf(substitution.getDownTeamMemberList().get(i));
					if (index != -1) {
						if (oldSubstitution.getDownTeamMemberList().get(index).getNumber() != substitution.getUpTeamMemberList().get(i).getNumber()) {
							return false;
						}
					}
				}
				for (int i = 0; i < substitution.getUpTeamMemberList().size(); ++i) {
					int index = oldSubstitution.getDownTeamMemberList().indexOf(substitution.getUpTeamMemberList().get(i));
					if (index != -1) {
						if (oldSubstitution.getUpTeamMemberList().get(index).getNumber() != substitution.getDownTeamMemberList().get(i).getNumber()) {
							return false;
						}
					}
				}
			}
		}
		
		this.addSubstitutionTeamA(substitution);
		return true;
	}
	public boolean realAddSubstitutionTeamB(Substitution substitution) {
		List<Substitution> substitutionsTeamB = this.getSubstitutionTeamB();
		if (substitutionsTeamB != null) {
			if (substitutionsTeamB.size() > 1) {
				return false;
			}
			else {
				Substitution oldSubstitution = substitutionsTeamB.get(0);
				for (int i = 0; i < substitution.getDownTeamMemberList().size(); ++i){
					int index = oldSubstitution.getUpTeamMemberList().indexOf(substitution.getDownTeamMemberList().get(i));
					if (index != -1) {
						if (oldSubstitution.getDownTeamMemberList().get(index).getNumber() != substitution.getUpTeamMemberList().get(i).getNumber()) {
							return false;
						}
					}
				}
				for (int i = 0; i < substitution.getUpTeamMemberList().size(); ++i) {
					int index = oldSubstitution.getDownTeamMemberList().indexOf(substitution.getUpTeamMemberList().get(i));
					if (index != -1) {
						if (oldSubstitution.getUpTeamMemberList().get(index).getNumber() != substitution.getDownTeamMemberList().get(i).getNumber()) {
							return false;
						}
					}
				}
			}
		}
		
		this.addSubstitutionTeamB(substitution);
		return true;
	}
	public void addSubstitutionTeamA(Substitution substitution) {
		List<Substitution> substitutionTeamA = this.getSubstitutionTeamA();
		if (substitutionTeamA == null) {
			substitutionTeamA = new ArrayList<Substitution>();
		}
		substitutionTeamA.add(substitution);
		this.setSubstitutionTeamA(substitutionTeamA);
	}
	public void addSubstitutionTeamB(Substitution substitution) {
		List<Substitution> substitutionTeamB = this.getSubstitutionTeamB();
		if (substitutionTeamB == null) {
			substitutionTeamB = new ArrayList<Substitution>();
		}
		substitutionTeamB.add(substitution);
		this.setSubstitutionTeamB(substitutionTeamB);
	}
	public void addPauseTeamA(Pause pause) {
		List<Pause> pauseTeamA = this.getPauseTeamA();
		if (pauseTeamA == null) {
			pauseTeamA = new ArrayList<Pause>();
		}
		pauseTeamA.add(pause);
		this.setPauseTeamA(pauseTeamA);
	}
	public void addPauseTeamB(Pause pause) {
		List<Pause> pauseTeamB = this.getPauseTeamB();
		if (pauseTeamB == null) {
			pauseTeamB = new ArrayList<Pause>();
		}
		pauseTeamB.add(pause);
		this.setPauseTeamB(pauseTeamB);
	}
	public void addTeamAInitialPosition(TeamMember teamMember) {
		List<TeamMember> teamAInitialPosition = this.getTeamAInitialPosition();
		if (teamAInitialPosition == null) {
			teamAInitialPosition = new ArrayList<TeamMember>();
		}
		teamAInitialPosition.add(teamMember);
		this.setTeamAInitialPosition(teamAInitialPosition);
	}
	public void addTeamBInitialPosition(TeamMember teamMember) {
		List<TeamMember> teamBInitialPosition = this.getTeamBInitialPosition();
		if (teamBInitialPosition == null) {
			teamBInitialPosition = new ArrayList<TeamMember>();
		}
		teamBInitialPosition.add(teamMember);
		this.setTeamBInitialPosition(teamBInitialPosition);
	}
	public void pushPointRecord(Point point) {
		List<Point> pointRecords = this.getPointRecords();
		if(pointRecords == null) {
			pointRecords = new ArrayList<Point>();
		}
		pointRecords.add(point);
		this.setPointRecords(pointRecords);
	}
	public Point popPointRecord() {
		List<Point> pointRecords = this.getPointRecords();
		try {
			Point point = pointRecords.get(pointRecords.size()-1);
			pointRecords.remove(point);
			this.setPointRecords(pointRecords);
			return point;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return null;
		}
	}
	public Point getLastPointRecord() {
		List<Point> pointRecords = this.getPointRecords();
		try {
			Point point = pointRecords.get(pointRecords.size()-1);
			return point;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return null;
		}
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Team getFirstTeam() {
		return firstTeam;
	}

	public void setFirstTeam(Team firstTeam) {
		this.firstTeam = firstTeam;
	}

	public List<TeamMember> getTeamAInitialPosition() {
		return teamAInitialPosition;
	}

	public void setTeamAInitialPosition(List<TeamMember> teamAInitialPosition) {
		this.teamAInitialPosition = teamAInitialPosition;
	}

	public List<TeamMember> getTeamBInitialPosition() {
		return teamBInitialPosition;
	}

	public void setTeamBInitialPosition(List<TeamMember> teamBinitialPosition) {
		this.teamBInitialPosition = teamBinitialPosition;
	}

	public Date getSetStartTime() {
		return setStartTime;
	}

	public void setSetStartTime(Date setStartTime) {
		this.setStartTime = setStartTime;
	}

	public Date getSetEndTime() {
		return setEndTime;
	}

	public void setSetEndTime(Date setEndTime) {
		this.setEndTime = setEndTime;
	}

	public List<Point> getPointRecords() {
		return pointRecords;
	}

	public void setPointRecords(List<Point> pointRecords) {
		this.pointRecords = pointRecords;
	}

	public List<Pause> getPauseTeamA() {
		return pauseTeamA;
	}

	public void setPauseTeamA(List<Pause> pauseTeamA) {
		this.pauseTeamA = pauseTeamA;
	}

	public List<Pause> getPauseTeamB() {
		return pauseTeamB;
	}

	public void setPauseTeamB(List<Pause> pauseTeamB) {
		this.pauseTeamB = pauseTeamB;
	}

	public Team getWinTeam() {
		return winTeam;
	}

	public void setWinTeam(Team winTeam) {
		this.winTeam = winTeam;
	}

	public int getFinalScoreTeamA() {
		return finalScoreTeamA;
	}

	public void setFinalScoreTeamA(int finalScoreTeamA) {
		this.finalScoreTeamA = finalScoreTeamA;
	}

	public int getFinalScoreTeamB() {
		return finalScoreTeamB;
	}

	public void setFinalScoreTeamB(int finalScoreTeamB) {
		this.finalScoreTeamB = finalScoreTeamB;
	}

	public String getSetDuration() {
		return setDuration;
	}

	public void setSetDuration(String setDuration) {
		this.setDuration = setDuration;
	}

	public List<Substitution> getSubstitutionTeamA() {
		return substitutionTeamA;
	}

	public void setSubstitutionTeamA(List<Substitution> substitutionTeamA) {
		this.substitutionTeamA = substitutionTeamA;
	}

	public List<Substitution> getSubstitutionTeamB() {
		return substitutionTeamB;
	}

	public void setSubstitutionTeamB(List<Substitution> substitutionTeamB) {
		this.substitutionTeamB = substitutionTeamB;
	}

}
