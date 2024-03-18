package com.heslingtonhustle.state;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

public class Trigger {
    private boolean isInteractable;
    private String identifier;

    private final MapProperties mapProperties;

    public Trigger(MapProperties mapProperties) {
        this.mapProperties = mapProperties;
        isInteractable = mapProperties.containsKey("interactable");
        identifier = (String)mapProperties.get("identifier"); //TODO: null check
    }

    public int changeScore() {
        return getPropertyValue("score", false);
    }

    public int study() {
        return getPropertyValue("study", true);
    }

    public int recreation() {
        return getPropertyValue("recreation", true);
    }

    public int eat() {
        return getPropertyValue("eat", true);
    }

    private int getPropertyValue(String property, boolean mustBePositive) {
        if (mapProperties.containsKey(property)) {
            int value = (int)mapProperties.get(property);
            if (mustBePositive) {
                if (value > 0) {
                    return value;
                }
            } else {
                return value;
            }
        }
        return 0;
    }

    public String getNewMap() {
        if (mapProperties.containsKey("new_map")) {
            return (String)mapProperties.get("new_map");
        }
        return null;
    }

    public boolean canSleep() {
        if (mapProperties.containsKey("sleep")) {
            return (boolean)mapProperties.get("sleep");
        }
        return false;
    }

    public Vector2 getNewMapCoords() {
        return new Vector2((int)mapProperties.get("new_map_x"), (int)mapProperties.get("new_map_y"));
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public String getIdentifier() {
        return identifier;
    }
}
