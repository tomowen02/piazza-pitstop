package com.heslingtonhustle.state;

import com.badlogic.gdx.Gdx;

public class Activity {

    public Activity() {

    }

    public void start() {
        // Maybe we want to add callbacks to activities like I did for dialogue boxes??
        Gdx.app.debug("ACTIVITY", "You have just completed an activity!");
    }
}
