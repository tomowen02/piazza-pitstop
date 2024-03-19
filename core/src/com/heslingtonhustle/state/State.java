package com.heslingtonhustle.state;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.map.MapManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Contains all data related to the logical state of the game. */
public class State {
    private static final int MAX_DAYS = 7;
    private final Player player;
    private final Clock clock;
    private final MapManager mapManager;
    private final DialogueManager dialogueManager;
    private final ActivityManager activityManager;
    private Activities activities;
    private Trigger currentTrigger;
    private boolean isInteractionPossible;
    private int score;

    public State(MapManager mapManager, float playerWidth, float playerHeight) {
        player = new Player(38.25f, 57.25f, playerWidth, playerHeight);
        clock = new Clock();
        this.mapManager = mapManager;
        dialogueManager = new DialogueManager();

        activities = new HashMap<>();
        activities.put("eat", new Activity(2));
        activities.put("recreation", new Activity(3));
        activities.put("study", new Activity(2));
        activities.put("sleep", new Activity());

        score = 0;
        currentTrigger = null;
        isInteractionPossible = false;
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action, float timeDelta) {
        currentTrigger = mapManager.getTrigger(player.getCollisionBox());
        if (action != null) {
            handleAction(action);
        }
        Vector2 previousPlayerPos = player.getPosition();
        player.update();
        if (mapManager.isCollision(player.getCollisionBox())) {
            player.setPosition(previousPlayerPos);
        }
        player.setInBounds(mapManager.getCurrentMapWorldDimensions());
        clock.increaseTime(timeDelta);
    }

    private void handleAction(Action action) {
        if (!dialogueManager.isEmpty()) {
            // A dialogue box is currently being displayed
            handleDialogueAction(action);
        } else if (action == Action.INTERACT) {
            handleInteraction();
        } else {
            // We have a normal action
            player.move(action);
        }
    }

    private void handleDialogueAction(Action action) {
        switch (action) {
            case MOVE_UP:
                dialogueManager.decreaseSelection();
                break;
            case MOVE_DOWN:
                dialogueManager.increaseSelection();
                break;
            case INTERACT:
                dialogueManager.submit();
        }
    }

    private void handleInteraction() {
        Trigger trigger = mapManager.getTrigger(player.getCollisionBox());
        if (currentTrigger == null) {
            return;
        }

        if (trigger.getNewMap() != null) {
            mapManager.loadMap("Maps/" + trigger.getNewMap());
            player.setPosition(trigger.getNewMapCoords());
        }

        score += trigger.changeScore();

        if (trigger.canSleep()) {
            advanceDay();
            Activity activity  = activities.get("sleep");
            if (activity != null) {
                activity.increaseValue(1);
            }
        }
        
        String s = trigger.getActivity();
        if (s != null) {
            int i = trigger.getValue();
            if (!activities.containsKey(s)) {
                activities.put(s, new Activity()); // Useful feature?
            }
            activities.get(s).increaseValue(i);
        }
    }

    private void advanceDay() {
        if (clock.getDay() == MAX_DAYS) {
            dialogueManager.addDialogue("Game Over");
            return;
        }

        Activity study = activities.get("study");
        if (study.getTimesPerformedToday() == 2) {
            study.changeMaxTimesPerDay(1);
        }

        for (Activity activity : activities.values()) {
            activity.dayAdvanced();
        }

        clock.incrementDay();
    }

    //Debug function
    public void printActivities() {
        for (String s : activities.keySet()) {
            System.out.println(s + " count: " + activities.get(s).getCount());
            System.out.println(s + " value: " + activities.get(s).getValue());
        }
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    public Time getTime() {
        return clock.getTime();
    }
    public int getDay() {
        return clock.getDay();
    }
    public String getDebugTime() {
        return clock.getDebugString();
    }
    public Facing getPlayerFacing() {
        return player.getFacing();
    }

    public Rectangle getPlayerCollisionBox() {
        return player.getCollisionBox();
    }
    public float getPlayerWidth() {
        return player.getPlayerWidth();
    }

    public float getPlayerHeight() {
        return player.getPlayerHeight();
    }

    public Action getPlayerMovement() {
        return player.getMovement();
    }

    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public void pushWelcomeDialogue() {
        dialogueManager.addDialogue("Hello, welcome to the Heslington Hustle game by Pitstop Piazza! " +
                "Note: if the scaling is weird and very zoomed out, try restarting, and only go fullscreen after pressing play");
    }

    public void pushTestDialogue() {
        // This is temporary
        List<String> options = new ArrayList<>(Arrays.asList("Increment day", "Decrement day", "Set time speed to VERY FAST", "Set time speed to normal", "Close"));
        dialogueManager.addDialogue("This is the debugging console. Please select an option", options, selectedOption -> {
            switch (selectedOption) {
                case 0: // Option 0 selected
                    clock.incrementDay();
                    break;
                case 1: // Option 1 selected
                    clock.decrementDay();
                    break;
                case 2:
                    clock.setSpeed(15);
                    break;
                case 3:
                    clock.setSpeed(6);
                    break;
            }
        });
    }

    public int getScore() {
        return score;
    }
    public void increaseScore(int increase) {
        score += increase;
    }

    public boolean isInteractionPossible() {
        if (currentTrigger == null) {
            return false;
        }
        return currentTrigger.isInteractable();
    }
}
