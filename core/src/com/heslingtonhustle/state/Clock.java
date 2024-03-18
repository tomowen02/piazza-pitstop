package com.heslingtonhustle.state;

public class Clock {
    private float MAX_TIME = 1000;  // This is the number of time units in a day.
                                    // This doesn't really mean anything as the player cant do anything at night anyway
    private float speed;
    private float timeUnits;
    private int day;

    public Clock() {
        timeUnits = 0;
        day = 1;
        speed = 6; // Probably want this to be less
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

    public void increaseTime(float delta) {
        if (timeUnits >= MAX_TIME) {
            return;
        }
        timeUnits += delta * speed;
    }

    public int incrementDay() {
        timeUnits = 0;
        day += 1;
        return day;
    }

    public void increaseSpeed(int speedIncrease) {
        speed += speedIncrease;
    }

    public String getDebugString() {
        return timeUnits + " " + getTime();
    }
}
