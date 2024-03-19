package com.heslingtonhustle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heslingtonhustle.state.State;

public class PauseMenu {
    private final boolean DEBUG = false;
    private final Screen playScreen;
    private final Stage stage;
    private State gameState;
    private Table optionsTable;
    private final Skin skin;
    private boolean isVisible;

    public PauseMenu(Screen parentClass, State gameState) {
        playScreen = parentClass;
        isVisible = false;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameState = gameState;

        skin = new Skin(Gdx.files.internal("Graphics/UI/Skin/plain-james-ui.json"));
        createTable();
        addOptions();
    }

    private void createTable() {
        optionsTable = new Table();
        optionsTable.setFillParent(true);
        optionsTable.setDebug(DEBUG);
        stage.addActor(optionsTable);
    }

    private void addOptions() {
        TextButton resumeButton = new TextButton("Resume", skin, "special");
        optionsTable.add(resumeButton).fillX().uniformX();
        optionsTable.row().pad(10, 0, 10, 0);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                playScreen.resume();
            }
        });

        optionsTable.row();
        TextButton mainMenuButton = new TextButton("Return to main menu", skin, "special");
        optionsTable.add(mainMenuButton).fillX().uniformX();
        optionsTable.row().pad(10, 0, 10, 0);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gameState.setGameOver();
            }
        });

        optionsTable.row();
        TextButton exitButton = new TextButton("Exit", skin, "special");
        optionsTable.add(exitButton).fillX().uniformX();
        optionsTable.row().pad(10, 0, 10, 0);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    public void ShowPauseMenu() {
        isVisible = true;
        optionsTable.setVisible(true);
    }

    public void HidePauseMenu() {
        isVisible = false;
        optionsTable.setVisible(false);
    }

    public void render() {
        if (isVisible) {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }
    }

    public Stage GetStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getCamera().viewportWidth = Gdx.graphics.getWidth();
        stage.getCamera().viewportHeight = Gdx.graphics.getHeight();
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
