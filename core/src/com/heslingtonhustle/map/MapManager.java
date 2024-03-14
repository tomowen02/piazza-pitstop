package com.heslingtonhustle.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

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

        MapLayer collisionLayer = currentMap.getLayers().get("Collisions");
        collisionObjects = collisionLayer.getObjects();
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

    @Override
    public void dispose() {
        for (TiledMap map : loadedMaps.values()) {
            map.dispose();
        }
    }
}
