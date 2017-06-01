package com.volleyball.match;

import java.util.Date;
import java.util.List;

public class Match {
	private int id;
	private String name;//��������
	private Team teamA;//A��
	private Team teamB;//B��
	private String city;//����
	private String stadium;//����
	private String site;//����
	private String stage;//�׶�
	private String category;//��𣨳��ꡢ���ꡢ���꣩
	private String competitionSystem;//���ƣ�ѭ���ơ���̭�ơ������������������������
	private Date matchStartDate;//������ʼ���ں�ʱ��
	private Date matchEndDate;//�����������ں�ʱ��
	
	private List<Set> sets;//ÿһ�ֱ����б�������3�֣������5�֣�������0��ʼ
	private int setNumber;//��ǰ���ڴ���ĵڼ��ֱ�����1-5��setNumber-1��Ϊsets������
	private int pauseTimeA;//A������ͣ����=sum(ÿһ�ֱ���A����ͣ����)
	private int pauseTimeB;//B������ͣ����=sum(ÿһ�ֱ���B����ͣ����)
	private int substitutionTimeA;//A�ӵ��ܻ��˴���=sum(ÿһ�ֱ���A�ӻ��˴���)
	private int substitutionTimeB;//B�ӵ��ܻ��˴���=sum(ÿһ�ֱ���B�ӻ��˴���)
	private Team winTeam;//�����Ļ�ʤ����
	private int totalScoreTeamA;//A�ӵ��ܷ�=sum(ÿһ�ֱ���A�����յ÷�)
	private int totalScoreTeamB;//B�ӵ��ܷ�=sum(ÿһ�ֱ���B�����յ÷�)
	private int setsDuration;//������ʱ��=sum(ÿһ�ֱ���ʱ��)����λ�����ӣ�
	private int failedSetTime;//��ʤ�������˼���

