package com.heslingtonhustle.state;

/**
 * Manages both the day and the time.
 */
public class Clock {
    private final float MAX_TIME = 1000;  // This is the number of time units in a day.
                                          // This doesn't really mean anything as the player cant do anything at night anyway
    private float speed;
    private float timeUnits;
    private int day;

    public Clock() {
        timeUnits = 0;
        day = 1;
        speed = 8; // Probably want this to be less
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

    public int getDay() {
        return day;
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

    // Is this method necessary? Should the day ever decrease?
    public int decrementDay() {
        timeUnits = 0;
        day -= 1;
        return day;
    }

    public void setSpeed(int speed) {
        // This is mainly used for debugging
        this.speed = speed;
    }

    public String getDebugString() {
        return timeUnits +" "+getTime() + " Day: "+getDay() + " Speed: "+speed;
    }
}
