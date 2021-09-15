package upsaclay.moovingrace.utils;

public enum TrackRotation {
    NORTH(0), WEST(90), SOUTH(180), EST(270);
    int rotation;
    TrackRotation(int rotation){
        this.rotation = rotation;
    }

}
