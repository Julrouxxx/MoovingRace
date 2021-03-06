package upsaclay.moovingrace.components.car;

import upsaclay.moovingrace.MoovingRaceWindow;
import upsaclay.moovingrace.components.tracktile.TrackTile;
import upsaclay.moovingrace.utils.Map;
import upsaclay.moovingrace.utils.TrackType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class CarModel {

    private Image sprite;
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    private final float DECELERATION = 0.98f;
    private float positionX;
    private float positionY;
    private float offSetX;
    private float offSetY;
    private TrackTile currentTile;
    private int lap = 1;
    private Date startDate;
    private boolean requestEnd;
    private Date endDate;
    private boolean loop;
    private float velocity;
    private Car car;
    private boolean isStarted;
    private JPanel context;
    private boolean hasEnded;
    private Timer timer;
    private float rotation;
    private final List<TrackTile> tiles;
    private boolean isCollided = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isRight = false;
    private boolean isLeft = false;
    private final Map map;

    /**
     * Car model constructor
     * @param car
     * @param context
     * @param map
     */
    public CarModel(Car car, JPanel context, Map map) {
        loadImage();
        this.velocity = 0;
        this.rotation = 0;
        this.map = map;
        this.loop = map.isLoop();
        this.car = car;
        this.tiles = new ArrayList<>();
        this.context = context;

        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                triggerListeners();
                if(!isStarted) {
                    updateWindow();

                    checkCountdown();
                    return;
                }
                if(!hasEnded)
                    updateEvent();
                updatePosition();
                updateVelocity();
                car.refreshBound();
                isCollided = checkCollision();
                reverseVelocity();
                reversePosition();
                car.refreshBound();
                updateWindow();
                if(!hasEnded)
                    countLap();

            }
        }, 17, 17);


    }


    /**
     * set the input up
     * @param up
     */
    public void setUp(boolean up) {
        isUp = up;
    }

    /**
     * set the input down
     * @param down
     */
    public void setDown(boolean down) {
        isDown = down;
    }

    /**
     * set the input left
     * @param left
     */
    public void setLeft(boolean left) {
        isLeft = left;
    }

    /**
     * set the input right
     * @param right
     */
    public void setRight(boolean right) {
        isRight = right;
    }

    public void setRequestEnd(boolean requestEnd) {
        this.requestEnd = requestEnd;
    }

    public ArrayList<ChangeListener> getChangeListeners() {
        return changeListeners;
    }

    /**
     * cancel the previous velocity if collision occurs
     */
    private void reverseVelocity() {
        if(!isCollided){
            return;
        }
        this.velocity = this.velocity / DECELERATION;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isEndRequested() {
        return requestEnd;
    }

    public JPanel getContext() {
        return context;
    }

    /**
     * cancel the previous position if collision occurs
     */
    private void reversePosition() {
        if(!isCollided){
            return;
        }
        this.positionX -= this.velocity * Math.cos(Math.toRadians(getRotation()));
        this.positionY -= this.velocity * Math.sin(Math.toRadians(getRotation()));
        this.velocity = 0;
        //System.out.println(this.position);
    }

    /**
     * make the camera follow the car
     */
    private void updateWindow(){
        MoovingRaceWindow.positionTranslate.setLocation(-positionX + offSetX/2, -positionY + offSetY/2);
        tiles.forEach(TrackTile::refreshBound);
    }

    /**
     * update the position of the car depending of rotation and velocity
     */
    private void updatePosition() {
        this.positionX += this.velocity * Math.cos(Math.toRadians(getRotation()));
        this.positionY += this.velocity * Math.sin(Math.toRadians(getRotation()));
        //System.out.println(this.position);
    }

    public boolean isStarted() {
        return isStarted;
    }

    /**
     * check if car is on alpha of a track tile
     * @return is collision occurs ?
     */
    private boolean checkCollision() {
        for (TrackTile collision : tiles) {
            if(!collision.getBounds().intersects(car.getBounds())) continue;
            currentTile = collision;
            collision.getModel().setPassed(true);
            Point position = new Point(Math.round(positionX), Math.round(positionY));
            //System.out.println(position + " : " +collision.getModel().getPosition());
            position.translate(-collision.getModel().getPosition().x + 13,
                    -collision.getModel().getPosition().y + 13);

            //position.translate((velocity > 0) ? 15 : 0, (rotation > 0) ? 15 : 0);
            //System.out.println("result : " + position);

            if(position.x < 0 || position.y < 0 || collision.getModel().getImage().getWidth() <= position.x || collision.getModel().getImage().getHeight() <= position.y){
                continue;
            }
            int rgb = collision.getModel().getImage().getRGB(position.x, position.y);
            int alpha = (rgb & 0xff000000) >>> 24;
            if(alpha != 0){
                //System.out.println("in");
                return false;
            }
        }
        return true;
    }

    /**
     * update the velocity to slow the car smoothly
     */
    private void updateVelocity() {

        this.velocity = this.velocity * DECELERATION;
        //System.out.println(this.velocity);
    }

    /**
     * load car image
     */
    private void loadImage() {
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/car_sprite.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * init car position and rotation depending of the track start
     */
    public void start(){

        for (Component component : context.getComponents()) {
            if(component instanceof TrackTile) {
                tiles.add(((TrackTile) component));
                if(((TrackTile) component).getModel().getType() == TrackType.TRACK_START)
                {
                    this.positionX = ((TrackTile) component).getModel().getPosition().x + 32 - 13;
                    this.positionY = ((TrackTile) component).getModel().getPosition().y + 32 - 13;
                    this.rotation = ((TrackTile) component).getModel().getRotation().getRotation();
                    //System.out.println(getPosition());

                }
            }
        }
        startCountdown();

    }

    private void startCountdown(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        startDate = calendar.getTime();
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

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Point getOffSet() {
        return new Point(Math.round(this.offSetX), Math.round(this.offSetY));
    }

    public void setOffSetX(float offSetX) {
        this.offSetX = offSetX;
    }

    public void setOffSetY(float offSetY) {
        this.offSetY = offSetY;
    }

    private void impulse(float force) {

        velocity += force;
    }

    private void rotateRight() {

        rotation = rotation >= 359 ? 0 + Math.abs(velocity)/2 + 2 : rotation + Math.abs(velocity)/2 + 2;
    }

    private void rotateLeft() {

        rotation = rotation <= 0 ? 360 - Math.abs(velocity)/2 - 2 : rotation - Math.abs(velocity)/2 - 2;
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

    public int getLap() {
        return lap;
    }

    private void countLap(){
        long test = tiles.stream().filter(c -> c.getModel().isPassed()).count();
        if(test < tiles.size()) return;
        if(!loop) {
            if(currentTile.getModel().getType() != TrackType.TRACK_END) return;
            hasEnded = true;
            endDate = new Date();
            return;
        }
        if(currentTile.getModel().getType() != TrackType.TRACK_START) return;
        tiles.forEach(c -> c.getModel().setPassed(false));
        if(lap >= map.getMaxLap().orElse(0)){
            //FINISHED
            hasEnded = true;
            endDate = new Date();
            return;
        }
        lap++;
    }
    private void checkCountdown(){
        if(startDate == null) return;
        if(startDate.before(new Date()))
            isStarted = true;
    }
    private void triggerListeners(){
        changeListeners.forEach(c -> c.stateChanged(new ChangeEvent(this)));
    }

    public Date getStartDate() {
        return startDate;
    }

    public Map getMap() {
        return map;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean hasEnded() {
        return hasEnded;
    }

    public void end(){
        timer.cancel();
    }
}
