package upsaclay.moovingrace;

import upsaclay.moovingrace.components.TrackTile;
import upsaclay.moovingrace.utils.MapManager;
import upsaclay.moovingrace.utils.Track;
import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import javax.swing.*;
import java.awt.*;

public class MoovingRaceWindow extends JFrame {

    JPanel panel;

    public MoovingRaceWindow() {

        super("MoovingRace");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);

        for (Track track : MapManager.getInstance().getMapByName("classic").getTracks()) {
            loadImage(track.getPositionX(64), track.getPositionY(64), track.getType(), track.getRotation());
        }


        pack();
        repaint();
    }


    private void loadImage(int x, int y, TrackType type, TrackRotation rotation) {

        TrackTile trackTile = new TrackTile(type, rotation);
        trackTile.setBounds(x, y, 64, 64);

        panel.add(trackTile);

    }
}