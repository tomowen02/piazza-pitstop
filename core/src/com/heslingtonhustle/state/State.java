package com.heslingtonhustle.state;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.map.MapManager;

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
        if (action != null && dialogManager.isShowing()) {
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
        DialogBox dialog = dialogManager.showDialog();
        switch (action) {
            case MOVE_UP:
                dialog.decreaseSelection();
                break;
            case MOVE_DOWN:
                dialog.increaseSelection();
                break;
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

    public void pushDialog(DialogBox dialogBox) {
        // This is temporary
        dialogManager.add(dialogBox);
    }
}
