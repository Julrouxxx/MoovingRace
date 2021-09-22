package upsaclay.moovingrace.components;

import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class TrackTileUI {
    public TrackTileUI(TrackTile trackTile) {

    }

    public void paint(Graphics2D g, TrackTile trackTile) {

        TrackTileModel model = trackTile.getModel();
        Image sprite = getImage(model.getType());
        System.out.println(trackTile.getModel().getRotation().getRotation());
        int angle = trackTile.getModel().getRotation().getRotation();
        AffineTransform rotation = AffineTransform.getRotateInstance(angle, sprite.getWidth(null)/2.0, sprite.getHeight(null)/2.0);
        AffineTransformOp op = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter((BufferedImage) sprite, null), 0, 0, null);
    }

    private Image getImage(TrackType type) {

        return TrackTile.sprites.get(type);
    }
}
