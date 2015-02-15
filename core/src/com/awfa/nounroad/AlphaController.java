package com.awfa.nounroad;

import com.badlogic.gdx.math.Interpolation;

public class AlphaController {
	private Interpolation interpolation;
	private float timer;
	private boolean running;
	
	public AlphaController(Interpolation interpolation) {
		this.interpolation = interpolation;
	}
	
	public float getAlpha() {
		return interpolation.apply(timer);
	}
	
	public void startInterpolation() {
		running = true;
		timer = 0.0f;
	}
	
	public void update(float deltaTime) {
		if (running) {
			timer += deltaTime;
			
			if(timer >= 1.0f) {
				timer = 1.0f;
				running = false;
			}
		}
	}
}
