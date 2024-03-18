package com.heslingtonhustle.state;

// Likely a temporary class
public class Activities {
    private int recreationCounter = 0;
    private int recreationValue = 0;
    private int studyCounter = 0;
    private int studyValue = 0;
    private int eatCounter = 0;
    private int eatValue = 0;

    public void eat(int value) {
        eatValue += value;
        eatCounter++;
    }

    public void recreate(int value) {
        recreationValue += value;
        recreationCounter++;
    }

    public void study(int value) {
        studyValue += value;
        studyCounter++;
    }

    public int getRecreationCounter() {
        return recreationCounter;
    }

    public int getRecreationValue() {
        return recreationValue;
    }

    public int getStudyCounter() {
        return studyCounter;
    }

    public int getStudyValue() {
        return studyValue;
    }

    public int getEatCounter() {
        return eatCounter;
    }

    public int getEatValue() {
        return eatValue;
    }
}
