package upsaclay.moovingrace.components;

import upsaclay.moovingrace.components.car.CarModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class InfoTextUI {
    Font font;
    Font startingFont;
    public InfoTextUI(){
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/Minecraft.ttf").toURI())));
        } catch (FontFormatException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        font = new Font("Minecraft", Font.PLAIN, 32);
        startingFont = new Font("Minecraft", Font.PLAIN, 64);

    }
    public void paint(Graphics2D g, CarModel model){
        int xWindow = model.getContext().getBounds().width;
        int yWindow = model.getContext().getBounds().height;
        System.out.println(xWindow);
        g.setPaint(Color.BLACK);
        g.setFont(font);
        if(model.isLoop())
            g.drawString("Lap: " + model.getLap(), 100, 50);

        long milis = new Date().getTime() - model.getStartDate().getTime();
        long m = milis%1000;
        long s = (milis - m) / 1000;
        if(model.isStarted())
            g.drawString(s + ":" + m,
                xWindow - 100,
                50);
        else {
            g.setFont(startingFont);
            g.drawString("" + Math.abs(s),
                    xWindow/2,
                    yWindow/2);
        }

    }
}
