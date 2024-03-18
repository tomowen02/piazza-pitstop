package com.heslingtonhustle.renderer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.heslingtonhustle.state.Action;
import com.heslingtonhustle.state.Facing;

public class CharacterRenderer {
    private final TextureManager characterTextures;
    private final Sprite characterSprite;
    private final TextureAtlas textureAtlas;
    private final String textureRegionPrefix;


    public CharacterRenderer(float width, float height, TextureAtlas textureAtlas, String textureRegionPrefix) {
        this.textureAtlas = textureAtlas;
        this.textureRegionPrefix = textureRegionPrefix;

        characterTextures = new TextureManager();
        addCharacterTextures();
        characterSprite = new Sprite(characterTextures.retrieveTexture("idle-down"));
        characterSprite.setSize(width, height);
        characterSprite.setOriginCenter();
    }

    public void render(SpriteBatch batch, float x, float y, Facing direction, Action action) {
        String textureKey = getTextureKey(direction, action);
        characterSprite.setRegion(characterTextures.retrieveTexture(textureKey));
        characterSprite.setPosition(x, y);
        characterSprite.draw(batch);
    }

    private void addCharacterTextures() {
        // First by adding the static textures (when the character is idle)
        TextureRegion idleLeft = textureAtlas.findRegion(textureRegionPrefix+"-idle-left");
        characterTextures.addTexture("idle-left", idleLeft);
        TextureRegion idleRight = textureAtlas.findRegion(textureRegionPrefix+"-idle-right");
        characterTextures.addTexture("idle-right", idleRight);
        TextureRegion idleUp = textureAtlas.findRegion(textureRegionPrefix+"-idle-up");
        characterTextures.addTexture("idle-up", idleUp);
        TextureRegion idleDown = textureAtlas.findRegion(textureRegionPrefix+"-idle-down");
        characterTextures.addTexture("idle-down", idleDown);

        // Now we need to add the textures that are used in animation.
        // the findRegions() function will find all areas of the atlas that have the same name and a number suffix
        // For example findRegions("walking_left") will find "walking_left_00", "walking_left_01", "walking_left_02 etc.
        Array<TextureAtlas.AtlasRegion> walkingLeft = textureAtlas.findRegions(textureRegionPrefix+"-walking-left");
        characterTextures.addAnimation("walking-left", walkingLeft);
        Array<TextureAtlas.AtlasRegion> walkingRight = textureAtlas.findRegions(textureRegionPrefix+"-walking-right");
        characterTextures.addAnimation("walking-right", walkingRight);
        Array<TextureAtlas.AtlasRegion> walkingUp = textureAtlas.findRegions(textureRegionPrefix+"-walking-up");
        characterTextures.addAnimation("walking-up", walkingUp);
        Array<TextureAtlas.AtlasRegion> walkingDown = textureAtlas.findRegions(textureRegionPrefix+"-walking-down");
        characterTextures.addAnimation("walking-down", walkingDown);
    }

    private String getTextureKey(Facing direction, Action action) {
        String textureKey = "idle-down";
        switch (action) {
            case MOVE_LEFT:
                textureKey = "walking-left";
                break;
            case MOVE_RIGHT:
                textureKey = "walking-right";
                break;
            case MOVE_UP:
                textureKey = "walking-up";
                break;
            case MOVE_DOWN:
                textureKey = "walking-down";
                break;
            default:
                // Must be idle
                switch (direction) {
                    case LEFT:
                        textureKey = "idle-left";
                        break;
                    case RIGHT:
                        textureKey = "idle-right";
                        break;
                    case UP:
                        textureKey = "idle-up";
                        break;
                    case DOWN:
                        break;
                    default:
                        break;
                }
        }

        return textureKey;
    }
}
