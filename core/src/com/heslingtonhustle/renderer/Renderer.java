package com.heslingtonhustle.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heslingtonhustle.map.MapManager;
import com.heslingtonhustle.state.State;
import com.heslingtonhustle.screens.PauseMenu;

import static java.lang.Math.round;

/**
 * Queries the State to draw the current state to the screen; does not make any changes to State.
 */
public class Renderer implements Disposable {
    private final boolean DEBUG_COLLISIONS = false;

    private int screenWidth;
    public int screenHeight;

    private final ExtendViewport viewport;
    private final OrthographicCamera camera;
    private MapRenderer mapRenderer;
    private final State gameState;
    private final CharacterRenderer playerRenderer;
    private final SpriteBatch batch;

    private final TextureAtlas textureAtlas;

    private final MapManager mapManager;

    private final PauseMenu pauseMenu;

    private final HudRenderer hudRenderer;

    public Renderer(State state, MapManager mapManager, PauseMenu pauseMenu)
    {
        screenWidth = 300;
        screenHeight = 200;

        gameState = state;
        camera = new OrthographicCamera();
        this.mapManager = mapManager;
        mapManager.loadMap("Maps/campusEast.tmx");

        batch = new SpriteBatch();
        mapRenderer = mapManager.getCurrentMapRenderer(batch);
        viewport = new ExtendViewport(screenWidth, screenHeight, camera);

        this.pauseMenu = pauseMenu;

        textureAtlas = new TextureAtlas("mainAtlas.atlas");

        hudRenderer = new HudRenderer(gameState, textureAtlas);

        float playerWidthInPixels = mapManager.worldToPixelValue(state.getPlayerWidth());
        float playerHeightInPixels = mapManager.worldToPixelValue(state.getPlayerHeight());
        playerRenderer = new CharacterRenderer(playerWidthInPixels, playerHeightInPixels, textureAtlas, "character00");
    }

    public void update() {
        Vector2 playerPixelPosition = mapManager.worldToPixelCoords(gameState.getPlayerPosition());
        Vector2 clampedPlayerPosition = clampCoordsToScreen(playerPixelPosition);
        camera.position.set(clampedPlayerPosition, 0);

        viewport.update(screenWidth, screenHeight);
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.2f, 0.2f, 0.5f, 1);

        mapRenderer = mapManager.getCurrentMapRenderer(batch); // Maybe change how this works
        mapRenderer.setView(camera);
        mapRenderer.render();

        if (DEBUG_COLLISIONS) {
            ShapeRenderer collisionRenderer = mapManager.getCollisionRenderer();
            collisionRenderer.setProjectionMatrix(camera.combined);
        }

        batch.begin();
        playerRenderer.render(batch, playerPixelPosition.x, playerPixelPosition.y, gameState.getPlayerFacing(), gameState.getPlayerMovement());
        batch.end();

        hudRenderer.render();

        pauseMenu.render();
    }

    public void ShowPauseScreen() {
        pauseMenu.ShowPauseMenu();
    }

    public void HidePauseScreen() {
        pauseMenu.HidePauseMenu();
    }

    private Vector2 clampCoordsToScreen(Vector2 coords) {
        float x = MathUtils.clamp(
                coords.x,
                camera.viewportWidth / 2,
                mapManager.getCurrentMapPixelDimensions().x - camera.viewportWidth / 2
        );
        float y = MathUtils.clamp(
                coords.y,
                camera.viewportHeight / 2,
                mapManager.getCurrentMapPixelDimensions().y - camera.viewportHeight / 2
        );
        x = round(x*10) / 10; // Round to two decimal places to avoid tearing
        y = round(y*10) / 10;
        return new Vector2(x, y);
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapManager.dispose();
        hudRenderer.dispose();
    }

    public void windowResized(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        viewport.update(width, height, true);
        hudRenderer.resize(width, height);
        pauseMenu.resize(width, height);
    }
}
