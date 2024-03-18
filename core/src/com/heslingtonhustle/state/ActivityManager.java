package com.heslingtonhustle.state;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.heslingtonhustle.activities.Activity;

public class ActivityManager {
    private HashMap<String, Activity> activities;

    public ActivityManager() {
        activities = new HashMap<>();
    }

    public void addActivity(String identifier, Activity activity) {
        activities.put(identifier, activity);
    }

    public void startActivity(String identifier) {
        Activity activity = activities.get(identifier);
        if (activity != null) {
            activity.start();
        } else {
            Gdx.app.debug("DEBUG", "There is no activity with the identifier " + identifier);
        }
    }


}
