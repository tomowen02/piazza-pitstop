package com.heslingtonhustle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heslingtonhustle.HeslingtonHustleGame;
import com.heslingtonhustle.input.InputHandler;
import com.heslingtonhustle.input.KeyboardInputHandler;
import com.heslingtonhustle.map.MapManager;
import com.heslingtonhustle.renderer.Renderer;
import com.heslingtonhustle.state.Action;
import com.heslingtonhustle.state.State;

public class PlayScreen implements Screen {
    public HeslingtonHustleGame heslingtonHustleGame;

    private final InputHandler inputHandler;
    private final State gameState;
    private final Renderer renderer;

    private final MapManager mapManager;

    public PlayScreen(HeslingtonHustleGame parentClass) {
        this.heslingtonHustleGame = parentClass;

        mapManager = new MapManager();
        gameState = new State();
        inputHandler = new KeyboardInputHandler();
        renderer = new Renderer(gameState, mapManager);

        Gdx.input.setInputProcessor(inputHandler);
    }
    @Override
    public void render(float delta) {
        Action action = inputHandler.getAction();
        if (handleDebugAction(action)) {
            action = inputHandler.getAction();
        }
        gameState.update(action);
        renderer.update();
    }

    private boolean handleDebugAction(Action action) {
        if (action == null) {
            return false;
        }
        switch (action) {
            case DEBUGGING_ACTION1:
                mapManager.loadMap("Maps/fieldMap.tmx");
                return true;
            case DEBUGGING_ACTION2:
                mapManager.loadMap("Maps/largeMap.tmx");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        renderer.windowResized(width, height);
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

    }
}
