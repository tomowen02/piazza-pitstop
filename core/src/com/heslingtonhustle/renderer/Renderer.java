package com.heslingtonhustle.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heslingtonhustle.map.MapManager;
import com.heslingtonhustle.state.State;

public class Renderer implements Disposable {

    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    private final ExtendViewport viewport;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final State gameState;
    private SpriteBatch batch;
    private Sprite playerSprite;
    private TextureAtlas textureAtlas;

    private final MapManager mapManager;

    private final TextureRegion playerTexture;
    private final TextureRegion buildingTexture;

    public Renderer(State state, MapManager mapManager)
    {
        gameState = state;
        camera = new OrthographicCamera();
        this.mapManager = mapManager;
        mapManager.loadMap("Maps/fieldMap.tmx"); // This is just a test map that I made
        TiledMap map = mapManager.getCurrentMap();
        batch = new SpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        viewport = new ExtendViewport(mapManager.getCurrentMapPixelDimensions().x, mapManager.getCurrentMapPixelDimensions().y, camera);
        viewport.setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        textureAtlas = new TextureAtlas("pack.atlas");
        playerTexture = textureAtlas.findRegion("circle");
        buildingTexture = textureAtlas.findRegion("triangle");

        playerSprite = new Sprite(playerTexture);
        playerSprite.setScale(0.5f, 0.5f);
    }

    public void update() {
        /* float x = MathUtils.clamp(gameState.getPlayerPosition().x, camera.viewportWidth/2, GAME_WIDTH - camera.viewportWidth/2);
        float y = MathUtils.clamp(gameState.getPlayerPosition().y, camera.viewportHeight/2, GAME_HEIGHT - camera.viewportHeight/2);
        camera.position.set(x, y, 0); */
        camera.position.set(gameState.getPlayerPosition(), 0);
        viewport.update(SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.2f, 0.2f, 0.5f, 1);

        mapRenderer.setView(camera);
        mapRenderer.render();

        //playerSprite.setRotation(gameState.getPlayerFacing());
        playerSprite.setPosition(gameState.getPlayerPosition().x, gameState.getPlayerPosition().y);

        batch.begin();
        playerSprite.draw(batch);
        batch.draw(buildingTexture, 50, 50);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapRenderer.dispose();
        mapManager.dispose();
    }

    public void windowResized(int width, int height) {
        viewport.update(width, height, true);
    }
}
