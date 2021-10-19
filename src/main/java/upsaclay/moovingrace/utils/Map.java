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

    /**
     * Get if this map is in loop (don't have end tracks)
     * @return true if map is a loop
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Get the number of Lap from this map if the map is a loop
     * @return Optional of the number of lap (can be empty if not a loop)
     */
    public Optional<Integer> getMaxLap() {
        return isLoop() ? Optional.of(maxLap) : Optional.empty();
    }

    /**
     * Get the name of the map
     * @return name of the map
     */
    public String getName() {
        return name;
    }

    /**
     * @deprecated
     * Get scale of map if map is too small or too big for the camera
     * @return the scale of the map
     */
    public int getScale() {
        return scale;
    }

    /**
     * Get tracks that composed this map
     * @return Array of Tracks
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }


    @Override
    public String toString() {
        return "Map{" +
                "tracks=" + tracks +
                '}';
    }

    /**
     * Set the number of laps from this map
     * @param maxLaps number of laps
     */

    public void setMaxLaps(int maxLaps) {
        this.maxLap = maxLaps;
    }

    /**
     * Set if the map is a loop
     * @param loop loop or not
     */
    public void setIsLoop(boolean loop) {
        this.loop = loop;
    }
}
