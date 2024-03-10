package com.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/** Represents the player character */
public class Player {
    public static final float SPEED = 2f;
    private Vector2 position;
    private Action movement = Action.STOP;
    private Direction facing = Direction.DOWN;
    private Texture playerUpTexture;
    private Texture playerDownTexture;
    private Texture playerLeftTexture;
    private Texture playerRightTexture;

    public Player() {
        position = new Vector2();
        playerUpTexture = new Texture("Graphics/Entities/Player1/up.png");
        playerDownTexture = new Texture("Graphics/Entities/Player1/down.png");
        playerLeftTexture = new Texture("Graphics/Entities/Player1/left.png");
        playerRightTexture = new Texture("Graphics/Entities/Player1/right.png");
    }

    /** The behaviour of the player character in a single frame. */
    public void update() {
        switch (movement) {
            case STOP:
                break;
            case MOVE_UP:
                if (facing != Direction.UP) { facing = Direction.UP; };
                position.y += SPEED;
                break;
            case MOVE_DOWN:
                if (facing != Direction.DOWN) { facing = Direction.DOWN; };
                position.y -= SPEED;
                break;
            case MOVE_LEFT:
                if (facing != Direction.LEFT) { facing = Direction.LEFT; };
                position.x -= SPEED;
                break;
            case MOVE_RIGHT:
                if (facing != Direction.RIGHT) { facing = Direction.RIGHT; };
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

    public Texture getTexture() {
        Texture currentTexture;
        switch (facing) {
            case UP:
                currentTexture = playerUpTexture;
                break;
            case DOWN:
                currentTexture = playerDownTexture;
                break;
            case LEFT:
                currentTexture = playerLeftTexture;
                break;
            case RIGHT:
                currentTexture = playerRightTexture;
                break;
            default:
                currentTexture = null;
        }
        return currentTexture;
    }
}
