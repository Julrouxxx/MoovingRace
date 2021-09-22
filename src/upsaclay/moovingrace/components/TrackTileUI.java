package upsaclay.moovingrace.components;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TrackTileUI {
    public TrackTileUI(TrackTile trackTile) {

    }

    public void paint(Graphics2D g, TrackTile trackTile) {

        TrackTileModel model = trackTile.getModel();
        Image sprite = getImage(model.getType());
        System.out.println(trackTile.getModel().getRotation().getRotation());
        int angle = trackTile.getModel().getRotation().getRotation();
        AffineTransform rotation = new AffineTransform();
        rotation = AffineTransform.getRotateInstance(angle, (int)(sprite.getWidth(null)/2), (int)(sprite.getHeight(null)/2));
        g.drawImage(sprite, rotation, null);
    }

    private Image getImage(TrackType type) {

        return TrackTile.sprites.get(type);
    }
}
