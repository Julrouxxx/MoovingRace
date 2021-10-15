package upsaclay.moovingrace.utils;

public class Track {
    private TrackType type;
    private TrackRotation rotation;
    private int x, y;
    public int id;

    public Track(int id, TrackType type, TrackRotation rotation, int x, int y) {
        this.id = id;
        this.type = type;
        this.rotation = rotation;
        this.x = x;
        this.y = y;
    }
    public Track(TrackType type, TrackRotation rotation, int x, int y) {
        this.type = type;
        this.rotation = rotation;
        this.x = x;
        this.y = y;
    }

    public TrackType getType() {
        return type;
    }

    public TrackRotation getRotation() {
        return rotation;
    }
    public int getPositionX(int scale){
        return x*scale;
    }

    public int getPositionY(int scale){
        return y*scale;
    }

    public int getId() {
        return id;
    }
}
