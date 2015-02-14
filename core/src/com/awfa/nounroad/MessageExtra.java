package com.awfa.nounroad;

public class MessageExtra {
	public String message;
	public int number;
	
	public MessageExtra(String message) {
		this(message, 0);
	}
	
	public MessageExtra(int number) {
		this(null, number);
	}
	
	public MessageExtra(String message, int number) {
		this.message = message;
		this.number = number;
	}
}
