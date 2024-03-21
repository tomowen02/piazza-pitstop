package com.heslingtonhustle.state;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** Represents the player character */
public class Player {
    public static final float UP = 0f;
    public static final float LEFT = 90f;
    public static final float DOWN = 180f;
    public static final float RIGHT = 270f;

    public static final float SPEED = 0.15f;

    private Vector2 position;
    private Rectangle collisionBox;
    private Action movement = Action.STOP;
    private Facing facing = Facing.DOWN;
    private final float width;
    private final float height;

    public Player(float startingX, float startingY, float width, float height) {
        // The width and height are in game units
        this.width = width;
        this.height = height;
        setPosition(startingX,startingY);
    }

    /** The behaviour of the player character in a single frame. */
    public void update() {
        switch (movement) {
            case STOP:
                break;
            case MOVE_UP:
                facing = Facing.UP;
                setPosition(position.x, position.y+SPEED);
                break;
            case MOVE_DOWN:
                facing = Facing.DOWN;
                setPosition(position.x, position.y-SPEED);
                break;
            case MOVE_LEFT:
                facing = Facing.LEFT;
                setPosition(position.x-SPEED, position.y);
                break;
            case MOVE_RIGHT:
                facing = Facing.RIGHT;
                setPosition(position.x+SPEED, position.y);
                break;
            default:
                break;
        }
    }

    /** Returns a copy of the player's position */
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void setPosition(float x, float y) {
        if (position == null) {
            position = new Vector2();
        }
        position.x = x;
        position.y = y;

        if (collisionBox == null) {
            collisionBox = new Rectangle();
            collisionBox.setWidth(width);
            collisionBox.setHeight(height);
        }
        collisionBox.setPosition(position.x, position.y);
    }
    public void setPosition(Vector2 newPosition) {
        position = newPosition;
        collisionBox.setPosition(position);
    }

    public void move(Action action) {
        movement = action;
    }

    public Facing getFacing() {
        return facing;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public float getPlayerWidth() {
        return width;
    }

    public float getPlayerHeight() {
        return height;
    }

    public void setInBounds(Vector2 mapSize) {
        position.x = MathUtils.clamp(position.x, 0, mapSize.x);
        position.y = MathUtils.clamp(position.y, 0, mapSize.y);
    }

    public Action getMovement() {
        return movement;
    }
}
