package upsaclay.moovingrace.components.tracktile;

import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TrackTile extends JComponent {
    public static Map<TrackType, Image> sprites;
    private TrackTileModel model;
    private TrackTileUI ui;

    public TrackTile(TrackType type, TrackRotation rotation, int scale, Point position) {
        InitSprites();

        this.model = new TrackTileModel(type, rotation, scale, position);

        this.ui = new TrackTileUI(this);
    }

    public TrackTileModel getModel() {
        return model;
    }

    public TrackTileUI getUi() {
        return ui;
    }
    public void setScale(float scale){
        model.setScale(scale);
        refreshBound();
    }

    public void refreshBound() {

        setBounds(Math.round(((float) getModel().getPosition().getX())*model.getScale()),
                Math.round (((float) getModel().getPosition().getY())*model.getScale()),
                Math.round(model.getScale()*64)+1 + MoovingRaceWindow.positionTranslate.x,
                Math.round(model.getScale()*64)+1 + MoovingRaceWindow.positionTranslate.y);
    }


    @Override
    public void paintComponent(Graphics g) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.ui.paint((Graphics2D)g, this);
    }

    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() {
        return new Dimension(64, 64);
    }
    public Dimension getMaximumSize() { return getPreferredSize(); }

    public void InitSprites()
    {
        if(sprites != null && !sprites.isEmpty()) return;

        sprites = new HashMap<>();

        for (TrackType type : TrackType.values()) {
            BufferedImage spriteSheets = null;
            try {
                spriteSheets = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sprites/track_sprites.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(spriteSheets == null) continue;

            switch(type)
            {
                case TRACK_CLASSIC:
                    sprites.put(TrackType.TRACK_CLASSIC, spriteSheets.getSubimage(64, 0, 64, 64));
                    break;
                case TRACK_SHIFT:
                    sprites.put(TrackType.TRACK_SHIFT, spriteSheets.getSubimage(128, 0, 64, 64));
                    break;
                case TRACK_START:
                    sprites.put(TrackType.TRACK_START, spriteSheets.getSubimage(0, 64, 64, 64));
                    break;
                case TRACK_END:
                    sprites.put(TrackType.TRACK_END, spriteSheets.getSubimage(0, 0, 64, 64));
                    break;
            }
        }
    }
}
