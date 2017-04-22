package com.volleyball.match;

import java.util.Date;
import java.util.List;

public class Match {
	private int id;
	private String name;//比赛名称
	private Team teamA;//A队
	private Team teamB;//B队
	private String city;//城市
	private String stadium;//场馆
	private String site;//场地
	private String stage;//阶段
	private String category;//类别（成年、青年、少年）
	private Date matchStartDate;//比赛开始日期和时间
	private Date matchEndDate;//比赛结束日期和时间
	
	private List<Set> sets;//每一局比赛列表，最少有3局，最多有5局，索引从0开始
	private int setNumber;//当前正在处理的第几局比赛，1-5，setNumber-1即为sets的索引
	private int pauseTimeA;//A队总暂停次数=sum(每一局比赛A队暂停次数)
	private int pauseTimeB;//B队总暂停次数=sum(每一局比赛B队暂停次数)
	private int substitutionTimeA;//A队的总换人次数=sum(每一局比赛A队换人次数)
	private int substitutionTimeB;//B队的总换人次数=sum(每一局比赛B队换人次数)
	private Team winTeam;//比赛的获胜队伍
	private int totalScoreTeamA;//A队的总分=sum(每一局比赛A队最终得分)
	private int totalScoreTeamB;//B队的总分=sum(每一局比赛B队最终得分)
	private int setsDuration;//比赛总时长=sum(每一局比赛时长)，单位（分钟）
	private int failedSetTime;//获胜队伍输了几局

	public Match() {
		// 
	}
	public String getStageName() {
		return this.getStage() + "：" + this.getName();
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
		//计算并设置比赛的获胜队伍，返回获胜队伍，如果未出现获胜队伍则返回null
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

}
