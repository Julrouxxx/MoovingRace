package upsaclay.moovingrace.utils;

public enum TrackRotation {
    NORTH(0), WEST(90), SOUTH(180), EAST(270);
    private int rotation;
    public int getRotation() {
        return rotation;
    }
    TrackRotation(int rotation){
        this.rotation = rotation;
    }

}
