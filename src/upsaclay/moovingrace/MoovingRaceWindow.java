package upsaclay.moovingrace;

import upsaclay.moovingrace.components.car.Car;
import upsaclay.moovingrace.components.tracktile.TrackTile;
import upsaclay.moovingrace.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MoovingRaceWindow extends JFrame {

    JPanel panel;
    public static Point positionTranslate;
    public static float scale;

    public MoovingRaceWindow() {

        super("MoovingRace");
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        positionTranslate = new Point(10, 10);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    positionTranslate.x--;
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    positionTranslate.x++;
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    positionTranslate.y--;
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    positionTranslate.y++;
                }
                repaint();
            }
        });
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scale = (float) (Math.round(getHeight() * 64.0/200) / 64.0);

                for (Component component : panel.getComponents()) {
                    if(component instanceof TrackTile){
                        ((TrackTile) component).refreshBound();
                    } else if(component instanceof Car){
                        ((Car) component).refreshBound();
                    }
                }
            }
        });
        Map map = MapManager.getInstance().getMapByName("complex");
        for (Track track : map.getTracks()) {
            //loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }

        Car car = new Car();
        panel.add(car);


        pack();
        repaint();
    }


    private void loadImage(int x, int y, TrackType type, TrackRotation rotation, int scale) {

        TrackTile trackTile = new TrackTile(type, rotation, scale, new Point(x, y));
        trackTile.refreshBound();

        panel.add(trackTile);

    }
}