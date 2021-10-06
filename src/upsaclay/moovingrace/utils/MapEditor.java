package upsaclay.moovingrace.utils;

import upsaclay.moovingrace.components.tracktile.TrackTile;
import upsaclay.moovingrace.components.tracktile.TrackTileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.spi.AbstractSelectionKey;

public class MapEditor {
    public Map map;
    public JPanel panel;

    public MapEditor(String mapName, JPanel panel) {
        this.panel = panel;
        this.map = new Map(mapName, 64);
        Track track = new Track(TrackType.TRACK_START, TrackRotation.EAST, 10, 10);
        map.getTracks().add(track);
        TrackTile trackTile_start = loadImage(track, map.getScale());

        Track track_button1 = new Track(TrackType.TRACK_BUTTON, TrackRotation.EAST, 11, 10);
        map.getTracks().add(track);
        loadButton(track_button1, map.getScale(), trackTile_start);
        Track track_button2 = new Track(TrackType.TRACK_BUTTON, TrackRotation.WEST, 9, 10);
        map.getTracks().add(track);
        loadButton(track_button2, map.getScale(), trackTile_start);
        Track track_button3 = new Track(TrackType.TRACK_BUTTON, TrackRotation.SOUTH, 10, 11);
        map.getTracks().add(track);
        loadButton(track_button3, map.getScale(), trackTile_start);
        Track track_button4 = new Track(TrackType.TRACK_BUTTON, TrackRotation.NORTH, 10, 9);
        map.getTracks().add(track);
        loadButton(track_button4, map.getScale(), trackTile_start);


    }

    private TrackTile loadImage(Track track, int scale) {

        TrackTile trackTile = new TrackTile(track.getType(), track.getRotation(), scale, new Point(track.getPositionX(scale), track.getPositionY(scale)));
        trackTile.refreshBound();

        trackTile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON2) {

                }
            }
        });

        panel.add(trackTile);

        return trackTile;
    }
    private void loadButton(Track track, int scale, TrackTile trackTileParent) {

        TrackTile trackTile = new TrackTile(track.getType(), track.getRotation(), scale, new Point(track.getPositionX(scale), track.getPositionY(scale)));
        trackTile.refreshBound();

        trackTile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(e.getButton() == MouseEvent.BUTTON1) {
                    TrackTileModel model = trackTileParent.getModel();
                    if(model.getType() == TrackType.TRACK_START) {
                        if(track.getPositionX(1) != (int) model.getPosition().getX()/scale) {
                            if(track.getPositionX(1) < (int) model.getPosition().getX()/scale) {
                                model.setRotation(TrackRotation.WEST);
                            } else {
                                model.setRotation(TrackRotation.EAST);
                            }
                        } else {
                            if(track.getPositionY(1) < (int) model.getPosition().getY()/scale) {
                                model.setRotation(TrackRotation.NORTH);
                            } else {
                                model.setRotation(TrackRotation.SOUTH);
                            }
                        }
                        trackTile.getModel().setType(TrackType.TRACK_CLASSIC);
                    }
                    System.out.println((int) model.getPosition().getX()/scale);
                    System.out.println(track.getPositionX(1));
                    trackTile.repaint();
                    trackTileParent.repaint();
                }
            }
        });
        panel.add(trackTile);

    }
}
