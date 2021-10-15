package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class TrackTileModel {
    BufferedImage image;

    private int id;
    private TrackType type;
    private TrackRotation rotation;
    private boolean isPassed;
    private Point position;
    private GeneralPath collisionShape;
    public TrackTileModel(TrackType type, TrackRotation rotation, Point position) {
        this.type = type;
        this.rotation = rotation;
        this.position = position;
        this.isPassed = false;
        AffineTransform r = AffineTransform.getRotateInstance(Math.toRadians(this.rotation.getRotation()),
                TrackTile.sprites.get(type).getWidth(null)/2.0,
                TrackTile.sprites.get(type).getHeight(null)/2.0);
        this.image = new BufferedImage(TrackTile.sprites.get(type).getWidth(null), TrackTile.sprites.get(type).getHeight(null), BufferedImage.TYPE_INT_ARGB);
        ((Graphics2D) this.image.getGraphics()).drawImage(TrackTile.sprites.get(type), r, null);
    }

    public BufferedImage getImage() {
        return image;
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

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public boolean isPassed() {
        return isPassed;
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
