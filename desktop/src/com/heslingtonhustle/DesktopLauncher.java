package com.heslingtonhustle;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.heslingtonhustle.HeslingtonHustleGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setHdpiMode(HdpiMode.Logical);
		config.setForegroundFPS(60);
		config.setTitle("HeslingtonHustle");
		new Lwjgl3Application(new HeslingtonHustleGame(), config);
	}
}
