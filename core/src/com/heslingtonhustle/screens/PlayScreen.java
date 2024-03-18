package com.heslingtonhustle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.heslingtonhustle.HeslingtonHustleGame;
import com.heslingtonhustle.input.InputHandler;
import com.heslingtonhustle.input.KeyboardInputHandler;
import com.heslingtonhustle.map.MapManager;
import com.heslingtonhustle.renderer.Renderer;
import com.heslingtonhustle.state.Action;
import com.heslingtonhustle.state.State;

public class PlayScreen implements Screen {
    public HeslingtonHustleGame heslingtonHustleGame;

    InputMultiplexer inputMultiplexer;
    private final InputHandler inputHandler;
    private final State gameState;
    private final Renderer renderer;
    private final MapManager mapManager;
    private final PauseMenu pauseMenu;
    private boolean isPaused;

    public PlayScreen(HeslingtonHustleGame parentClass) {
        this.heslingtonHustleGame = parentClass;
        isPaused = false;

        float playerWidth = 0.6f; // This is in world units
        float playerHeight = 0.9f;
        mapManager = new MapManager();
        gameState = new State(mapManager, playerWidth, playerHeight);
        pauseMenu = new PauseMenu(this);
        renderer = new Renderer(gameState, mapManager, pauseMenu);

        inputHandler = new KeyboardInputHandler();
        addInputHandlers();

        gameState.pushWelcomeDialog();
    }
    @Override
    public void render(float delta) {
        Action action = inputHandler.getAction();
        if (handleDebugAction(action)) {
            action = inputHandler.getAction();
        }
        if (handlePauseAction(action)) {
            action = inputHandler.getAction();
        }
        if (!isPaused) {
            gameState.update(action, delta);
        }
        renderer.update();
    }

    private boolean handleDebugAction(Action action) {
        if (action == null) {
            return false;
        }
        switch (action) {
            case DEBUGGING_ACTION1:
                Vector2 playerPos = gameState.getPlayerPosition();
                Rectangle playerCollisionBox = gameState.getPlayerCollisionBox();
                Gdx.app.debug("DEBUG", "Position: "+playerPos.x+", "+playerPos.y);
                Gdx.app.debug("DEBUG", "CollisionBox: "+playerCollisionBox.getX()+", "+playerCollisionBox.getY());

                return true;
            case DEBUGGING_ACTION2:
                Gdx.app.debug("DEBUG", "Time: "+gameState.getDebugTime());
                return true;
            case DEBUGGING_ACTION3:
                gameState.pushTestDialog();
                return true;
            default:
                return false;
        }
    }

    private boolean handlePauseAction(Action action) {
        if (action == Action.PAUSE) {
            pause();
            return true;
        }
        return false;
    }

    private void addInputHandlers() {
        inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(inputHandler);
        inputMultiplexer.addProcessor(pauseMenu.GetStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
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
        isPaused = true;
        renderer.ShowPauseScreen();
    }

    @Override
    public void resume() {
        isPaused = false;
        renderer.HidePauseScreen();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapManager.dispose();
    }
}
