package com.awfa.nounroad;

import com.badlogic.gdx.math.Interpolation;

public class FlashInterpolation extends Interpolation {
	@Override
	public float apply(float a) {
		return (float) Math.abs(Math.sin(a*Math.PI*2));
	}

}
