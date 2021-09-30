package upsaclay.moovingrace.components.car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class CarModel {

    private Image sprite;

    private final float DECELERATION = 0.98f;
    private float positionX;
    private float positionY;
    private float velocity;
    private float rotation;

    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isRight = false;
    private boolean isLeft = false;

    public CarModel(Car car) {
        loadImage();

        this.positionX = 64;
        this.positionY = 64;
        this.velocity = 0;
        this.rotation = 0;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateEvent();
                updatePosition();
                updateVelocity();
                car.refreshBound();
            }
        }, 17, 17);


        car.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeft = false;
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRight = false;
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    isUp = false;
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isDown = false;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeft = true;
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRight = true;
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    isUp = true;
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isDown = true;
                }
            }
        });
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

    private void impulse(float force) {
        velocity += force;
    }

    private void rotateRight() {

        rotation = rotation >= 359 ? 0 + velocity : rotation + velocity;
    }

    private void rotateLeft() {

        rotation = rotation <= 0 ? 360 - velocity : rotation - velocity;
    }

    private void updateEvent() {

        if(isLeft) {
            //Rotate Left
            rotateLeft();
        }
        if(isRight) {
            //Rotate Right
            rotateRight();
        }
        if(isUp) {
            impulse(0.1f);
        }
        if(isDown) {
            impulse(-0.1f);
        }
    }
}
