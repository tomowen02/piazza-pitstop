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

public class PauseMenu {
    private boolean DEBUG = false;
    private Screen playScreen;
    private Stage stage;
    private Table optionsTable;
    private Skin skin;
    private boolean isVisible;

    public PauseMenu(Screen parentClass) {
        playScreen = parentClass;
        isVisible = false;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

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

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
