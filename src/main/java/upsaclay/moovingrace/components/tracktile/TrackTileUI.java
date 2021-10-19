package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.utils.TrackType;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static upsaclay.moovingrace.components.tracktile.TrackTile.sprites;

public class TrackTileUI {

    public void paint(Graphics2D g, TrackTile trackTile) {

        g.translate(MoovingRaceWindow.positionTranslate.getX() - MoovingRaceWindow.positionTranslate.x,
                MoovingRaceWindow.positionTranslate.getY() - MoovingRaceWindow.positionTranslate.y);
        trackTile.refreshBound();
        TrackTileModel model = trackTile.getModel();
        Image sprite = getImage(model.getType());
        sprite = sprite.getScaledInstance( Math.round(MoovingRaceWindow.scale*64)+1, Math.round(MoovingRaceWindow.scale*64)+1, Image.SCALE_DEFAULT);
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
