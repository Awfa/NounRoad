package com.awfa.nounroad;

import com.awfa.nounroad.MessageSystem.Message;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NounRoad extends ApplicationAdapter implements MessageListener {
	private MessageSystem messageSystem;
	private GameManager gameManager;
	private GameInputManager gameInputManager;
	
	// prototype rendering
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont arial15;
	private BitmapFont test72;
	
	@Override
	public void create () {
		messageSystem = new MessageSystem();
		gameManager = new GameManager(messageSystem);
		gameInputManager = new GameInputManager(messageSystem);
		Gdx.input.setInputProcessor(gameInputManager);
		
		messageSystem.register(this, Message.TEXT_ENTERED);
		//prototype rendering
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		arial15 = new BitmapFont(Gdx.files.internal("font.txt"));
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render() {
		gameManager.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(203.f/255, 240.f/255, 241.f/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int textY = 705;
		camera.update();
		batch.begin();
		int offset = 72;
		arial15.draw(batch, "Game State: " + gameManager.getState(), 15, textY);
		textY -= offset;
		
		if(gameManager.getState() != GameManager.State.ENTERING_PLAYER_NAMES) {
			Player p1 = gameManager.getPlayer(0);
			Player p2 = gameManager.getPlayer(1);
			
			arial15.draw(batch, "Time left: " + gameManager.getTimeLeft(), 15, textY);
			textY -= offset;
			
			arial15.draw(batch, "Player 1 Name: " + p1.getName() + ", Strikes: " + p1.getStrikes(), 15, textY);
			textY -= offset;
			
			arial15.draw(batch, "Player 2 Name: " + p2.getName() + ", Strikes: " + p2.getStrikes(), 15, textY);
			textY -= offset;
			
			arial15.draw(batch, "It is " + gameManager.getCurrentPlayer().getName(), 15, textY);
			textY -= offset;
			
			arial15.draw(batch, "Recently used words: " + gameManager.getRecentWords(), 15, textY);
			textY -= offset;
		}
		
		arial15.draw(batch, "Type here: " + gameInputManager.getInput(), 15, textY);
		textY -= offset;
		
		batch.end();
	}

	@Override
	public void recieveMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recieveMessage(Message message, MessageExtra extra) {
		if (message == Message.TEXT_ENTERED) {
			gameManager.takeInput(extra.message);
		}
	}
}
