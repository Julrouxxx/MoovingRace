package upsaclay.moovingrace;

import upsaclay.moovingrace.components.InfoText;
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
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);

        positionTranslate = new Point(0, 0);
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

                scale = 2f;
                for (Component component : panel.getComponents()) {
                    if(component instanceof TrackTile){
                        ((TrackTile) component).refreshBound();
                    } else if(component instanceof Car){
                        ((Car) component).refreshBound();
                    }else if(component instanceof InfoText){
                        ((InfoText) component).refreshBound();
                    }
                }
            }
        });
        Map map = MapManager.getInstance().getMapByName("complex");
        Car car = new Car(panel,  map);

        panel.add(car);
        panel.add(new InfoText(car));
        for (Track track : map.getTracks()) {
            loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }

        car.getModel().start();
        pack();
        repaint();
    }


    private void loadImage(int x, int y, TrackType type, TrackRotation rotation, int scale) {

        TrackTile trackTile = new TrackTile(type, rotation, scale, new Point(x, y));
        trackTile.refreshBound();

        panel.add(trackTile);

    }
}