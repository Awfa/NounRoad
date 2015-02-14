package com.awfa.nounroad;

import java.util.HashSet;
import java.util.Set;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GameManager {
	public static final float INIT_TIME = 5.0f;
	public static final float TIME_LIMIT = 3.0f;
	public static final int MAX_STRIKES = 3;
	
	public enum State {
		ENTERING_PLAYER_NAMES,
		GAME_INITIALIZE,
		GAME_RUNNING,
		GAME_OVER
	}
	
	private Player[] players;
	private int player;
	
	private float timer;
	
	private State gameState;
	private WordManager wordManager;
	private MessageSystem messageSystem;
	
	public GameManager(MessageSystem messageSystem) {
		gameState = State.ENTERING_PLAYER_NAMES;
		players = new Player[2];
		
		// load up nouns
		FileHandle nounListHandle = Gdx.files.internal("nouns.txt");
		String nounList = nounListHandle.readString();
		String[] nouns = nounList.split("\n");
		
		Set<String> words = new HashSet<String>();
		for(String noun : nouns) {
			words.add(noun);
		}
		
		wordManager = new WordManager(words);
		this.messageSystem = messageSystem;
	}
	
	public void update(float deltaTime) {
		if (gameState == State.GAME_INITIALIZE) {
			timer += deltaTime;
			if (timer > INIT_TIME) {
				gameState = State.GAME_RUNNING;
				messageSystem.sendMessage(Message.STATE_CHANGE, new MessageExtra(gameState.ordinal()));
			}
		}
		if (gameState == State.GAME_RUNNING) {
			timer += deltaTime;
			if (timer > TIME_LIMIT) {
				players[player].increaseStrikes();
				
				if (players[player].getStrikes() >= MAX_STRIKES) {
					gameState = State.GAME_OVER;
					messageSystem.sendMessage(Message.STATE_CHANGE, new MessageExtra(gameState.ordinal()));
				}
				gotoNextPlayer();
			}
		}
	}
	
	public void takeInput(String word) {
		if (gameState == State.ENTERING_PLAYER_NAMES) {
			// Make a new player with a name
			if (player < players.length) {
				players[player] = new Player(word);
				player++;
			}
			
			// Once everyone is ready, get the game to go
			if (player == players.length) {
				gameState = State.GAME_INITIALIZE;
				messageSystem.sendMessage(Message.STATE_CHANGE, new MessageExtra(gameState.ordinal()));
			}
		} else if (gameState == State.GAME_INITIALIZE) {
			// shouldn't be able to receive words while initializing
			throw new IllegalStateException();
		} else if (gameState == State.GAME_RUNNING) {
			// try to play the word
			boolean result = wordManager.playWord(word);
			if (result) { // if the word works, give the score
				players[player].increaseScore(1);
				gotoNextPlayer();
			}
		}
	}
	
	public State getState() {
		return gameState;
	}
	
	private void gotoNextPlayer() {
		player = (player + 1) % players.length;
	}
	
}
