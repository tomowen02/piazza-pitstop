package com.heslingtonhustle.state;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

/**
 * A trigger that can be populated from an external editor. Defines behaviour when the player interacts with an object.
 */
public class Trigger {

    private final int id;
    private final String activity;

    private final MapProperties mapProperties;

    public Trigger(MapProperties mapProperties) {
        this.mapProperties = mapProperties;
        id = (int)mapProperties.get("id");

        activity = mapProperties.containsKey("activity") ? (String)mapProperties.get("activity") : null;
    }

    /**
     * @return The activity to perform. Null if none specified.
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @return The value assigned to the activity. 0 if no activity.
     */
    public int getValue() {
        if (activity != null) {
            return getPropertyValue(activity, true);
        }
        return 0;
    }

    public int getEnergyCost() {
        if (activity != null) {
            return getPropertyValue("energy_cost", false);
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

    public String getSuccessMessage() {
        if (mapProperties.containsKey("success_message")) {
            return (String)mapProperties.get("success_message");
        }
        return null;
    }

    public String getFailedMessage() {
        if (mapProperties.containsKey("failed_message")) {
            return (String)mapProperties.get("failed_message");
        }
        return null;
    }
}
