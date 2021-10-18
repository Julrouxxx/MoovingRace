package upsaclay.moovingrace.components;

import upsaclay.moovingrace.components.car.CarModel;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class InfoTextUI {
    Font font;
    Font startingFont;
    public InfoTextUI(){
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {

            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, (Objects.requireNonNull(getClass().getResourceAsStream("/Minecraft.ttf")))));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        font = new Font("Minecraft", Font.PLAIN, 32);
        startingFont = new Font("Minecraft", Font.PLAIN, 64);

    }
    public void paint(Graphics2D g, CarModel model){
        int xWindow = model.getContext().getBounds().width;
        int yWindow = model.getContext().getBounds().height;
        g.setPaint(Color.BLACK);
        g.setFont(font);
        if (model.isLoop())
            g.drawString("Lap: " + model.getLap() + "/" + model.getMap().getMaxLap().orElse(0), 100, 50);

        long milis = (model.hasEnded() ? model.getEndDate().getTime() : new Date().getTime()) - model.getStartDate().getTime();
        long m = milis % 1000;
        long s = (milis - m) / 1000;
        if (model.isStarted() && !model.hasEnded())
            g.drawString(s + "''" + m,
                    xWindow - 150,
                    50);
        else {
            g.setFont(startingFont);
            g.drawString((model.hasEnded() ? s + "''" + m : String.valueOf(Math.abs(s))),
                    xWindow - xWindow / 2 - (model.hasEnded() ? 50 : 0),
                    yWindow / 4);
            if (model.hasEnded()) {
                g.drawString("Game Over!", xWindow - xWindow / 2 - 150, yWindow - yWindow / 4);
                g.setFont(font);
                g.drawString("Press ESC to quit", xWindow - xWindow / 2 - 100, yWindow - yWindow / 4 + 50);

            }
        }

    }
}
