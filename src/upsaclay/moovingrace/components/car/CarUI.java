package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class CarUI {

    public void paint(Graphics2D g, Car car) {
        CarModel model = car.getModel();
        /*Image sprite = model.getSprite();
        sprite = sprite.getScaledInstance( Math.round(MoovingRaceWindow.scale *23),
                Math.round(MoovingRaceWindow.scale*14),
                Image.SCALE_DEFAULT);
        float angle = model.getRotation();
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle),
                sprite.getWidth(null)/2f,
                sprite.getHeight(null)/2f);
        g.setColor(Color.black);
        g.fillRect(0, 0, car.getBounds().width, car.getBounds().height);
        g.drawImage(sprite, rotation, null);*/


        BufferedImage sprite = (BufferedImage) model.getSprite();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(model.getRotation()));
        int x = (car.getWidth() - sprite.getWidth()) / 2;
        int y = (car.getHeight() - sprite.getHeight()) / 2;
        System.out.println(y);
        g2d.drawImage(sprite, x, y, car);
        g2d.dispose();

    }
}
