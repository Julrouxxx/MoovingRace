package upsaclay.moovingrace.utils;

import java.util.ArrayList;
import java.util.Optional;

public class Map {
    private ArrayList<Track> tracks;
    private String name;
    private int scale;
    private Integer maxLap;
    private boolean loop;

    public Map(String name, int scale, int maxLap){
        this.name = name;
        this.scale = scale;
        this.loop = true;
        this.maxLap = maxLap;
        this.tracks = new ArrayList<>();
    }
    public Map(String name, int scale){
        this.name = name;
        this.scale = scale;
        this.loop = false;
        this.tracks = new ArrayList<>();
    }
    public boolean isLoop() {
        return loop;
    }

    public Optional<Integer> getMaxLap() {
        return isLoop() ? Optional.of(maxLap) : Optional.empty();
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


    @Override
    public String toString() {
        return "Map{" +
                "tracks=" + tracks +
                '}';
    }

    public void setMaxLaps(int maxLaps) {
        this.maxLap = maxLaps;
    }

    public void setIsLoop(boolean loop) {
        this.loop = loop;
    }
}
