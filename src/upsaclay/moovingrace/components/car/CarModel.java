package upsaclay.moovingrace.components.car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class CarModel {

    private Image sprite;

    private final float DECELERATION = 0.98f;
    private float positionX;
    private float positionY;
    private float velocity;
    private float rotation;

    public CarModel(Car car) {
        loadImage();

        this.positionX = 64;
        this.positionY = 64;
        this.velocity = 3;
        this.rotation = 0;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updatePosition();
                rotation = rotation >= 359 ? 0 : rotation+1;
                updateVelocity();
                car.refreshBound();
            }
        }, 0, 17);
    }

    private void updatePosition() {
        this.positionX += this.velocity * Math.cos(Math.toRadians(getRotation()));
        this.positionY += this.velocity * Math.sin(Math.toRadians(getRotation()));
        //System.out.println(this.position);
    }

    private void updateVelocity() {
        this.velocity = this.velocity * DECELERATION;
        //System.out.println(this.velocity);
    }

    private void loadImage() {
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sprites/car_sprite.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getSprite() {
        return sprite;
    }

    public float getRotation() {
        return rotation;
    }

    public Point getPosition() {
        return new Point(Math.round(this.positionX), Math.round(this.positionY));
    }
}
