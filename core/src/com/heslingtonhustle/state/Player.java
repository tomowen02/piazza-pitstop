package com.heslingtonhustle.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** Represents the player character */
public class Player {
    public static final float UP = 0f;
    public static final float LEFT = 90f;
    public static final float DOWN = 180f;
    public static final float RIGHT = 270f;

    public static final float SPEED = 0.5f;

    private Vector2 position;
    private Action movement = Action.STOP;
    private float facing = UP;

    public Player() {
        position = new Vector2();
    }

    /** The behaviour of the player character in a single frame. */
    public void update() {
        switch (movement) {
            case STOP:
                break;
            case MOVE_UP:
                facing = UP;
                position.y += SPEED;
                break;
            case MOVE_DOWN:
                facing = DOWN;
                position.y -= SPEED;
                break;
            case MOVE_LEFT:
                facing = LEFT;
                position.x -= SPEED;
                break;
            case MOVE_RIGHT:
                facing = RIGHT;
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

    public void setPosition(Vector2 position) {
        this.position = position;
        sprite.setPosition(position.x, position.y);
    }

    public void move(Action action) {
        movement = action;
    }

    public float getFacing() {
        return facing;
    }

    public void setInBounds(Vector2 mapSize) {
        position.x = MathUtils.clamp(position.x, 0, mapSize.x);
        position.y = MathUtils.clamp(position.y, 0, mapSize.y);
    }
}
