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
import com.heslingtonhustle.state.Action;
import com.heslingtonhustle.state.State;

public class PlayScreen implements Screen {
    public HeslingtonHustleGame heslingtonHustleGame;
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;
    public static final float GAME_WIDTH = 30*16;
    public static final float GAME_HEIGHT = 20*16;
    private final InputHandler inputHandler;
    private final State gameState;
    private final ExtendViewport viewport;
    private OrthographicCamera camera;
    private MapManager mapManager;
    private OrthogonalTiledMapRenderer renderer;
    SpriteBatch batch;
    Texture playerTexture;
    Sprite playerSprite;

    public PlayScreen(HeslingtonHustleGame parentClass) {
        this.heslingtonHustleGame = parentClass;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        viewport.setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.viewportWidth = GAME_WIDTH;
        camera.viewportHeight = GAME_HEIGHT;

        mapManager = new MapManager();
        mapManager.loadMap("Maps/fieldMap.tmx"); // This is just a test map that I made

        gameState = new State();
        inputHandler = new KeyboardInputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        TiledMap map = mapManager.getCurrentMap();
        renderer = new OrthogonalTiledMapRenderer(map);

        batch = new SpriteBatch();

        playerTexture = new Texture("circle.png");
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1f*16, 1f*16);
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
        Action action = inputHandler.getAction();
        if (action == Action.DEBUGGING_ACTION1) {
            mapManager.loadMap("Maps/caveMap.tmx");
            renderer.setMap(mapManager.getCurrentMap());
            return;
        } else if (action == Action.DEBUGGING_ACTION2) {
            mapManager.loadMap("Maps/fieldMap.tmx");
            renderer.setMap(mapManager.getCurrentMap());
            return;
        }
        gameState.update(action);
    }

    private void drawToScreen() {
        camera.position.set(gameState.getPlayerPosition(), 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.2f, 0.2f, 0.5f, 1);

        renderer.setView(camera);
        renderer.render();

        batch.begin();
        gameState.getPlayerSprite().draw(batch);
        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        playerTexture.dispose();
    }
}
