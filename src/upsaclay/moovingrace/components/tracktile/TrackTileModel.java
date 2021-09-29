package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;

public class TrackTileModel {

    private TrackType type;
    private TrackRotation rotation;
    private Point position;
    private float scale;

    public TrackTileModel(TrackType type, TrackRotation rotation, float scale, Point position) {
        this.type = type;
        this.rotation = rotation;
        this.scale = scale;
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public TrackType getType() {
        return type;
    }

    public TrackRotation getRotation() {
        return rotation;
    }

    public Point getPosition() {
        return position;
    }
}
