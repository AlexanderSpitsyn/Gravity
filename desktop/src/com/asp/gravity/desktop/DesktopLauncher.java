package com.asp.gravity.desktop;

import com.asp.gravity.Constants;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.asp.gravity.GravityGame;

public class DesktopLauncher {
	public static void main (final String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.WIDTH;
		config.height = Constants.HEIGHT;
        config.samples = 4;
		new LwjglApplication(new GravityGame(), config);
	}
}
