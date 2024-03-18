package com.heslingtonhustle.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heslingtonhustle.state.DialogBox;
import com.heslingtonhustle.state.DialogManager;
import com.heslingtonhustle.state.State;

import java.util.List;

public class HudRenderer implements Disposable {
    private State gameState;

    private OrthographicCamera hudCamera;
    private ScreenViewport viewport;

    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private float PADDING = 50f;

    private Sprite clockSprite;
    private float clockSize = 75;
    private TextureRegion clockTexture;

    public HudRenderer(State gameState, TextureAtlas textureAtlas){
        this.gameState = gameState;

        hudCamera = new OrthographicCamera();
        viewport = new ScreenViewport(hudCamera);

        this.textureAtlas = textureAtlas;
        batch = new SpriteBatch();

        font = new BitmapFont();

        shapeRenderer = new ShapeRenderer();

        clockTexture = textureAtlas.findRegion("morningClock");
        clockSprite = new Sprite();
        clockSprite.setSize(100,100);
    }

    public void render(){
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.setProjectionMatrix(hudCamera.combined);

        setClockTexture();

        batch.begin();
        clockSprite.draw(batch);
        batch.end();

        checkDialog();
    }

    private void setClockTexture() {
        switch (gameState.getTime()) {
            case MORNING:
                clockTexture = textureAtlas.findRegion("clock-morning");
                break;
            case AFTERNOON:
                clockTexture = textureAtlas.findRegion("clock-afternoon");
                break;
            case EVENING:
                clockTexture = textureAtlas.findRegion("clock-evening");
                break;
            case NIGHT:
                clockTexture = textureAtlas.findRegion("clock-night");
                break;
        }
        clockSprite.setRegion(clockTexture);
    }

    private void checkDialog() {
        DialogManager dialogManager = gameState.getDialogManager();
        if (dialogManager.isEmpty()) {
            return;
        }
        DialogBox dialog = dialogManager.showDialog();

        showDialog(dialog, 250);
    }

    private void showDialog(DialogBox dialog, float height) {
        float x = 100;
        float y = 100;
        float width = Gdx.graphics.getWidth() - x*2;

        // Start ShapeRenderer for the background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();

        font.setColor(Color.WHITE);
        font.draw(batch, dialog.getMessage(), x + 20, y + height - 20, width - 40, Align.left, true);

        // Draw options
        List<String> options = dialog.getOptions();
        float optionY = y + height - 60; // Starting Y position for options
        for (int i = 0; i < options.size(); i++) {
            String optionPrefix = (dialog.getSelectedOption() == i) ? "> " : "  ";
            font.draw(batch, optionPrefix + options.get(i), x + 20, optionY, width - 40, Align.left, false);
            optionY -= 20; // Move up for the next option
        }

        batch.end();
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
