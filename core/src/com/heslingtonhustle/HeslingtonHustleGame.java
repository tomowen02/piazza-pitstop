package com.heslingtonhustle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.heslingtonhustle.screens.MenuScreen;
import com.heslingtonhustle.screens.PlayScreen;
import com.heslingtonhustle.screens.AvailableScreens;

public class HeslingtonHustleGame extends Game {
	private final AvailableScreens DEFAULT_AvailableScreens = AvailableScreens.MenuScreen;
	private MenuScreen menuScreen;
	private PlayScreen playScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Logs all messages to the console
		changeScreen(DEFAULT_AvailableScreens);
	}

	public boolean changeScreen(AvailableScreens availableScreens) {
		switch (availableScreens) {
			case MenuScreen:
				if (menuScreen == null) { menuScreen = new MenuScreen(this); }
				setScreen(menuScreen);
				break;
			case PlayScreen:
				if (playScreen == null) { playScreen = new PlayScreen(this); }
				setScreen(playScreen);
				break;
			default:
				return false;
		}
		return true; // Successfully changed screen
    }
}
