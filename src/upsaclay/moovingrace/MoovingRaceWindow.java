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
import java.util.ArrayList;

public class MoovingRaceWindow extends JFrame {

    JPanel panel;
    public static Point positionTranslate;
    public static float scale;

    public MoovingRaceWindow() {

        super("MoovingRace");
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        positionTranslate = new Point(-64*8, -64*9);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    positionTranslate.x++;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    positionTranslate.x--;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    positionTranslate.y++;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    positionTranslate.y--;
                }
                refreshAllBounds();
                repaint();
                System.out.println(panel.getComponents().length);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scale = 2;

                for (Component component : panel.getComponents()) {
                    if(component instanceof TrackTile){
                        ((TrackTile) component).refreshBound();
                    } else if(component instanceof Car){
                        ((Car) component).refreshBound();
                    }
                }
            }
        });

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);
        //createGame();
        createMapEditor("test");

        //Map map2 = createMapFromWindow("aa");
        //System.out.println(map2.getTracks().size());

        pack();
        repaint();
    }



    private void loadImage(int x, int y, TrackType type, TrackRotation rotation, int scale) {

        TrackTile trackTile = new TrackTile(type, rotation, scale, new Point(x, y));
        trackTile.refreshBound();

        panel.add(trackTile);

    }

    private void refreshAllBounds() {
        for (Component component : panel.getComponents()) {
            if(component instanceof TrackTile){
                TrackTile tile = ((TrackTile) component);
                tile.refreshBound();
            }
        }
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

    public void createMapEditor(String mapName) {
        panel.removeAll();

        MapEditor editor = new MapEditor(mapName, panel);
    }

    public void createGame() {
        panel.removeAll();

        Car car = new Car();
        panel.add(car);

        Map map = MapManager.getInstance().getMapByName("complex");
        for (Track track : map.getTracks()) {
            loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }
    }
}