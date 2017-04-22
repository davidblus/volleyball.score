package com.volleyball.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Set {

	private int num;//�ڼ��֣�1-5
	private Team firstTeam;//�׷�����
	private List<TeamMember> teamAInitialPosition;//A����ʼλ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	private List<TeamMember> teamBInitialPosition;//B����ʼλ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	private Date setStartTime;//��ֱ�����ʼʱ��
	private Date setEndTime;//��ֱ�������ʱ��
	private List<Point> pointRecords;//��ֱ����ȷּ�¼�б���ʵ�ʱ������˳��������¼
	private List<Pause> pauseTeamA;//��ֱ���A�ӽе���ͣ
	private List<Pause> pauseTeamB;//��ֱ���B�ӽе���ͣ
	private List<Substitution> substitutionTeamA;//��ֱ���A�ӽеĻ���
	private List<Substitution> substitutionTeamB;//��ֱ���B�ӽеĻ���
	private Team winTeam;//��ֱ����Ļ�ʤ����
	private int finalScoreTeamA;//��ֱ���A�ӵ����յ÷�
	private int finalScoreTeamB;//��ֱ���B�ӵ����յ÷�
	private String setDuration;//��ֱ���ʱ��=��ֱ�������ʱ��-��ֱ�����ʼʱ�䣬ʾ����1'20''25��ʾ1Сʱ20����25��
	
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
