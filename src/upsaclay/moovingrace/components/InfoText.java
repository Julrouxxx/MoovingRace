package upsaclay.moovingrace.components;

import upsaclay.moovingrace.components.car.Car;

import javax.swing.*;
import java.awt.*;

public class InfoText extends JComponent {
    private Car car;
    private InfoTextUI ui;
    public InfoText(Car car) {
        this.car = car;
        this.ui = new InfoTextUI();
        car.addChangeListener(e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ui.paint(((Graphics2D) g), car.getModel());
    }
    public void refreshBound(){
        setBounds(0, 0, car.getModel().getContext().getBounds().width, car.getModel().getContext().getBounds().height);
    }
}
