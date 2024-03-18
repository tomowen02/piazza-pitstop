package com.heslingtonhustle.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heslingtonhustle.state.DialogManager;
import com.heslingtonhustle.state.State;

import java.util.LinkedList;
import java.util.List;

public class HudRenderer implements Disposable {
    private State gameState;

    private OrthographicCamera hudCamera;
    private ScreenViewport viewport;

    private DialogManager dialogManager;

    private TextureAtlas textureAtlas;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private float PADDING = 50f;

    private Sprite clockSprite;
    private float clockSize = 100;
    private TextureRegion clockTexture;
    private Sprite calendarSprite;
    private TextureRegion calendarTexture;
    private TextureManager textureManager;

    public HudRenderer(State gameState, TextureAtlas textureAtlas){
        this.gameState = gameState;

        hudCamera = new OrthographicCamera();
        viewport = new ScreenViewport(hudCamera);

        dialogManager = gameState.getDialogManager();

        this.textureAtlas = textureAtlas;
        batch = new SpriteBatch();

        font = new BitmapFont();

        shapeRenderer = new ShapeRenderer();

        textureManager = new TextureManager();
        addAnimations();

        clockTexture = textureAtlas.findRegion("morningClock");
        clockSprite = new Sprite();
        clockSprite.setSize(clockSize,clockSize);


        calendarTexture = textureAtlas.findRegion("calendar-empty");
        calendarSprite = new Sprite();
        calendarSprite.setSize(clockSize, clockSize);
    }

    public void render(){
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.setProjectionMatrix(hudCamera.combined);

        setClockTexture();
        setCalendarTexture();

        batch.begin();
        clockSprite.draw(batch);
        calendarSprite.draw(batch);
        batch.end();

        showDialog();
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
                clockTexture = textureManager.retrieveTexture("clock-night");
                break;
        }
        clockSprite.setRegion(clockTexture);
    }

    private void setCalendarTexture() {
        switch (gameState.getDay()) {
            case 1:
                calendarTexture = textureAtlas.findRegion("calendar-1");
                break;
            case 2:
                calendarTexture = textureAtlas.findRegion("calendar-2");
                break;
            case 3:
                calendarTexture = textureAtlas.findRegion("calendar-3");
                break;
            case 4:
                calendarTexture = textureAtlas.findRegion("calendar-4");
                break;
            case 5:
                calendarTexture = textureAtlas.findRegion("calendar-5");
                break;
            case 6:
                calendarTexture = textureAtlas.findRegion("calendar-6");
                break;
            case 7:
                calendarTexture = textureAtlas.findRegion("calendar-7");
                break;
            default:
                calendarTexture = textureAtlas.findRegion("calendar-empty");
        }
        calendarSprite.setRegion(calendarTexture);
    }

    private void showDialog() {
        if (dialogManager.isEmpty()) {
            // No dialog box to show
            return;
        }
        String message = dialogManager.getMessage();
        List<String> options = dialogManager.getOptions();
        int selectedOption = dialogManager.getSelectedOption();

        float x = 100;
        float y = 100;
        float height = 250;
        float width = Gdx.graphics.getWidth() - x*2;

        // Start ShapeRenderer for the background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();

        font.setColor(Color.WHITE);
        font.draw(batch, message, x + 20, y + height - 20, width - 40, Align.left, true);

        // Draw options
        float optionY = y + height - 80; // Starting Y position for options
        for (int i = 0; i < options.size(); i++) {
            String optionPrefix = (selectedOption == i) ? "> " : "  ";
            font.draw(batch, optionPrefix + options.get(i), x + 20, optionY, width - 40, Align.left, false);
            optionY -= 20; // Move up for the next option
        }

        batch.end();
    }

    private void addAnimations() {
        TextureRegion[] clockAnimationFrames = new TextureRegion[2];
        clockAnimationFrames[0] = clockTexture = textureAtlas.findRegion("clock-night");
        clockAnimationFrames[1] = clockTexture = textureAtlas.findRegion("clock-red");
        textureManager.addAnimation("clock-night", clockAnimationFrames, 0.4f);
    }

    public void resize(int width, int height) {
        float clockX = Gdx.graphics.getWidth() - (clockSize+PADDING);
        float clockY = PADDING;
        clockSprite.setPosition(clockX,clockY);

        float calendarX = PADDING;
        float calendarY = PADDING;
        calendarSprite.setPosition(calendarX, calendarY);

        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
