package com.heslingtonhustle.state;

public class Activity {
    private int counter;
    private int value;
    private int timesPerformedToday;
    private int maxTimesPerDay;

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
        if (timesPerformedToday >= maxTimesPerDay || value <= 0) {
            return false;
        }
        this.value += value;
        incrementCounter();
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
