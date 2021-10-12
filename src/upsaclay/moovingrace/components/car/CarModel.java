package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.components.tracktile.TrackTile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class CarModel {

    private Image sprite;

    private final float DECELERATION = 0.98f;
    private float positionX;
    private float positionY;
    private float velocity;
    private Car car;
    private JPanel context;
    private float rotation;
    private List<TrackTile> collisions;
    private boolean isCollided = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isRight = false;
    private boolean isLeft = false;

    public CarModel(Car car, JPanel context) {
        loadImage();
        this.positionX = 64;
        this.positionY = 64;
        this.velocity = 0;
        this.rotation = 0;
        this.car = car;
        this.collisions = new ArrayList<>();
        this.context = context;


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateEvent();
                updatePosition();
                updateVelocity();
                car.refreshBound();
                isCollided = checkCollision();
                reverseVelocity();
                reversePosition();
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

    private void reverseVelocity() {
        if(!isCollided){
            return;
        }
        this.velocity = this.velocity / DECELERATION;
    }

    private void reversePosition() {
        if(!isCollided){
            return;
        }
        this.positionX -= this.velocity * Math.cos(Math.toRadians(getRotation()));
        this.positionY -= this.velocity * Math.sin(Math.toRadians(getRotation()));
        this.velocity = 0;
        //System.out.println(this.position);
    }
    private void updatePosition() {
        this.positionX += this.velocity * Math.cos(Math.toRadians(getRotation()));
        this.positionY += this.velocity * Math.sin(Math.toRadians(getRotation()));
        //System.out.println(this.position);
    }

    private boolean checkCollision() {
        for (TrackTile collision : collisions) {

            if(!collision.getBounds().intersects(car.getBounds())) continue;
            Point position = new Point(Math.round(positionX), Math.round(positionY));
            position.translate(-collision.getModel().getPosition().x + 13, -collision.getModel().getPosition().y + 13);
           //position.translate((velocity > 0) ? 15 : 0, (rotation > 0) ? 15 : 0);
            if(position.x < 0 || position.y < 0 || collision.getModel().getImage().getWidth(null) <= position.x || collision.getModel().getImage().getHeight(null) <= position.y){
                continue;
            }
            int rgb = collision.getModel().getImage().getRGB(position.x, position.y);
            int alpha = (rgb & 0xff000000) >>> 24;
            if(alpha != 0){
                return false;
            }
        }
        return true;
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
    public void start(){
        for (Component component : context.getComponents()) {
            if(component instanceof TrackTile)
                collisions.add(((TrackTile) component));
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

        rotation = rotation >= 359 ? 0 + velocity/2 + 2 : rotation + velocity/2 + 2;
    }

    private void rotateLeft() {

        rotation = rotation <= 0 ? 360 - velocity/2 - 2 : rotation - velocity/2 - 2;
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
