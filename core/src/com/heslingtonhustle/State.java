package com.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

/** Contains all data related to the logical state of the game. */
public class State {
    private final Player player;

    public State() {
        player = new Player();
    }

    /** Given an Action, apply that action to the state. */
    public void update(Action action) {
        if (action != null) {
            player.move(action);
        }
        player.update();
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }
    public Sprite getPlayerSprite() { return player.getSprite(); }
}
