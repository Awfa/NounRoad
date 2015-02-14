package com.awfa.nounroad;

public interface MessageListener {
	public void recieveMessage(MessageSystem.Message message);
	public void recieveMessage(MessageSystem.Message message, MessageExtra extra);
}