package com.awfa.nounroad;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class LoggingSystem implements MessageListener {
	private MessageSystem messageSystem;
	private long time;
	public LoggingSystem(MessageSystem messageSystem) {
		this.messageSystem = messageSystem;
		messageSystem.register(this, Message.PLAYER_SCORED);
		messageSystem.register(this, Message.PLAYER_STRIKED);
		time = TimeUtils.millis();
	}
	@Override
	public void recieveMessage(Message message) {
		long timePassed = TimeUtils.timeSinceMillis(time);
		if (message == Message.PLAYER_SCORED) {
			Gdx.app.log("PLAYER_SCORED", timePassed/1000+"s");
			time += timePassed;
		} else if (message == Message.PLAYER_STRIKED) {
			//Gdx.app.log("PLAYER_STRIKED", timePassed/1000+"s");
		}
	}
	@Override
	public void recieveMessage(Message message, MessageExtra extra) {
		// TODO Auto-generated method stub
		
	}
}
