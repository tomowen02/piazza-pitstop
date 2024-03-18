package com.heslingtonhustle.state;

public class Trigger {
    public boolean isInteractable;
    public String identifier;

    public Trigger(boolean isInteractable, String identifier) {
        this.isInteractable = isInteractable;
        this.identifier = identifier;
    }

    public Trigger() {
        this.isInteractable = false;
        this.identifier = "";
    }
}
