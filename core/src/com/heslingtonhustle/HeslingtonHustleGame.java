package com.heslingtonhustle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.heslingtonhustle.screens.MenuScreen;
import com.heslingtonhustle.screens.Screen;

public class HeslingtonHustleGame extends Game {
	

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Logs all messages to the console
		setScreen(new MenuScreen(this));
//		setScreen(new PlayScreen(this));
	}

	public boolean changeScreen(Screen screen) {
		switch (screen) {
			case MenuScreen:
				setScreen(new MenuScreen(this));
			case PlayScreen:
				break;
			default:
				return false;
		}
		return true;
	}
}
