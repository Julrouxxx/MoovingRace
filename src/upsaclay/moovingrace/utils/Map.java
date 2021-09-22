package upsaclay.moovingrace.utils;

import java.util.ArrayList;

public class Map {
    private ArrayList<Track> tracks;
    private String name;
    private int scale;

    public Map(String name, int scale){
        this.name = name;
        this.scale = scale;
        this.tracks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScale() {
        return scale;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
