package com.heslingtonhustle.renderer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class TextureManager {

    private HashMap<String, Animation<TextureRegion>> animations;
    private HashMap<String, TextureRegion> staticTextures;
    private float animationTime;

    public TextureManager() {
        animationTime = 0f;
        animations = new HashMap<String, Animation<TextureRegion>>();
        staticTextures = new HashMap<String, TextureRegion>();
    }

    public TextureRegion retrieveTexture(String key) {
        animationTime += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> animation = animations.get(key);
        if (animation != null) {
            // We have an animation for this key. Return the current frame
            return animation.getKeyFrame(animationTime, true);
        }
        TextureRegion texture = staticTextures.get(key);
        if (texture != null) {
            return texture;
        }
        throw new NullPointerException("Texture has not been added");
    }

    public void addAnimation(String animationKey, Array<TextureAtlas.AtlasRegion> textureRegions) {
        animations.put(animationKey, new Animation<TextureRegion>(0.1f, textureRegions));
    }

    public void addTexture(String textureKey, TextureRegion texture) {
        staticTextures.put(textureKey, texture);
    }
}
