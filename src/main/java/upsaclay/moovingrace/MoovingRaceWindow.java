package upsaclay.moovingrace;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.io.FileNotFoundException;
import java.io.FileReader;

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


        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Map.class, new MapSerializer());
        Gson gson = gsonBuilder.create();
        try {
            Map test = gson.fromJson(new FileReader(Main.getWorkingDirectory()+"/test.json"), Map.class);
            MapManager.getInstance().getMaps().add(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createGame(MapManager.getInstance().getMapByName("test"));
        //createMapEditor("test");

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

    public void createMenu(String mapName) {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        MapEditor editor = new MapEditor(mapName, panel);
    }

    public void createMapEditor(String mapName) {
        panel.removeAll();
        panel.setLayout(null);

        MapEditor editor = new MapEditor(mapName, panel);
    }

    public void createGame(Map map) {
        panel.removeAll();
        panel.setLayout(null);

        Car car = new Car(panel, map);
        panel.add(car);
        panel.add(new InfoText(car));

        for (Track track : map.getTracks()) {
            loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }
        car.getModel().start();
    }
}