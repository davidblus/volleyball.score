package com.volleyball.match;

public class Pause {

	private int initiatorScore;//������ͣ����ķ���
	private int receiverScore;//������ͣ����ķ���
	
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
