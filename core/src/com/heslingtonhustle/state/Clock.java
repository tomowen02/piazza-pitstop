package com.heslingtonhustle.state;

import com.badlogic.gdx.Gdx;

public class Clock {
    private float speed;
    private float timeUnits;

    public Clock() {
        timeUnits = 0;
//        speed = 4;
        speed = 10; // ONLY FOR TESTING
    }


    public Time getTime() {
        if (timeUnits < 250) {
            return Time.MORNING;
        } else if (timeUnits < 500) {
            return Time.AFTERNOON;
        } else if (timeUnits < 750) {
            return Time.EVENING;
        } else {
            return Time.NIGHT;
        }
    }

    public void update(float delta) {
        timeUnits += delta * speed;
//        Gdx.app.debug("DEBUG", "DELTA: "+delta);
    }

    public void increaseSpeed(int speedIncrease) {
        speed += speedIncrease;
    }

    public String getDebugString() {
        return timeUnits + " " + getTime();
    }
}
