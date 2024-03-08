package com.heslingtonhustle;

import com.badlogic.gdx.math.Vector2;

/** Represents the player character */
public class Player {
    public static final int SPEED = 5;
    private Vector2 position;
    private Action movement = Action.STOP;

    public Player() {
        position = new Vector2();
    }

    /** The behaviour of the player character in a single frame. */
    public void update() {
        switch (movement) {
            case STOP:
                break;
            case MOVE_UP:
                position.y += SPEED;
                break;
            case MOVE_DOWN:
                position.y -= SPEED;
                break;
            case MOVE_LEFT:
                position.x -= SPEED;
                break;
            case MOVE_RIGHT:
                position.x += SPEED;
                break;
            default:
                break;
        }
    }

    /** Returns a copy of the player's position */
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void move(Action action) {
        movement = action;
    }
}
