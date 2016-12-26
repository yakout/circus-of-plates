package views.test;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by Moham on 26-Dec-16.
 */
public class PlateController<T extends Shape> implements Runnable {
    private T plate;
    public static final double PLATE_SPEED = 17;
    public static final double STEP = 50;
    private double sign;
    private enum PlateState {
        MOVING,
        IN_STACK,
        INACTIVE
    }
    private PlateState state;
    public PlateController(T plate, boolean left) {
        this.plate = plate;
        if (left) {
            sign = 1;
        } else {
            sign = -1;
        }
        state = PlateState.MOVING;
    }

    public void move() {
        TranslateTransition tth = new TranslateTransition(Duration.millis
                (PLATE_SPEED * STEP * 2), plate);
        TranslateTransition ttv = new TranslateTransition(Duration.millis
                (PLATE_SPEED * STEP * 3), plate);
        tth.setByX(sign * (350 - 1.5 * plate.getLayoutBounds().getWidth()));
        ttv.setByY(500);
        SequentialTransition sq = new SequentialTransition(plate, tth, ttv);
        sq.setCycleCount(Animation.INDEFINITE);
        if (state == PlateState.MOVING) {
            sq.play();
        } else {
            sq.pause();
        }
    }

    @Override
    public synchronized void run() {
        TranslateTransition tt = new TranslateTransition(Duration.millis
                (STEP), plate);
        //double center = plate.getScene().getX() + plate.getScene().getWidth()
        //       /2.0;
        // double boundary = center + 61;
        while (true) {
            if (plate.getLayoutY() + plate.getTranslateY() >= 500) {
                tt.setByX(-plate.getTranslateX());
                tt.setByY(-plate.getTranslateY());
            } else if (Math.abs(350 - (plate.getLayoutX() + 61/2.0 + plate
                    .getTranslateX() -
                    sign * PLATE_SPEED)) <
                     61) {
                tt.setByY(PLATE_SPEED);
                tt.setByX(0);
            } else {
                tt.setByX(sign * PLATE_SPEED);
                tt.setByY(0);
            }
            tt.play();
            try {
                Thread.sleep(10);
                notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
