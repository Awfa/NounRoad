package com.awfa.nounroad;

public class Player {
	private int score;
	private int strikes;
	private String name;
	
	public Player(String name) {
		score = 0;
		strikes = 0;
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getStrikes() {
		return strikes;
	}
	
	public String getName() {
		return name;
	}
	
	public void increaseScore(int amount) {
		score += amount;
	}
	
	public void increaseStrikes() {
		strikes++;
	}
}
