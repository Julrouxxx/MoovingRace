package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

public class TrackTileModel {
    BufferedImage image;

    private TrackType type;
    private TrackRotation rotation;
    private Point position;
    private GeneralPath collisionShape;
    public TrackTileModel(TrackType type, TrackRotation rotation, Point position) {
        this.type = type;
        this.rotation = rotation;
        this.position = position;
        AffineTransform r = AffineTransform.getRotateInstance(Math.toRadians(this.rotation.getRotation()),
                TrackTile.sprites.get(type).getWidth(null)/2.0,
                TrackTile.sprites.get(type).getHeight(null)/2.0);
        this.image = new BufferedImage(TrackTile.sprites.get(type).getWidth(null), TrackTile.sprites.get(type).getHeight(null), BufferedImage.TYPE_INT_ARGB);
        ((Graphics2D) this.image.getGraphics()).drawImage(TrackTile.sprites.get(type), r, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }

    public TrackRotation getRotation() {
        return rotation;
    }

    public Point getPosition() {
        return position;
    }
}
