package upsaclay.moovingrace.components;

import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static upsaclay.moovingrace.components.TrackTile.sprites;

public class TrackTileUI {
    public TrackTileUI(TrackTile trackTile) {

    }

    public void paint(Graphics2D g, TrackTile trackTile) {

        g.translate(MoovingRaceWindow.positionTranslate.getX(), MoovingRaceWindow.positionTranslate.getY());
        trackTile.refreshBound();
        TrackTileModel model = trackTile.getModel();
        Image sprite = getImage(model.getType());
        sprite = sprite.getScaledInstance( Math.round(model.getScale()*64)+1, Math.round(model.getScale()*64)+1, Image.SCALE_DEFAULT);
        int angle = model.getRotation().getRotation();
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle),
                sprite.getWidth(null)/2.0,
                sprite.getHeight(null)/2.0);
        g.drawImage(sprite, rotation, null);
    }

    private Image getImage(TrackType type) {

        return sprites.get(type);
    }
}
