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

    public String getNewMap() {
        if (mapProperties.containsKey("new_map")) {
            return (String)mapProperties.get("new_map");
        }
        else return null;
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
