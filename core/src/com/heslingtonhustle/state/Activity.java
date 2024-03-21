package com.heslingtonhustle.state;

/**
 * A generic game activity, which can be populated by triggers in the external editor.
 * Be sure to call dayAdvanced() each new day.
 * Use this for Recreation, Study, Eating.
 */
public class Activity {
    private int counter;
    private int value;
    private int timesPerformedToday;
    private int maxTimesPerDay;

    public Activity() {
        counter = 0;
        value = 0; // Represents different things depending on the type of activity
                    // The value can be specified in the Tiled trigger (e.g. eat: 2)
        timesPerformedToday = 0;
        maxTimesPerDay = 1;
    }

    public Activity(int maxTimesPerDay) {
        counter = 0;
        value = 0;
        timesPerformedToday = 0;
        this.maxTimesPerDay = maxTimesPerDay;
    }

    public int getCount() {
        return counter;
    }

    public int getValue() {
        return value;
    }

    public void increaseValue(int value) {
        this.value += value;
        incrementCounter();
    }

    public boolean canIncreaseValue() {
        if (timesPerformedToday >= maxTimesPerDay) {
            return false;
        }
        return true;
    }

    private void incrementCounter() {
        counter++;
        timesPerformedToday++;
    }

    public void dayAdvanced() {
        timesPerformedToday = 0;
    }

    public int getTimesPerformedToday() {
        return timesPerformedToday;
    }

    public void changeMaxTimesPerDay(int times) {
        maxTimesPerDay = times;
    }
}
