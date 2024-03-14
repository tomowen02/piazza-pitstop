package com.heslingtonhustle.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.graalvm.compiler.lir.amd64.vector.AMD64VectorMove;

import java.util.HashMap;
import java.util.Vector;

public class MapManager implements Disposable {
    private TiledMap currentMap;
    private TmxMapLoader mapLoader;
    private HashMap<String, TiledMap> loadedMaps;
    private MapObjects collisionObjects;

    public MapManager() {
        mapLoader = new TmxMapLoader();
        loadedMaps = new HashMap<>();
    }

    public void loadMap(String path) {
        if (!loadedMaps.containsKey(path)) {
            loadedMaps.put(path, mapLoader.load(path));
        }
        currentMap = loadedMaps.get(path);

        /* This code can cause a nullptr exception if no collision layer is present */
        //MapLayer collisionLayer = currentMap.getLayers().get("Collisions");
        //collisionObjects = collisionLayer.getObjects();
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public boolean checkCollision(Rectangle playerRectangle) {
        // For each rectangle in the collisions layer, check whether the player rectangle intercepts
        for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
            Rectangle collisionRectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(collisionRectangle, playerRectangle)) {
                Gdx.app.debug("DEBUG", "Collision!");
                return true;
            }
        }
        return false;
    }

    public Vector2 getCurrentMapTileDimensions() {
        return new Vector2(
                (int)currentMap.getProperties().get("tilewidth"),
                (int)currentMap.getProperties().get("tileheight")
        );
    }

    public Vector2 getCurrentMapWorldDimensions() {
        return new Vector2(
                (int)currentMap.getProperties().get("width"),
                (int)currentMap.getProperties().get("height")
        );
    }

    public Vector2 getCurrentMapPixelDimensions() {
        return new Vector2(
                getCurrentMapWorldDimensions().x * getCurrentMapTileDimensions().x,
                getCurrentMapWorldDimensions().y * getCurrentMapTileDimensions().y
        );
    }

    @Override
    public void dispose() {
        for (TiledMap map : loadedMaps.values()) {
            map.dispose();
        }
    }
}
