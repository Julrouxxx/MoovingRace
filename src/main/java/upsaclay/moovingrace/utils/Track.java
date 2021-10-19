package upsaclay.moovingrace.utils;

public class Track {
    private TrackType type;
    private TrackRotation rotation;
    private int x, y;
    public int id;

    /**
     * Initialize a new Track for MapEditor
     * @param id ID of the track (used MapEditor)
     * @param type Type of Track to created
     * @param rotation Rotation of the track
     * @param x horizontal position of the track
     * @param y vertical position of the track
     */
    public Track(int id, TrackType type, TrackRotation rotation, int x, int y) {
        this.id = id;
        this.type = type;
        this.rotation = rotation;
        this.x = x;
        this.y = y;
    }
    /**
     * Initialize a new Track
     * @param type Type of Track to created
     * @param rotation Rotation of the track
     * @param x horizontal position of the track
     * @param y vertical position of the track
     */
    public Track(TrackType type, TrackRotation rotation, int x, int y) {
        this.type = type;
        this.rotation = rotation;
        this.x = x;
        this.y = y;
    }

    /**
     * Get Type of Track
     * @see TrackType
     * @return TrackType of Track
     */
    public TrackType getType() {
        return type;
    }
    /**
     * Get Rotation of Track
     * @see TrackRotation
     * @return Rotation of Track
     */
    public TrackRotation getRotation() {
        return rotation;
    }

    /**
     * Get horizontal position according to scale
     * @param scale scale of the position point
     * @return horizontal position with scale
     */
    public int getPositionX(int scale){
        return x*scale;
    }
    /**
     * Get vertical position according to scale
     * @param scale scale of the position point
     * @return vertical position with scale
     */
    public int getPositionY(int scale){
        return y*scale;
    }

    /**
     * Get ID of the Track (used in MapEditor)
     * @return ID of the Track
     */
    public int getId() {
        return id;
    }
}
