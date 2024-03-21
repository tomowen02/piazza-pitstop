package com.heslingtonhustle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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

        // The player size is in world units
        float playerWidth = 0.6f;
        float playerHeight = 0.9f;

        mapManager = new MapManager();
        gameState = new State(mapManager, playerWidth, playerHeight);
        pauseMenu = new PauseMenu(this, gameState);
        renderer = new Renderer(gameState, mapManager, pauseMenu);

        inputHandler = new KeyboardInputHandler();
        addInputHandlers();

        gameState.pushWelcomeDialogue();
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

        if (gameState.isGameOver()) {
            heslingtonHustleGame.changeScreen(AvailableScreens.MenuScreen);
        }
    }

    private boolean handleDebugAction(Action action) {
        if (action == null) {
            return false;
        }
        // One of the debugging keys have been pressed. By default, these are ',' '.' '/' keys
        switch (action) {
            case DEBUGGING_ACTION1:
                gameState.printActivities();
                return true;
            case DEBUGGING_ACTION2:
                Gdx.app.debug("DEBUG", "Time: "+gameState.getDebugTime());
                return true;
            case DEBUGGING_ACTION3:
                gameState.pushTestDialogue();
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
        // We use an input multiplexer so that we can handle multiple sources of inputs at once
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
        renderer.dispose();
        pauseMenu.dispose();
    }
}
