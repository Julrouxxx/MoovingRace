package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.utils.Map;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Car extends JComponent {

    public CarModel carModel;
    public CarUI ui;
    public JPanel context;
    public Car(JPanel context, Map map) {
        setFocusable(true);
        this.carModel = new CarModel(this, context, map);
        this.ui = new CarUI();
        this.context = context;
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
        setBounds(context.getSize().width/2 - Math.round(MoovingRaceWindow.scale*25)/2,
                context.getSize().height/2 - Math.round(MoovingRaceWindow.scale*25)/2,
                Math.round(MoovingRaceWindow.scale*25),
                Math.round(MoovingRaceWindow.scale*25));
        carModel.setOffSetX(context.getSize().width/2.0f - Math.round(MoovingRaceWindow.scale*25)/2.0f);
        carModel.setOffSetY(context.getSize().height/2.0f - Math.round(MoovingRaceWindow.scale*25)/2.0f);
        //System.out.println(carModel.getPosition());
        repaint();
    }
    public void addChangeListener(ChangeListener changeListener){
        getModel().getChangeListeners().add(changeListener);
    }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() {
        return new Dimension(23, 23);
    }
    public Dimension getMaximumSize() { return getPreferredSize(); }
}
