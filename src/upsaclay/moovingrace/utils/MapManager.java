package upsaclay.moovingrace.utils;

import java.util.ArrayList;

public class MapManager {
    public static MapManager instance = new MapManager();
    ArrayList<Map> maps = new ArrayList<>();
    public static MapManager getInstance() {
        return instance;
    }
    public MapManager(){
        //init
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Map getMapByName(String name){
        return maps.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
