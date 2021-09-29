package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;

import javax.swing.*;
import java.awt.*;

public class Car extends JComponent {

    public CarModel carModel;
    public CarUI ui;

    public Car() {
        carModel = new CarModel(this);
        ui = new CarUI();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.ui.paint((Graphics2D)g, this);
    }

    public CarModel getModel() {
        return carModel;
    }
    public void refreshBound() {

        setBounds(Math.round(((float) getModel().getPosition().getX())* MoovingRaceWindow.scale),
                Math.round (((float) getModel().getPosition().getY())*MoovingRaceWindow.scale),
                Math.round(MoovingRaceWindow.scale*23) + MoovingRaceWindow.positionTranslate.x,
                Math.round(MoovingRaceWindow.scale*23) + MoovingRaceWindow.positionTranslate.y);
        repaint();
    }
}
