package com.heslingtonhustle.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.map.MapManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Contains all data related to the logical state of the game. */
public class State {
    private final Player player;
    private final Clock clock;
    private final MapManager mapManager;
    private final DialogManager dialogManager;

    public State(MapManager mapManager, float playerWidth, float playerHeight) {
        player = new Player(32, 40, playerWidth, playerHeight);
        clock = new Clock();
        this.mapManager = mapManager;
        dialogManager = new DialogManager();
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action, float timeDelta) {
        if (action != null && !dialogManager.isEmpty()) {
            // We have an action that might be to do with the dialog
            handleDialogAction(action);
        } else if (action != null) {
            // We have a normal action
            player.move(action);
        }
        Vector2 previousPlayerPos = player.getPosition();
        player.update();
        if (mapManager.isCollision(player.getCollisionBox())) {
            player.setPosition(previousPlayerPos);
        }
        player.setInBounds(mapManager.getCurrentMapWorldDimensions());
        clock.update(timeDelta);
    }

    private void handleDialogAction(Action action) {
        switch (action) {
            case MOVE_UP:
                dialogManager.decreaseSelection();
                break;
            case MOVE_DOWN:
                dialogManager.increaseSelection();
                break;
            case INTERACT:
                dialogManager.submit();
        }
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    public Time getTime() {
        return clock.getTime();
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

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public void pushWelcomeDialog() {
        dialogManager.addDialog("Hello, welcome to the Heslington Hustle game by Pitstop Piazza!");
    }

    public void pushTestDialog() {
        // This is temporary
        List<String> options = new ArrayList<String>(Arrays.asList("Hello world", "Heyy", "What's up"));
        dialogManager.addDialog("Welcome to the game. Please select an options", options, selectedOption -> {
            switch (selectedOption) {
                case 0: // Option 0 selected
                    Gdx.app.debug("DEBUG", "Options 0 selected");
                    break;
                case 1: // Option 1 selected
                    Gdx.app.debug("DEBUG", "Options 1 selected");
                    break;
            }
        });
    }
}
