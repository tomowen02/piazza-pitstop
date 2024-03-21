package com.heslingtonhustle.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.heslingtonhustle.state.Trigger;

import java.util.HashMap;

public class MapManager implements Disposable {
    private TiledMap currentMap;
    private final TmxMapLoader mapLoader;
    private final HashMap<String, TiledMap> loadedMaps;
    private final HashMap<TiledMap, OrthogonalTiledMapRenderer> loadedMapRenderers;
    private ShapeRenderer collisionRenderer;
    private MapObjects collisionObjects;
    private MapObjects triggerObjects;
    private final HashMap<TiledMap, Array<RectangleMapObject>> collisionRectangles;
    private final HashMap<TiledMap, Array<RectangleMapObject>> triggerRectangles;

    public MapManager() {
        mapLoader = new TmxMapLoader();
        loadedMaps = new HashMap<>();
        loadedMapRenderers = new HashMap<>();
        collisionRectangles = new HashMap<>();
        triggerRectangles = new HashMap<>();
    }

    public void loadMap(String path) {
        if (!loadedMaps.containsKey(path)) {
            loadedMaps.put(path, mapLoader.load(path));
        }
        currentMap = loadedMaps.get(path);

        try {
            MapLayer collisionLayer = currentMap.getLayers().get("Collisions");
            collisionObjects = collisionLayer.getObjects();
        } catch (NullPointerException e) {
            Gdx.app.debug("DEBUG", "NO COLLISION LAYER FOUND!");
        }

        try {
            MapLayer triggerLayer = currentMap.getLayers().get("Triggers");
            triggerObjects = triggerLayer.getObjects();
        } catch (NullPointerException e) {
            Gdx.app.debug("DEBUG", "NO TRIGGER LAYER FOUND!");
        }

    }

    public OrthogonalTiledMapRenderer getCurrentMapRenderer(SpriteBatch spriteBatch) {
        if (currentMap == null) return null;

        if (!loadedMapRenderers.containsKey(currentMap)) {
            loadedMapRenderers.put(currentMap, new OrthogonalTiledMapRenderer(currentMap, spriteBatch));
        }

        return loadedMapRenderers.get(currentMap);
    }

    public ShapeRenderer getCollisionRenderer() {
        // This method gets the renderer that is used to show the collision rectangles and trigger rectangles
        // for debugging purposes
        if (collisionRenderer == null) {
            collisionRenderer = new ShapeRenderer();
        }
        collisionRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (collisionObjects != null) {
            collisionRenderer.setColor(255, 0, 0, 50);
            for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
                Rectangle collisionRectangle = rectangleObject.getRectangle();
                collisionRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
            }
        }
        if (triggerObjects != null) {
            collisionRenderer.setColor(0, 0, 255, 50);
            for (RectangleMapObject rectangleObject : triggerObjects.getByType(RectangleMapObject.class)) {
                Rectangle triggerRectangle = rectangleObject.getRectangle();
                collisionRenderer.rect(triggerRectangle.x, triggerRectangle.y, triggerRectangle.width, triggerRectangle.height);
            }
        }
        collisionRenderer.end();
        return collisionRenderer;
    }

    public boolean isCollision(Rectangle playerRectangle) {
        if (collisionObjects == null) {
            return false;
        }
        playerRectangle = worldRectangleToPixelRectangle(playerRectangle);
        Array<RectangleMapObject> mapCollisionRectangles = getRectangles(collisionRectangles);
        RectangleMapObject overlappingRectangle = getOverlappingMapRectangle(playerRectangle, mapCollisionRectangles);
        return overlappingRectangle != null;
    }

    public Trigger getTrigger(Rectangle playerRectangle) {
        if (triggerObjects == null) {
            return null;
        }
        playerRectangle = worldRectangleToPixelRectangle(playerRectangle);
        Array<RectangleMapObject> mapTriggerRectangles = getRectangles(triggerRectangles);
        RectangleMapObject overlappingRectangle = getOverlappingMapRectangle(playerRectangle, mapTriggerRectangles);
        if (overlappingRectangle == null) {
            return null;
        }
        MapProperties mapProperties = overlappingRectangle.getProperties();
        return new Trigger(mapProperties);
    }


    /**
     * @param cache Collection containing cached object. May be modified to add a new array.
     * @return The array of rectangles added to/retrieved from the cache.
     */
    private Array<RectangleMapObject> getRectangles(HashMap<TiledMap, Array<RectangleMapObject>> cache) {
        if (currentMap == null) return new Array<>();

        if (!cache.containsKey(currentMap)) {
            cache.put(currentMap, collisionObjects.getByType(RectangleMapObject.class));
        }

        return cache.get(currentMap);
    }

    public Vector2 getCurrentMapTileDimensions() {
        //TODO: Add null check
        return new Vector2(
                (int)currentMap.getProperties().get("tilewidth"),
                (int)currentMap.getProperties().get("tileheight")
        );
    }

    public Vector2 getCurrentMapWorldDimensions() {
        //TODO: Add null check
        return new Vector2(
                (int)currentMap.getProperties().get("width"),
                (int)currentMap.getProperties().get("height")
        );
    }

    public Vector2 getCurrentMapPixelDimensions() {
        //TODO: Add null check
        return new Vector2(
                getCurrentMapWorldDimensions().x * getCurrentMapTileDimensions().x,
                getCurrentMapWorldDimensions().y * getCurrentMapTileDimensions().y
        );
    }

    public Vector2 worldToPixelCoords(Vector2 worldCoords) {
        return new Vector2(
                worldCoords.x * getCurrentMapTileDimensions().x,
                worldCoords.y * getCurrentMapTileDimensions().y
        );
    }

    public float worldToPixelValue(float worldValue) {
        return worldValue * getCurrentMapTileDimensions().x;
    }

    private Rectangle worldRectangleToPixelRectangle(Rectangle rectangle) {
        float x = rectangle.x * getCurrentMapTileDimensions().x;
        float y = rectangle.y * getCurrentMapTileDimensions().y;
        float width = rectangle.width * getCurrentMapTileDimensions().x;
        float height = rectangle.height * getCurrentMapTileDimensions().y;
        return new Rectangle(x, y, width, height);
    }

    private RectangleMapObject getOverlappingMapRectangle(Rectangle playerRectangle, Array<RectangleMapObject> mapRectangles) {
        // For each rectangle in the collisions layer, check whether the player rectangle intercepts
        for (RectangleMapObject rectangleObject : mapRectangles) {
            Rectangle collisionRectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(collisionRectangle, playerRectangle)) {
                //Gdx.app.debug("DEBUG", "Collision!");
                return rectangleObject;
            }
        }
        return null;
    }

    @Override
    public void dispose() {
        for (TiledMap map : loadedMaps.values()) {
            map.dispose();
        }
        for (OrthogonalTiledMapRenderer mapRenderer: loadedMapRenderers.values()) {
            mapRenderer.dispose();
        }
        if (collisionRenderer != null) {
            collisionRenderer.dispose();
        }
    }
}
