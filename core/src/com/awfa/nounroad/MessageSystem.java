package com.awfa.nounroad;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class MessageSystem {
	public enum Message {
		STATE_CHANGE,
		TEXT_ENTERED,
		PLAYER_SCORED, PLAYER_STRIKED, INVALID_INPUT,
		PLAYER_NAME_ENTERED,
		GAME_RESET
	}
	
	private ObjectMap<Message, Array<MessageListener>> listenerMap;
	
	public MessageSystem() {
		listenerMap = new ObjectMap<Message, Array<MessageListener>>();
		
		for (int i = 0; i < Message.values().length; ++i) {
			listenerMap.put(Message.values()[i], new Array<MessageListener>());
		}
	}
	
	public void register(MessageListener recipient, Message message) {
		listenerMap.get(message).add(recipient);
	}
	
	public void sendMessage(Message message) {
		Array<MessageListener> listeners = listenerMap.get(message);
		
		for (MessageListener listener : listeners) {
			listener.recieveMessage(message);
		}
	}
	
	public void sendMessage(Message message, MessageExtra extra) {
		Array<MessageListener> listeners = listenerMap.get(message);
		
		for (MessageListener listener : listeners) {
			listener.recieveMessage(message, extra);
		}
	}
}