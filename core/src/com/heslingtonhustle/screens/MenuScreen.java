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
import com.heslingtonhustle.HeslingtonHustleGame;

public class MenuScreen implements Screen {
    private boolean DEBUG = false;
    private HeslingtonHustleGame heslingtonHustleGame;
    private Stage stage;
    private Table optionsTable;
    private Skin skin;


    public MenuScreen(HeslingtonHustleGame parentClass) {
        heslingtonHustleGame = parentClass;
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
        TextButton playGameButton = new TextButton("Play game", skin, "special");
        optionsTable.add(playGameButton).fillX().uniformX();
        optionsTable.row().pad(10, 0, 10, 0);
        playGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                heslingtonHustleGame.changeScreen(AvailableScreens.PlayScreen);
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

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
