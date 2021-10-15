package upsaclay.moovingrace.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import upsaclay.moovingrace.Main;
import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.components.tracktile.TrackTile;
import upsaclay.moovingrace.components.tracktile.TrackTileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapEditor {
    public Map map;
    public JPanel panel;
    private ComponentAdapter centerTile;

    public MapEditor(String mapName, JPanel panel) {

        this.panel = panel;
        this.map = new Map(mapName, 64);
        Track track = new Track(0, TrackType.TRACK_START, TrackRotation.EAST, 10, 10);
        map.getTracks().add(track);
        TrackTile trackTile_start = loadImage(track, map.getScale());
        updateOnResize(trackTile_start);

        Track track_button1 = new Track(track.getId(), TrackType.TRACK_BUTTON, TrackRotation.EAST, 11, 10);
        map.getTracks().add(track_button1);
        loadButton(track_button1, map.getScale(), trackTile_start);
        Track track_button2 = new Track(track.getId(), TrackType.TRACK_BUTTON, TrackRotation.WEST, 9, 10);
        map.getTracks().add(track_button2);
        loadButton(track_button2, map.getScale(), trackTile_start);
        Track track_button3 = new Track(track.getId(), TrackType.TRACK_BUTTON, TrackRotation.SOUTH, 10, 11);
        map.getTracks().add(track_button3);
        loadButton(track_button3, map.getScale(), trackTile_start);
        Track track_button4 = new Track(track.getId(), TrackType.TRACK_BUTTON, TrackRotation.NORTH, 10, 9);
        map.getTracks().add(track_button4);
        loadButton(track_button4, map.getScale(), trackTile_start);

    }

    private TrackTile loadImage(Track track, int scale) {

        TrackTile trackTile = new TrackTile(track.getType(), track.getRotation(), scale, new Point(track.getPositionX(scale), track.getPositionY(scale)));
        trackTile.refreshBound();

        panel.add(trackTile);

        return trackTile;
    }

    private void loadButton(Track track, int scale, TrackTile trackTileParent) {

        TrackTile trackTile = new TrackTile(track.getId(), track.getType(), track.getRotation(), scale, new Point(track.getPositionX(scale), track.getPositionY(scale)));
        trackTile.refreshBound();

        trackTile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TrackTileModel model = trackTileParent.getModel();
                if(e.getButton() == MouseEvent.BUTTON1) {
                    switch (model.getType()) {
                        case TRACK_START:
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
                            createButton(trackTile);
                            removeChildren(trackTileParent);
                            break;
                        case TRACK_CLASSIC:
                            matchShift(model, track, scale);

                            trackTile.getModel().setType(TrackType.TRACK_CLASSIC);
                            createButton(trackTile);
                            removeChildren(trackTileParent);
                            matchToStart(trackTile);
                            break;
                    }

                    //System.out.println((int) model.getPosition().getX()/scale);
                    //System.out.println(track.getPositionX(1));
                    trackTile.repaint();
                    trackTileParent.repaint();
                    trackTile.removeMouseListener(this);
                } else if(e.getButton() == MouseEvent.BUTTON3) {

                    if(trackTileParent.getModel().getType() == TrackType.TRACK_START) return;

                    matchShift(model, track, scale);
                    trackTile.getModel().setType(TrackType.TRACK_END);
                    removeChildren(trackTileParent);
                    endTrack();
                }
            }
        });
        panel.add(trackTile);

    }

    private void matchShift(TrackTileModel model, Track track, int scale) {
        if(track.getPositionX(1) != (int) model.getPosition().getX()/ scale) {
            if(track.getPositionX(1) < (int) model.getPosition().getX()/ scale) {
                switch (model.getRotation()) {
                    case NORTH:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.WEST);
                        break;
                    case WEST:
                        model.setType(TrackType.TRACK_CLASSIC);
                        model.setRotation(TrackRotation.WEST);
                        break;
                    case EAST:
                        System.out.println("ERROR");
                        break;
                    case SOUTH:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.SOUTH);
                        break;
                }
            } else {
                switch (model.getRotation()) {
                    case NORTH:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.NORTH);
                        break;
                    case WEST:
                        System.out.println("ERROR");
                        break;
                    case EAST:
                        model.setType(TrackType.TRACK_CLASSIC);
                        model.setRotation(TrackRotation.EAST);
                        break;
                    case SOUTH:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.EAST);
                        break;
                }
            }
        } else {
            if(track.getPositionY(1) < (int) model.getPosition().getY()/ scale) {

                switch (model.getRotation()) {
                    case NORTH:
                        model.setType(TrackType.TRACK_CLASSIC);
                        model.setRotation(TrackRotation.NORTH);
                        break;
                    case WEST:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.EAST);
                        break;
                    case EAST:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.SOUTH);
                        break;
                    case SOUTH:
                        System.out.println("ERROR");
                        break;
                }
            } else {
                switch (model.getRotation()) {
                    case NORTH:
                        System.out.println("ERROR");
                        break;
                    case WEST:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.NORTH);
                        break;
                    case EAST:
                        model.setType(TrackType.TRACK_SHIFT);
                        model.setRotation(TrackRotation.WEST);
                        break;
                    case SOUTH:
                        model.setType(TrackType.TRACK_CLASSIC);
                        model.setRotation(TrackRotation.SOUTH);
                        break;
                }
            }
        }
    }

    private void removeChildren(TrackTile trackTileParent) {

        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                if (tile.getModel().getType() == TrackType.TRACK_BUTTON &&
                        tile.getModel().getId() == trackTileParent.getModel().getId()) {

                    panel.remove(component);
                    //System.out.println("find");
                }
                //System.out.println("test");
            }
        }
        panel.repaint();
    }
    private boolean isTileAvailable(int x, int y) {

        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                if(tile.getModel().getPosition().x/64 == x &&
                        tile.getModel().getPosition().y/64 == y) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean hasEndTileAvailable() {

        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                if(tile.getModel().getType() == TrackType.TRACK_END) {
                    return true;
                }
            }
        }
        return false;
    }

    private void matchToStart(TrackTile trackTileButton) {

        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                if(tile.getModel().getType() == TrackType.TRACK_START) {
                    if(tile.getModel().getRotation() == TrackRotation.EAST){
                        if(tile.getModel().getPosition().x/64 - 1 == trackTileButton.getModel().getPosition().x/64 &&
                                tile.getModel().getPosition().y/64 == trackTileButton.getModel().getPosition().y/64) {
                            //WEST
                            switch (trackTileButton.getModel().getRotation()) {
                                case NORTH:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.NORTH);
                                    break;
                                case SOUTH:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.EAST);
                                    break;
                            }
                            removeChildren(trackTileButton);
                            endTrack();
                        }
                    }
                    if(tile.getModel().getRotation() == TrackRotation.WEST){
                        if(tile.getModel().getPosition().x/64 + 1 == trackTileButton.getModel().getPosition().x/64 &&
                                tile.getModel().getPosition().y/64 == trackTileButton.getModel().getPosition().y/64) {
                            //EAST
                            switch (trackTileButton.getModel().getRotation()) {
                                case NORTH:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.WEST);
                                    break;
                                case SOUTH:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.SOUTH);
                                    break;
                            }
                            removeChildren(trackTileButton);
                            endTrack();
                        }
                    }

                    if(tile.getModel().getRotation() == TrackRotation.SOUTH){
                        if(tile.getModel().getPosition().x/64 == trackTileButton.getModel().getPosition().x/64 &&
                                tile.getModel().getPosition().y/64 - 1 == trackTileButton.getModel().getPosition().y/64) {

                            //NORTH
                            switch (trackTileButton.getModel().getRotation()) {
                                case EAST:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.WEST);
                                    break;
                                case WEST:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.NORTH);
                                    break;
                            }
                            removeChildren(trackTileButton);
                            endTrack();
                        }
                    }
                    if(tile.getModel().getRotation() == TrackRotation.NORTH){
                        if(tile.getModel().getPosition().x/64 == trackTileButton.getModel().getPosition().x/64 &&
                                tile.getModel().getPosition().y/64 + 1 == trackTileButton.getModel().getPosition().y/64) {

                            //NORTH
                            switch (trackTileButton.getModel().getRotation()) {
                                case EAST:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.SOUTH);
                                    break;
                                case WEST:
                                    trackTileButton.getModel().setType(TrackType.TRACK_SHIFT);
                                    trackTileButton.getModel().setRotation(TrackRotation.EAST);
                                    break;
                            }
                            removeChildren(trackTileButton);
                            endTrack();
                        }
                    }
                }
            }
        }
    }

    private void createButton(TrackTile trackTileParent) {

        MoovingRaceWindow.positionTranslate.setLocation(-trackTileParent.getModel().getPosition().x + panel.getWidth()/2/2 - 64/2, -trackTileParent.getModel().getPosition().y + panel.getHeight()/2/2 - 64/2);
        updateOnResize(trackTileParent);

        boolean isStuck = true;

        trackTileParent.getModel().incId();
        if(trackTileParent.getModel().getRotation() != TrackRotation.SOUTH &&
                isTileAvailable(trackTileParent.getModel().getPosition().x/64, trackTileParent.getModel().getPosition().y/64 - 1)){

            Track track_button = new Track(trackTileParent.getModel().getId(),
                    TrackType.TRACK_BUTTON,
                    TrackRotation.NORTH,
                    trackTileParent.getModel().getPosition().x/64,
                    trackTileParent.getModel().getPosition().y/64 - 1);
            map.getTracks().add(track_button);
            loadButton(track_button, map.getScale(), trackTileParent);
            isStuck = false;
        }
        if(trackTileParent.getModel().getRotation() != TrackRotation.WEST &&
                isTileAvailable(trackTileParent.getModel().getPosition().x/64 + 1, trackTileParent.getModel().getPosition().y/64)){

            Track track_button = new Track(trackTileParent.getModel().getId(),
                    TrackType.TRACK_BUTTON,
                    TrackRotation.EAST,
                    trackTileParent.getModel().getPosition().x/64 + 1,
                    trackTileParent.getModel().getPosition().y/64);
            map.getTracks().add(track_button);
            loadButton(track_button, map.getScale(), trackTileParent);
            isStuck = false;
        }
        if(trackTileParent.getModel().getRotation() != TrackRotation.NORTH &&
                isTileAvailable(trackTileParent.getModel().getPosition().x/64, trackTileParent.getModel().getPosition().y/64 + 1)){

            Track track_button = new Track(trackTileParent.getModel().getId(),
                    TrackType.TRACK_BUTTON,
                    TrackRotation.SOUTH,
                    trackTileParent.getModel().getPosition().x/64,
                    trackTileParent.getModel().getPosition().y/64 + 1);
            map.getTracks().add(track_button);
            loadButton(track_button, map.getScale(), trackTileParent);
            isStuck = false;
        }
        if(trackTileParent.getModel().getRotation() != TrackRotation.EAST &&
                isTileAvailable(trackTileParent.getModel().getPosition().x/64 - 1, trackTileParent.getModel().getPosition().y/64)){

            Track track_button = new Track(trackTileParent.getModel().getId(),
                    TrackType.TRACK_BUTTON,
                    TrackRotation.WEST,
                    trackTileParent.getModel().getPosition().x/64 - 1,
                    trackTileParent.getModel().getPosition().y/64);
            map.getTracks().add(track_button);
            loadButton(track_button, map.getScale(), trackTileParent);
            isStuck = false;
        }


        if(isStuck) {
            trackTileParent.getModel().setType(TrackType.TRACK_END);
            endTrack();
        }

    }

    private void updateOnResize(TrackTile trackTileParent) {


        panel.removeComponentListener(centerTile);
        centerTile = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                MoovingRaceWindow.positionTranslate.setLocation(-trackTileParent.getModel().getPosition().x + panel.getWidth()/2/2 - 64/2, -trackTileParent.getModel().getPosition().y + panel.getHeight()/2/2 - 64/2);

            }
        };
        panel.addComponentListener(centerTile);
        refreshAllBounds();
    }


    private Map createMapFromWindow(String mapName) {

        Map map = new Map(mapName, 64);
        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);

                Track track = new Track(tile.getModel().getType(),
                        tile.getModel().getRotation(),
                        tile.getModel().getPosition().x,
                        tile.getModel().getPosition().y);

                map.getTracks().add(track);
            }
        }
        return map;
    }


    private void endTrack() {

        map = createMapFromWindow(map.getName());
        if(!hasEndTileAvailable()) {
            JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 15, 1));
            JOptionPane.showConfirmDialog(null, jSpinner, "How Many Laps?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            map.setIsLoop(true);
            map.setMaxLaps(((int) jSpinner.getValue()));
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Map.class, new MapSerializer());
        Gson gson = gsonBuilder.create();
        String workingDirectory = Main.getWorkingDirectory();
        File file = new File(workingDirectory);
        file.mkdirs();
        file = new File(workingDirectory+map.getName()+".json");
        String s = gson.toJson(map);
        BufferedWriter fileWriter;
        try {
            fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(s);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved in " + file.getAbsolutePath());
    }

    private void refreshAllBounds() {
        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                tile.refreshBound();
            }
        }
    }
}
