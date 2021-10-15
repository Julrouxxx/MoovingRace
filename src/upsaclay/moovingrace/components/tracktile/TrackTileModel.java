package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;

public class TrackTileModel {

    private int id;
    private TrackType type;
    private TrackRotation rotation;
    private Point position;

    public TrackTileModel(TrackType type, TrackRotation rotation, Point position) {
        this.type = type;
        this.rotation = rotation;
        this.position = position;
    }

    public TrackTileModel(int id, TrackType type, TrackRotation rotation, Point position) {
        this.id = id;
        this.type = type;
        this.rotation = rotation;
        this.position = position;
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

    public void setType(TrackType type) {
        this.type = type;
    }

    public void setRotation(TrackRotation rotation) {
        this.rotation = rotation;
    }

    public int getId() {
        return id;
    }

    public void incId() {
        this.id++;
    }
}
