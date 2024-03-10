package com.heslingtonhustle;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.Map;

public class MapManager {
    private TiledMap currentMap;
    private TmxMapLoader mapLoader;

    public MapManager() {
        mapLoader = new TmxMapLoader();
    }

    public void loadMap(String path) {
        if (currentMap != null) {
            currentMap.dispose();
        }
        currentMap = mapLoader.load(path);
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }
}
