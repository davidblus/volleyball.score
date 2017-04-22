package com.volleyball.match;

public class Pause {

	private int initiatorScore;//发起暂停队伍的分数
	private int receiverScore;//接受暂停队伍的分数
	
	public Pause() {
		// 
	}

	public Pause(int initiatorScore, int receiverScore) {
		this.initiatorScore = initiatorScore;
		this.receiverScore = receiverScore;
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

}
