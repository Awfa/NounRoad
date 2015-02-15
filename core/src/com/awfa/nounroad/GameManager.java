package com.awfa.nounroad;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GameManager {
	public static final float INIT_TIME = 0.2f;
	public static final float TIME_LIMIT = 12.0f;
	public static final int MAX_STRIKES = 3;
	public static final int NUM_OF_PLAYERS = 2;
	public static final String RECENT_DELIMITER = "   ";
	
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
		players = new Player[NUM_OF_PLAYERS];
		
		// load up nouns
		FileHandle nounListHandle = Gdx.files.internal("nouns.txt");
		String nounList = nounListHandle.readString();
		String[] nouns = nounList.split("\n");
		
		Set<String> words = new HashSet<String>(53800);
		for(String noun : nouns) {
			noun = noun.toLowerCase().trim();
			words.add(noun);
		}
		
		wordManager = new WordManager(words);
		this.messageSystem = messageSystem;
	}
	
	public void update(float deltaTime) {
		if (gameState == State.GAME_INITIALIZE) {
			timer += deltaTime;
			if (timer > INIT_TIME) {
				timer = 0.0f;
				changeState(State.GAME_RUNNING);
			}
		}
		if (gameState == State.GAME_RUNNING) {
			timer += deltaTime;
			if (timer > TIME_LIMIT) {
				players[player].increaseStrikes();
				messageSystem.sendMessage(Message.PLAYER_STRIKED, new MessageExtra(player));
				if (players[player].getStrikes() >= MAX_STRIKES) {
					gotoNextPlayer();
					changeState(State.GAME_OVER);
				} else {
					gotoNextPlayer();
				}
				timer = 0.0f;
			}
		}
	}
	
	public void takeInput(String word) {
		if (gameState == State.ENTERING_PLAYER_NAMES) {
			// Make a new player with a name
			players[player] = new Player(word);
			messageSystem.sendMessage(Message.PLAYER_NAME_ENTERED);
			if (player == players.length - 1) {
				// Once everyone is ready, get the game to go
				changeState(State.GAME_INITIALIZE);
			}
			gotoNextPlayer();
		} else if (gameState == State.GAME_INITIALIZE) {
			// shouldn't be able to receive words while initializing
			throw new IllegalStateException();
		} else if (gameState == State.GAME_RUNNING) {
			// try to play the word
			boolean result = wordManager.playWord(word);
			if (result) { // if the word works, give the score
				players[player].increaseScore(1);
				messageSystem.sendMessage(Message.PLAYER_SCORED, new MessageExtra(player));
				timer = 0.0f;
				gotoNextPlayer();
			} else {
				messageSystem.sendMessage(Message.INVALID_INPUT, new MessageExtra(player));
			}
		}
	}
	
	public State getState() {
		return gameState;
	}
	
	public Player getPlayer(int playerNum) {
		return players[playerNum];
	}
	
	public float getTimeLeft() {
		if (gameState == State.GAME_INITIALIZE) {
			return INIT_TIME - timer;
		} else {
			return TIME_LIMIT - timer;
		}
	}
	
	public Player getCurrentPlayer() {
		return players[player];
	}
	
	public String getRecentWords() {
		StringBuilder recentWords = new StringBuilder();
		Stack<String> reversedWords = new Stack<String>();
		
		for(String word : wordManager.getUsedWords()) {
			reversedWords.push(word);
		}
		
		if(!reversedWords.isEmpty()) {
			while(!reversedWords.isEmpty()) {
				recentWords.append(RECENT_DELIMITER);
				recentWords.append(reversedWords.pop());
			}
		}
		
		return recentWords.toString();
	}
	
	private void gotoNextPlayer() {
		player = (player + 1) % NUM_OF_PLAYERS;
	}
	
	private void changeState(State newState) {
		gameState = newState;
		messageSystem.sendMessage(Message.STATE_CHANGE);
	}
	
}
