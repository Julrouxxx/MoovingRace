package upsaclay.moovingrace.utils;

import java.util.ArrayList;

public class Map {
    private ArrayList<Track> tracks;
    private String name;

    public Map(String name){
        this.name = name;
        this.tracks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
