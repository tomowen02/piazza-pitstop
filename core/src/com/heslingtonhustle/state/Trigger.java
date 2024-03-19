package com.heslingtonhustle.state;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

public class Trigger {

    private final int id;
    private final String activity;

    private final MapProperties mapProperties;

    public Trigger(MapProperties mapProperties) {
        this.mapProperties = mapProperties;
        id = (int)mapProperties.get("id");

        activity = mapProperties.containsKey("activity") ? (String)mapProperties.get("activity") : null;
    }

    public String getActivity() {
        return activity;
    }

    public int getValue() {
        if (activity != null) {
            return getPropertyValue(activity, true);
        }
        return 0;
    }

    public int changeScore() {
        return getPropertyValue("score", false);
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
}
