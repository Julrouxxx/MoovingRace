package upsaclay.moovingrace;

import upsaclay.moovingrace.components.TrackTile;
import upsaclay.moovingrace.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                float scale = (float) (Math.round(getHeight() * 64.0/400) / 64.0);
                for (Component component : panel.getComponents()) {
                    if(component instanceof TrackTile){
                        ((TrackTile) component).setScale(scale);
                    }
                }
            }
        });
        Map map = MapManager.getInstance().getMapByName("classic");
        for (Track track : map.getTracks()) {
            loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }


        pack();
        repaint();
    }


    private void loadImage(int x, int y, TrackType type, TrackRotation rotation, int scale) {

        TrackTile trackTile = new TrackTile(type, rotation, scale, new Point(x, y));
        trackTile.setScale(scale);

        panel.add(trackTile);

    }
}