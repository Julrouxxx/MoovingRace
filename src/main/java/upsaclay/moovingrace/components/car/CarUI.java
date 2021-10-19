package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class CarUI {

    public void installUI(Car car){
        CarModel m = car.getModel();
        car.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    m.setLeft(false);
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    m.setRight(false);
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    m.setUp(false);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    m.setDown(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    m.setLeft(true);
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    m.setRight(true);
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    m.setUp(true);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    m.setDown(true);
                }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    m.setRequestEnd(true);
                }
            }
        });
    }

    public void paint(Graphics2D g, Car car) {
        CarModel model = car.getModel();
        Image sprite = model.getSprite();
        sprite = sprite.getScaledInstance( Math.round(MoovingRaceWindow.scale *23),
                Math.round(MoovingRaceWindow.scale*14),
                Image.SCALE_DEFAULT);
        float angle = model.getRotation();
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle),
                car.getBounds().getWidth()/2f,
                car.getBounds().getHeight()/2f);
        //rotation.translate(car.getBounds().getWidth()/2f, car.getBounds().getHeight()/2f);
        rotation.translate((car.getBounds().getWidth() - sprite.getWidth(null))/2f,
                (car.getBounds().getHeight() - sprite.getHeight(null))/2f);
        //g.setColor(Color.black);
        //g.fillRect(0, 0, car.getBounds().width, car.getBounds().height);
        g.drawImage(sprite, rotation, null);
    }
}
