package com.heslingtonhustle.state;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.map.MapManager;

/** Contains all data related to the logical state of the game. */
public class State {
    private final Player player;
    private final Clock clock;
    private final MapManager mapManager;

    public State(MapManager mapManager, float playerWidth, float playerHeight) {
        player = new Player(32, 40, playerWidth, playerHeight);
        clock = new Clock();
        this.mapManager = mapManager;
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action, float timeDelta) {
        if (action != null) {
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
}
