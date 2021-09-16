package upsaclay.moovingrace;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MoovingRaceWindow extends JFrame {

    JPanel panel;

    public MoovingRaceWindow() {

        super("MoovingRace");

        setPreferredSize(new Dimension(600, 400));

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.green);
        add(panel, BorderLayout.CENTER);

        loadImage(0, 0);
        loadImage(64, 64);
        loadImage( 128, 128);


        pack();
    }

    private void loadImage(int x, int y) {

        JLabel image = new JLabel();
        image.setBounds(x, y, 64, 64);
        BufferedImage sprites;
        try {
            sprites = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sprites/track_sprites.png")));
            Image sprite = sprites.getSubimage(64, 0, 64, 64);
            image.setIcon(new ImageIcon(sprite));
        } catch (IOException e) {
            e.printStackTrace();
        }


        panel.add(image);

    }
}