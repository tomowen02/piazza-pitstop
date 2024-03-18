package com.heslingtonhustle.state;

import java.util.HashMap;

public class ActivityManager {
    private final HashMap<String, Activity> activities;

    public ActivityManager() {
        activities = new HashMap<>();
    }

    public void addActivity(String identifier, Activity activity) {
        activities.put(identifier, activity);
    }

    public void startActivity(String identifier) {
        Activity activity = activities.get(identifier);
        activity.start();
    }


}
