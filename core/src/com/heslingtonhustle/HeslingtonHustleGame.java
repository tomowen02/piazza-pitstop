package com.heslingtonhustle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.heslingtonhustle.screens.MenuScreen;
import com.heslingtonhustle.screens.PlayScreen;
import com.heslingtonhustle.screens.AvailableScreens;

public class HeslingtonHustleGame extends Game {
	private Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Logs all messages to the console
		changeScreen(AvailableScreens.MenuScreen);
	}

	public void changeScreen(AvailableScreens availableScreens) {
		if (currentScreen != null) {
			currentScreen.dispose();
		}
		switch (availableScreens) {
			case MenuScreen:
				currentScreen = new MenuScreen(this);
				break;
			case PlayScreen:
				currentScreen = new PlayScreen(this);
				break;
		}
		setScreen(currentScreen);
    }
}
