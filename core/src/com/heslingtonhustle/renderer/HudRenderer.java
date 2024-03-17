package com.heslingtonhustle.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heslingtonhustle.state.State;

public class HudRenderer implements Disposable {
    private State gameState;

    private OrthographicCamera hudCamera;
    private ScreenViewport viewport;

    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    private float PADDING = 50f;

    private Sprite clockSprite;
    private float clockSize = 100f;
    private TextureRegion clockTexture;

    public HudRenderer(State gameState, TextureAtlas textureAtlas){
        this.gameState = gameState;

        hudCamera = new OrthographicCamera();
        viewport = new ScreenViewport(hudCamera);

        this.textureAtlas = textureAtlas;
        batch = new SpriteBatch();

        clockTexture = textureAtlas.findRegion("morningClock");
        clockSprite = new Sprite();
        clockSprite.setSize(100,100);
    }

    public void render(){
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);

        setClockTexture();

        batch.begin();
        clockSprite.draw(batch);
        batch.end();
    }

    private void setClockTexture() {
        switch (gameState.getTime()) {
            case MORNING:
                clockTexture = textureAtlas.findRegion("morningClock");
                break;
            case AFTERNOON:
                clockTexture = textureAtlas.findRegion("afternoonClock");
                break;
            case EVENING:
                clockTexture = textureAtlas.findRegion("eveningClock");
                break;
            case NIGHT:
                clockTexture = textureAtlas.findRegion("nightClock");
                break;
        }
        clockSprite.setRegion(clockTexture);
    }

    public void resize(int width, int height) {
        float clockX = Gdx.graphics.getWidth() - (clockSize+PADDING);
        float clockY = PADDING;
        clockSprite.setPosition(clockX,clockY);

        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
