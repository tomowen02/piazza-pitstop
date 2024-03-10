package com.heslingtonhustle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class HeslingtonHustleGame extends Game {
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Logs all messages to the console
		setScreen(new PlayScreen());
	}
}
