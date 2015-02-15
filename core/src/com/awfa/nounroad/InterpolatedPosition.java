package com.awfa.nounroad;

import com.badlogic.gdx.math.Interpolation;

public class InterpolatedPosition {
	private float x;
	private float y;
	
	private float targetX;
	private float targetY;
	
	private Interpolation interpolation;
	private float currentAlpha;
	
	public InterpolatedPosition(float x, float y, Interpolation interpolation) {
		this.x = x;
		this.y = y;
		
		targetX = x;
		targetY = y;
		
		this.interpolation = interpolation;
		currentAlpha = 0.0f;
	}
	
	public float getCurrX() {
		return interpolation.apply(x, targetX, currentAlpha);
	}
	
	public float getCurrY() {
		return interpolation.apply(y, targetY, currentAlpha);
	}
	
	public void setNewPosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
		
		targetX = newX;
		targetY = newY;
		
		reset();
	}
	
	public void setNewTarget(float newX, float newY) {
		// finish going to previous target
		this.x = targetX;
		this.y = targetY;
		
		// set new target
		targetX = newX;
		targetY = newY;
		
		// start progress over
		reset();
	}
	
	public void update(float deltaTime) {
		currentAlpha += deltaTime;
		if (currentAlpha > 1.0f) {
			currentAlpha = 1.0f;
		}
	}
	
	public boolean isComplete() {
		return currentAlpha == 1.0f;
	}
	
	public void reset() {
		currentAlpha = 0;
	}
}
