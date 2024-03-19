package com.heslingtonhustle.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heslingtonhustle.state.DialogueManager;
import com.heslingtonhustle.state.State;

import java.util.List;

public class HudRenderer implements Disposable {
    private final State gameState;

    private final OrthographicCamera hudCamera;
    private final ScreenViewport viewport;

    private final DialogueManager dialogueManager;

    private final TextureAtlas textureAtlas;
    private final SpriteBatch batch;

    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;

    private final float PADDING = 50f;

    private final Sprite clockSprite;
    private final float clockSize = 100;
    private TextureRegion clockTexture;
    private final Sprite calendarSprite;
    private TextureRegion calendarTexture;
    private final Sprite interactSprite;
    private TextureManager textureManager;

    public HudRenderer(State gameState, TextureAtlas textureAtlas){
        this.gameState = gameState;

        hudCamera = new OrthographicCamera();
        viewport = new ScreenViewport(hudCamera);

        dialogueManager = gameState.getDialogueManager();

        this.textureAtlas = textureAtlas;
        batch = new SpriteBatch();

        font = new BitmapFont();

        shapeRenderer = new ShapeRenderer();

        textureManager = new TextureManager();
        addAnimations();

        clockTexture = textureAtlas.findRegion("morningClock"); // This is the default but will be overwritten
        clockSprite = new Sprite();
        clockSprite.setSize(clockSize,clockSize);

        calendarTexture = textureAtlas.findRegion("calendar-empty"); // This is the default but will be overwritten
        calendarSprite = new Sprite();
        calendarSprite.setSize(clockSize, clockSize);

        interactSprite = new Sprite();
        interactSprite.setSize(128, 34);
        interactSprite.setRegion(textureManager.retrieveTexture("interact"));

    }

    public void render(){
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.setProjectionMatrix(hudCamera.combined);

        setClockTexture();
        setCalendarTexture();
        setInteractTexture();

        batch.begin();
        clockSprite.draw(batch);
        calendarSprite.draw(batch);
        if (gameState.isInteractionPossible()) {
            interactSprite.draw(batch);
        }
        batch.end();

        showDialogue();
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

    private void setInteractTexture() {
        interactSprite.setRegion(textureManager.retrieveTexture("interact"));
    }

    private void showDialogue() {
        if (dialogueManager.isEmpty()) {
            // No dialogue box to show
            return;
        }
        String message = dialogueManager.getMessage();
        List<String> options = dialogueManager.getOptions();
        int selectedOption = dialogueManager.getSelectedOption();

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
        float optionY = y + height - 130; // Starting Y position for options
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

        Array<TextureAtlas.AtlasRegion> interact = textureAtlas.findRegions("interact");
        textureManager.addAnimation("interact", interact, 0.4f);
    }

    public void resize(int width, int height) {
        float clockX = Gdx.graphics.getWidth() - (clockSize+PADDING);
        float clockY = PADDING;
        clockSprite.setPosition(clockX,clockY);

        float calendarX = PADDING;
        float calendarY = PADDING;
        calendarSprite.setPosition(calendarX, calendarY);

        float interactX = PADDING;
        float interactY = height - PADDING - 34;
        interactSprite.setPosition(interactX, interactY);

        
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
