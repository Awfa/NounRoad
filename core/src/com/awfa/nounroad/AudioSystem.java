package com.awfa.nounroad;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioSystem implements MessageListener {
	private MessageSystem messageSystem;

	private Sound correctSound, strikeSound, invalidSound;
	private Music theme;
	
	public AudioSystem(MessageSystem messageSystem) {
		this.messageSystem = messageSystem;
		messageSystem.register(this, Message.PLAYER_SCORED);
		messageSystem.register(this, Message.PLAYER_STRIKED);
		messageSystem.register(this, Message.INVALID_INPUT);
		messageSystem.register(this, Message.STATE_CHANGE);
		
		correctSound = Gdx.audio.newSound(Gdx.files.internal("correct.wav"));
		strikeSound = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));
		invalidSound = Gdx.audio.newSound(Gdx.files.internal("invalid.ogg"));
		
		theme = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.ogg"));
		
		theme.setLooping(true);
		theme.play();
	}

	@Override
	public void recieveMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recieveMessage(Message message, MessageExtra extra) {
		if (message == Message.PLAYER_SCORED) {
			correctSound.play();
		} else if (message == Message.PLAYER_STRIKED) {
			strikeSound.play();
		} else if (message == Message.INVALID_INPUT) {
			invalidSound.play();
		}
	}
}
