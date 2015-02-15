package com.awfa.nounroad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.awfa.nounroad.NounRoad;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Noun Rush!";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new NounRoad(), config);
	}
}
