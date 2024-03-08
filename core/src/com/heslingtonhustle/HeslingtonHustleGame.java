package com.heslingtonhustle;

import com.badlogic.gdx.Game;

public class HeslingtonHustleGame extends Game {
	@Override
	public void create() {
		setScreen(new PlayScreen());
	}
}
