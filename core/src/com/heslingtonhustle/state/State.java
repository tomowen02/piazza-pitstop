package com.heslingtonhustle.state;

import com.badlogic.gdx.math.Vector2;

/** Contains all data related to the logical state of the game. */
public class State {
    private final Player player;
    private final Clock clock;

    public State() {
        player = new Player();
        clock = new Clock();
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action, float delta) {
        if (action != null) {
            player.move(action);
        }
        player.update();
        clock.update(delta);
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
}
