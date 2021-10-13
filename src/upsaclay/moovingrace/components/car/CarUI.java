package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class CarUI {

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
