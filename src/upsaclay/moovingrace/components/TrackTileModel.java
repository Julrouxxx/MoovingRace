package upsaclay.moovingrace.components;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;

public class TrackTileModel {

    private TrackType type;
    private TrackRotation rotation;
    private Point position;

    public TrackTileModel(TrackType type, TrackRotation rotation) {
        this.type = type;
        this.rotation = rotation;
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
