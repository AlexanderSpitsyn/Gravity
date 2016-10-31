package com.asp.gravity.desktop;

import com.asp.gravity.GravityGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (final String[] arg) {
        TexturePacker.processIfModified("../../assetsSrc", "./", "texture");

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
        config.samples = 4;
		new LwjglApplication(new GravityGame(), config);
	}
}