	private int integratingTeamA;//A�ӻ��֣�ѭ�����ƣ�
	private int integratingTeamB;//B�ӻ��֣�ѭ�����ƣ�
	private int rankingTeamA;//A�����Σ���ѭ�����ƣ�
	private int rankingTeamB;//B�����Σ���ѭ�����ƣ�
	public Match() {
		// 
	}
	public String getStageName() {
		return this.getStage() + "��" + this.getName();
	}
	public Team getTeamByName(String name) {
		if(this.getTeamA().getName().equals(name)) {
			return this.getTeamA();
		}
		if(this.getTeamB().getName().equals(name)) {
			return this.getTeamB();
		}
		return null;
	}
	public void calSetFailedSetTime() {
		int failedSetTime = 0;
		for(Set set:this.getSets()) {
			if(this.getWinTeam().getId() != set.getWinTeam().getId()) {
				failedSetTime++;
			}
		}
		this.setFailedSetTime(failedSetTime);
	}
	public void calSetSetsDuration() {
		long setsDurationSecond = 0;
		for(Set set:this.getSets()) {
			long setDurationSecond = (set.getSetEndTime().getTime() - set.getSetStartTime().getTime()) / 1000;
			setsDurationSecond = setsDurationSecond + setDurationSecond;
		}
		this.setSetsDuration((int) (setsDurationSecond / 60));
	}
	public void calSetTotalScoreTeamA() {
		int totalScore = 0;
		for(Set set:this.getSets()) {
			totalScore = totalScore + set.getFinalScoreTeamA();
		}
		this.setTotalScoreTeamA(totalScore);
	}
	public void calSetTotalScoreTeamB() {
		int totalScore = 0;
		for(Set set:this.getSets()) {
			totalScore = totalScore + set.getFinalScoreTeamB();
		}
		this.setTotalScoreTeamB(totalScore);
	}
	public void calSetPauseTimeA() {
		int pauseTime = 0;
		for(Set set:this.getSets()) {
			try {
				pauseTime = pauseTime + set.getPauseTeamA().size();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		this.setPauseTimeA(pauseTime);
	}
	public void calSetPauseTimeB() {
		int pauseTime = 0;
		for(Set set:this.getSets()) {
			try {
				pauseTime = pauseTime + set.getPauseTeamB().size();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		this.setPauseTimeB(pauseTime);
	}
	public void calSetSubstitutionTimeA() {
		int substitutionTime = 0;
		for(Set set:this.getSets()) {
			try {
				substitutionTime = substitutionTime + set.getSubstitutionTeamA().size();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		this.setSubstitutionTimeA(substitutionTime);
	}
	public void calSetSubstitutionTimeB() {
		int substitutionTime = 0;
		for(Set set:this.getSets()) {
			try {
				substitutionTime = substitutionTime + set.getSubstitutionTeamB().size();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		this.setSubstitutionTimeB(substitutionTime);
	}
	public Team calSetWinTeam() {
		//���㲢���ñ����Ļ�ʤ���飬���ػ�ʤ���飬���δ���ֻ�ʤ�����򷵻�null
		int winCountTeamA = 0;
		int winCountTeamB = 0;
		for(Set set:this.getSets()) {
			if(set.getWinTeam().getId() == this.getTeamA().getId()) {
				winCountTeamA++;
			}
			else {
				winCountTeamB++;
			}
		}
		if(winCountTeamA == 3) {
			this.setWinTeam(this.getTeamA());
		}
		if(winCountTeamB == 3) {
			this.setWinTeam(this.getTeamB());
		}
		return this.getWinTeam();
	}
	public void calSetIntegrating() {
		//���˳�������ѭ����ʱ�����㲢���ö���˫���Ļ��֣�3��0����3��1Ӯ�˼�3�֡����˼�0�֣�3��2Ӯ�˼�2�֡����˼�1��
		//���ô˺���֮ǰӦ�����ô˳������Ļ�ʤ���飬Ҳ����winTeam����������쳣��
		if(this.getCompetitionSystem().equals("ѭ����")) {
			if(this.getWinTeam().getId() == this.getTeamA().getId()) {
				if(this.getFailedSetTime() == 2) {//3:2
					this.setIntegratingTeamA(2);//A��ʤ�������2����
					this.setIntegratingTeamB(1);//B��ʧ�ܣ����1����
				}
				else {//3:0����3:1
					this.setIntegratingTeamA(3);
					this.setIntegratingTeamB(0);
				}
			}
			else {
				if(this.getFailedSetTime() == 2) {//3:2
					this.setIntegratingTeamA(1);//A��ʧ�ܣ����1����
					this.setIntegratingTeamB(2);//B��ʤ�������2����
				}
				else {//3:0����3:1
					this.setIntegratingTeamA(0);
					this.setIntegratingTeamB(3);
				}
			}
		}
	}
	public boolean addSet(Set set) {
		List<Set> sets = this.getSets();
		sets.add(set);
		this.setSets(sets);
		return true;
	}
	public boolean delSet(Set set) {
		List<Set> sets = this.getSets();
		sets.remove(set);
		this.setSets(sets);
		return true;
	}
	public Set getNowSet() {
		return this.getSets().get(this.getSetNumber()-1);
	}
	public void setNowSet(Set set) {
		List<Set> sets = this.getSets();
		sets.set(this.getSetNumber()-1, set);
		this.setSets(sets);
	}
	public Match getMatch() {
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

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getMatchStartDate() {
		return matchStartDate;
	}

	public void setMatchStartDate(Date matchStartDate) {
		this.matchStartDate = matchStartDate;
	}

	public Date getMatchEndDate() {
		return matchEndDate;
	}

	public void setMatchEndDate(Date matchEndDate) {
		this.matchEndDate = matchEndDate;
	}

	public List<Set> getSets() {
		return sets;
	}

	public void setSets(List<Set> sets) {
		this.sets = sets;
	}

	public int getPauseTimeA() {
		return pauseTimeA;
	}

	public void setPauseTimeA(int pauseTimeA) {
		this.pauseTimeA = pauseTimeA;
	}

	public int getPauseTimeB() {
		return pauseTimeB;
	}

	public void setPauseTimeB(int pauseTimeB) {
		this.pauseTimeB = pauseTimeB;
	}

	public int getSubstitutionTimeA() {
		return substitutionTimeA;
	}

	public void setSubstitutionTimeA(int substitutionTimeA) {
		this.substitutionTimeA = substitutionTimeA;
	}

	public int getSubstitutionTimeB() {
		return substitutionTimeB;
	}

	public void setSubstitutionTimeB(int substitutionTimeB) {
		this.substitutionTimeB = substitutionTimeB;
	}

	public Team getWinTeam() {
		return winTeam;
	}

	public void setWinTeam(Team winTeam) {
		this.winTeam = winTeam;
	}

	public int getTotalScoreTeamA() {
		return totalScoreTeamA;
	}

	public void setTotalScoreTeamA(int totalScoreTeamA) {
		this.totalScoreTeamA = totalScoreTeamA;
	}

	public int getTotalScoreTeamB() {
		return totalScoreTeamB;
	}

	public void setTotalScoreTeamB(int totalScoreTeamB) {
		this.totalScoreTeamB = totalScoreTeamB;
	}

	public int getSetsDuration() {
		return setsDuration;
	}

	public void setSetsDuration(int setsDuration) {
		this.setsDuration = setsDuration;
	}

	public int getFailedSetTime() {
		return failedSetTime;
	}

	public void setFailedSetTime(int failedSetTime) {
		this.failedSetTime = failedSetTime;
	}

	public int getSetNumber() {
		return setNumber;
	}

	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}
	public String getCompetitionSystem() {
		return competitionSystem;
	}
	public void setCompetitionSystem(String competitionSystem) {
		this.competitionSystem = competitionSystem;
	}
	public int getIntegratingTeamA() {
		return integratingTeamA;
	}
	public void setIntegratingTeamA(int integratingTeamA) {
		this.integratingTeamA = integratingTeamA;
	}
	public int getIntegratingTeamB() {
		return integratingTeamB;
	}
	public void setIntegratingTeamB(int integratingTeamB) {
		this.integratingTeamB = integratingTeamB;
	}
	public int getRankingTeamA() {
		return rankingTeamA;
	}
	public void setRankingTeamA(int rankingTeamA) {
		this.rankingTeamA = rankingTeamA;
	}
	public int getRankingTeamB() {
		return rankingTeamB;
	}
	public void setRankingTeamB(int rankingTeamB) {
		this.rankingTeamB = rankingTeamB;
	}

}
