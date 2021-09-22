package upsaclay.moovingrace;

import upsaclay.moovingrace.components.TrackTile;
import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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

        TrackTile trackTile = new TrackTile(TrackType.TRACK_CLASSIC, TrackRotation.EAST);
        loadImage(0, 0);
        loadImage(64, 64);
        loadImage( 128, 128);


        pack();
        repaint();
    }


    private void loadImage(int x, int y) {

        TrackTile trackTile = new TrackTile(TrackType.TRACK_CLASSIC, TrackRotation.EAST);
        trackTile.setBounds(x, y, 64, 64);

        panel.add(trackTile);

    }
}