package upsaclay.moovingrace;

import upsaclay.moovingrace.components.InfoText;
import upsaclay.moovingrace.components.car.Car;
import upsaclay.moovingrace.components.car.CarModel;
import upsaclay.moovingrace.components.tracktile.TrackTile;
import upsaclay.moovingrace.utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

public class MoovingRaceWindow extends JFrame {

    JPanel panel;
    public static Point positionTranslate;
    public static float scale;

    /**
     * constructor (load menu)
     */
    public MoovingRaceWindow() {

        super("MoovingRace");
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {

            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, (Objects.requireNonNull(getClass().getResourceAsStream("/Minecraft.ttf")))));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(600, 400));
        setMinimumSize(getPreferredSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(getDialogIcon().getImage());
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);

        positionTranslate = new Point(0, 0);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                scale = 2f;
                refreshAllBounds();
            }


        });


        panel = new JPanel();
        panel.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been pressed.
             *
             * @param e
             */
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    createMenu();
                }
            }
        });
        panel.setFocusable(true);
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);
        createMenu();
        pack();
        repaint();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadImage(int x, int y, TrackType type, TrackRotation rotation, int scale) {

        TrackTile trackTile = new TrackTile(type, rotation, scale, new Point(x, y));
        trackTile.refreshBound();

        panel.add(trackTile);

    }

    /**
     * refresh all bounds
     */
    private void refreshAllBounds() {
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

    /**
     * create menu UI
     */
    public void createMenu() {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        Font titleFont = new Font("Minecraft", Font.PLAIN, 64);
        Font buttonFont = new Font("Minecraft", Font.PLAIN, 16);
        panel.add(getIconLabel());
        panel.add(getTitleLabel(titleFont));
        panel.add(getPlayButton(buttonFont));
        panel.add(getEditorButton(buttonFont));
        panel.add(getQuitButton(buttonFont));
        panel.add(Box.createVerticalGlue());
        panel.repaint();
        panel.revalidate();
    }

    private JButton getQuitButton(Font buttonFont) {
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(buttonFont);
        quitButton.addActionListener(e -> dispose());
        quitButton.setBackground(new Color(0, 170, 170));
        quitButton.setFocusPainted(false);
        quitButton.setBorderPainted(false);

        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return quitButton;
    }

    private JButton getEditorButton(Font buttonFont) {
        JButton editorButton = new JButton("Map Editor");
        editorButton.setFont(buttonFont);
        editorButton.setBackground(new Color(0, 170, 170));
        editorButton.setFocusPainted(false);
        editorButton.setBorderPainted(false);

        editorButton.addActionListener(e ->{
            JTextField mapNameField = new JTextField();
            int c = JOptionPane.showConfirmDialog(panel, mapNameField, "Choose map name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getDialogIcon());
            if (c != 0 || mapNameField.getText().isBlank()) return;
            createMapEditor(mapNameField.getText());
        });
        editorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return editorButton;
    }

    private JButton getPlayButton(Font buttonFont) {
        JButton playButton = new JButton("Play!");
        playButton.setFont(buttonFont);
        playButton.setBackground(new Color(0, 170, 170));
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);

        playButton.addActionListener(e -> {
            JComboBox<String> mapList = new JComboBox<>(MapManager.getInstance().getMapList());

            int c = JOptionPane.showConfirmDialog(panel, mapList, "Choose map", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getDialogIcon());
            if(c != 0) return;
            createGame(MapManager.getInstance().getMapByName(((String) mapList.getSelectedItem())));

        });
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return playButton;
    }

    private JLabel getTitleLabel(Font titleFont) {
        JLabel titleLabel = new JLabel("MoovingRace");


        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 50, 0));
        return titleLabel;
    }

    private JLabel getIconLabel() {
        JLabel iconLabel = new JLabel(new ImageIcon(getDialogIcon().getImage().getScaledInstance(100, 61, Image.SCALE_DEFAULT)));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return iconLabel;
    }

    /**
     * replace java icon by a car in dialog
     * @return
     */
    public ImageIcon getDialogIcon() {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/car_sprite.png")).readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * create map editor ui
     * @param mapName
     */
    public void createMapEditor(String mapName) {
        panel.removeAll();
        panel.setLayout(null);
        new MapEditor(mapName, this);
        panel.repaint();

    }

    /**
     * create game UI (load map, etc)
     * @param map
     */
    public void createGame(Map map) {
        panel.removeAll();
        panel.setLayout(null);
        Car car = new Car(panel, map);
        panel.add(car);
        panel.add(new InfoText(car));

        for (Track track : map.getTracks()) {
            loadImage(track.getPositionX(map.getScale()), track.getPositionY(map.getScale()), track.getType(), track.getRotation(), map.getScale());
        }
        car.addChangeListener(e -> {
            if (((CarModel) e.getSource()).isEndRequested()) {
                ((CarModel) e.getSource()).end();
                createMenu();
            }});
        car.getModel().start();
        refreshAllBounds();
        car.requestFocusInWindow();
    }
}