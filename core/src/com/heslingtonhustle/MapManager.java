package com.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class MapManager {
    private TiledMap currentMap;
    private TmxMapLoader mapLoader;
    private MapObjects collisionObjects;

    public MapManager() {
        mapLoader = new TmxMapLoader();
    }

    public void loadMap(String path) {
        if (currentMap != null) {
            currentMap.dispose();
        }
        currentMap = mapLoader.load(path);

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
}
