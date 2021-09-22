package upsaclay.moovingrace.utils;

public enum TrackRotation {
    NORTH(270), WEST(0), SOUTH(90), EAST(180);
    private int rotation;
    public int getRotation() {
        return rotation;
    }
    TrackRotation(int rotation){
        this.rotation = rotation;
    }

}
