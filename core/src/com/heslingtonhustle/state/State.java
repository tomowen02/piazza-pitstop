package com.heslingtonhustle.state;

import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.map.MapManager;

/** Contains all data related to the logical state of the game. */
public class State {
    private final Player player;
    private final Clock clock;
    private final MapManager mapManager;

    public State(MapManager mapManager) {
        player = new Player();
        clock = new Clock();
        this.mapManager = mapManager;
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action, float delta) {
        if (action != null) {
            player.move(action);
        }
        player.update();
        clock.update(delta);
        player.setInBounds(mapManager.getCurrentMapWorldDimensions());
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }
    public void setPlayerPosition(Vector2 position) {
        player.setPosition(position);
    }

    public Time getTime() {
        return clock.getTime();
    }
    public String getDebugTime() {
        return clock.getDebugString();
    }
    public float getPlayerFacing() {
        return player.getFacing();
    }
}
