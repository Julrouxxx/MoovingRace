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

    /**
     * Define a Track in the Map
     * @param type Type Of The Track
     * @param rotation Rotation of The Track
     * @param scale Scale of The Track
     * @param position Position of The Track
     */
    public TrackTile(TrackType type, TrackRotation rotation, int scale, Point position) {
        InitSprites();

        this.model = new TrackTileModel(type, rotation, position);

        this.ui = new TrackTileUI();
    }

    /**
     * Define a Track in the Map (used for map editor)
     * @param id Id of the track (used for map editor)
     * @param type Type Of The Track
     * @param rotation Rotation of The Track
     * @param scale Scale of The Track
     * @param position Position of The Track
     */
    public TrackTile(int id, TrackType type, TrackRotation rotation, int scale, Point position) {
        InitSprites();

        this.model = new TrackTileModel(id, type, rotation, position);

        this.ui = new TrackTileUI();
    }

    public TrackTileModel getModel() {
        return model;
    }

    public TrackTileUI getUi() {
        return ui;
    }
    public void setScale(){
        refreshBound();
    }

    public void refreshBound() {
        //context.getSize().width/2 - Math.round(MoovingRaceWindow.scale*25)/2,
          //      context.getSize().height/2 - Math.round(MoovingRaceWindow.scale*25)/2,
        //Math.round(((float) getModel().getPosition().getX() + MoovingRaceWindow.positionTranslate.x)*MoovingRaceWindow.scale),
                //Math.round (((float) getModel().getPosition().getY() + MoovingRaceWindow.positionTranslate.y)*MoovingRaceWindow.scale)
        setBounds(Math.round(((float) getModel().getPosition().getX() + MoovingRaceWindow.positionTranslate.x)*MoovingRaceWindow.scale),
                Math.round (((float) getModel().getPosition().getY() + MoovingRaceWindow.positionTranslate.y)*MoovingRaceWindow.scale),
                Math.round(MoovingRaceWindow.scale*64)+1,
                Math.round(MoovingRaceWindow.scale*64)+1);
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


    /**
     * Init Sprites for every type of Track
     */
    public void InitSprites()
    {
        if(sprites != null && !sprites.isEmpty()) return;

        sprites = new HashMap<>();

        for (TrackType type : TrackType.values()) {
            BufferedImage spriteSheets = null;
            try {
                spriteSheets = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/track_sprites.png")));
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
                case TRACK_BUTTON:
                    sprites.put(TrackType.TRACK_BUTTON, spriteSheets.getSubimage(64, 64, 64, 64));
                    break;
            }
        }
    }
}
