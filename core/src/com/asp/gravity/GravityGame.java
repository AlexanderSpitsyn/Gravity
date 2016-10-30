package com.asp.gravity;

import com.badlogic.gdx.Game;

public class GravityGame extends Game {

    private GravityScreen gravityScreen;
    private AssetsProvider assetsProvider;
	
	@Override
	public void create () {
        assetsProvider = new AssetsProvider();
		gravityScreen = new GravityScreen(assetsProvider);
        setScreen(gravityScreen);
	}

    @Override
    public void dispose() {
        gravityScreen.dispose();
        assetsProvider.dispose();
    }
}
