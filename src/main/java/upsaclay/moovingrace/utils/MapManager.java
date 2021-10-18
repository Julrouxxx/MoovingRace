package upsaclay.moovingrace.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import upsaclay.moovingrace.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MapManager {
    public static MapManager instance = new MapManager();
    ArrayList<Map> maps = new ArrayList<>();
    public static MapManager getInstance() {
        return instance;
    }
    public MapManager(){
        //init
        loadFromFile();
    }
    private void loadExamplesMaps(){
        Map simpleMax = new Map("simple", 64, 3);
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 2, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 2, 1));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 1, 1));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0, 1));

        maps.add(simpleMax);
        Map complexMap = new Map("complex", 64, 3);
        complexMap.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 2,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 3,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 3,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 4,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 4,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 4,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 3,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 2,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 1,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 1,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 1,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0,0));
        maps.add(complexMap);
        Map map = new Map("classic", 64, 3);
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0, 0));
        map.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1, 0));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 2, 0));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 2, 1));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 2, 2));
        map.getTracks().add(new Track(TrackType.TRACK_END, TrackRotation.EAST, 1, 2));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0, 2));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 0, 1));
        maps.add(map);
    }
    public void loadFromFile() {
        maps.clear();
        loadExamplesMaps();
        File file = new File(Main.getWorkingDirectory());
        File[] jsons = file.listFiles(pathname -> {
            int i = pathname.getAbsolutePath().lastIndexOf(".");
            return pathname.getAbsolutePath().substring(i+1).equalsIgnoreCase("json");
        });
        if(jsons == null)
            return;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Map.class, new MapSerializer());
        Gson gson = gsonBuilder.create();
        for (File json : jsons) {
            try {
               Map map =  gson.fromJson(new FileReader(json), Map.class);
               maps.add(map);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getMapList(){
        String[] s = new String[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            s[i] = maps.get(i).getName();
        }
        return  s;
    }
    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Map getMapByName(String name){
        return maps.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
