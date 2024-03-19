package com.heslingtonhustle.state;

public class Activity {
    private int counter;
    private int value;

    public Activity() {
        counter = 0;
        value = 0;
    }

    public int getCount() {
        return counter;
    }

    public int getValue() {
        return value;
    }

    public void increaseValue(int value) {
        if (value > 0) {
            this.value += value;
            counter++;
        }
    }
}
