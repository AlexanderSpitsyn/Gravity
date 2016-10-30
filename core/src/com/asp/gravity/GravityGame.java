package com.asp.gravity;

import com.badlogic.gdx.Game;

public class GravityGame extends Game {

    private GravityScreen gravityScreen;
    private GravityGameManager gravityGameManager;
    private AssetsProvider assetsProvider;

	@Override
	public void create () {
        gravityGameManager = new GravityGameManager();
        assetsProvider = new AssetsProvider();
		gravityScreen = new GravityScreen(gravityGameManager, assetsProvider);
        setScreen(gravityScreen);
	}

    @Override
    public void dispose() {
        gravityScreen.dispose();
        assetsProvider.dispose();
    }
}
