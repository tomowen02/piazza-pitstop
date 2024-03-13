package com.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.state.Action;

/** Represents the player character */
public class Player {
    public static final float SPEED = 2f;
    private Vector2 position;
    private Action movement = Action.STOP;
    private Direction facing = Direction.DOWN;
    private Sprite sprite;
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
        sprite = new Sprite(playerDownTexture);
        sprite.setSize(1f*16, 1f*16);
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
        sprite.setPosition(position.x, position.y);
    }

    /** Returns a copy of the player's position */
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void move(Action action) {
        movement = action;
    }

    public Sprite getSprite() {
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
        sprite.setTexture(currentTexture);
        sprite.setPosition(position.x, position.y);
        return sprite;
    }
}
