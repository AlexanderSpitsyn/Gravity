package com.asp.gravity;

import com.badlogic.gdx.Game;

public class GravityGame extends Game {
    private GravityScreen gravityScreen;
	
	@Override
	public void create () {
		gravityScreen = new GravityScreen();
        setScreen(gravityScreen);
	}

    @Override
    public void dispose() {
        gravityScreen.dispose();
    }
}
