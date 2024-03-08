package com.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;
    private final InputHandler inputHandler;
    private final State gameState;
    private final FitViewport viewport;
    SpriteBatch batch;
    Texture texture;
    Texture texture2;

    public PlayScreen() {
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport.setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport.getCamera().viewportWidth = SCREEN_WIDTH;
        viewport.getCamera().viewportHeight = SCREEN_HEIGHT;

        gameState = new State();
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        batch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");
        texture2 = new Texture("circle.png");
    }
    @Override
    public void render(float delta) {
        handleInput();
        updateState();
        drawToScreen();
    }

    private void handleInput() {
    }

    private void updateState() {
        Action action = inputHandler.getBufferedAction();
        gameState.update(action);
    }

    private void drawToScreen() {
        viewport.getCamera().position.set(gameState.getPlayerPosition(), 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        ScreenUtils.clear(0.2f, 0.2f, 0.5f, 1);

        batch.begin();
        batch.draw(texture, 0, 0);
        batch.draw(texture2, gameState.getPlayerPosition().x, gameState.getPlayerPosition().y);
        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        texture2.dispose();
    }
}
