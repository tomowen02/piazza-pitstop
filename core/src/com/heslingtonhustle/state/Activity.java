package com.heslingtonhustle.state;

public class Activity {
    private int counter;
    private int value;
    private int timesPerformedToday;
    private final int maxTimesPerDay;

    public Activity() {
        counter = 0;
        value = 0;
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

    public boolean increaseValue(int value) {
        if (value > 0) {
            if (timesPerformedToday >= maxTimesPerDay) {
                return false;
            }
            this.value += value;
            incrementCounter();
            return true;
        } else {
            return false;
        }
    }

    private void incrementCounter() {
        counter++;
        timesPerformedToday++;
    }

    public void dayAdvanced() {
        timesPerformedToday = 0;
    }
}
