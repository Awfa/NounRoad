package com.awfa.nounroad;

import java.util.*;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameInputManager implements InputProcessor {
	private String input;
	private MessageSystem messageSystem;
	
	public GameInputManager(MessageSystem messageSystem) {
		input = "";
		this.messageSystem = messageSystem;
	}
	
	public String getInput() {
		return input;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ENTER) {
			messageSystem.sendMessage(Message.TEXT_ENTERED, new MessageExtra(input));
			input = "";
			
			return true;
		} else if (keycode == Keys.BACKSPACE && input.length() > 0) {
			input = input.substring(0, input.length()-1);
			return true;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if (input.length() < 20 && Character.isAlphabetic(character)) {
			input += character;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
