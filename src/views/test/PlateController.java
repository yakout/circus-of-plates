package views.test;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Moham on 26-Dec-16.
 */
public class PlateController<T extends Shape> implements Runnable {
    private T plate;
    private static final double PLATE_SPEED = 20;
    private static final double PLATE_X_SPEED = 2.3;
    private static final double PLATE_Y_SPEED = 2.0;
    private static final double STEP = 50;
    private double sign;
    private double rodLength;
    private double offset = 61;
    private enum PlateState {
        MOVING,
        IN_STACK,
        INACTIVE
    }
    private PlateState state;
    public PlateController(T plate, boolean left, double rodLength) {
        this.plate = plate;
        this.rodLength = rodLength;
        if (left) {
            sign = 1;
            offset = 0;
        } else {
            sign = -1;
        }
        state = PlateState.MOVING;
        /*Thread movingThread = new Thread(moveTask, "plate movement thread");
        movingThread.setDaemon(true);
        movingThread.start();*/
    }

    Task<DoubleProperty> moveTask = new Task<DoubleProperty>() {
        @Override
        protected DoubleProperty call() throws Exception {
            /*double center = plate.getParent().getLayoutBounds().getMinX() +
                    plate
                            .getParent()
                            .getLayoutBounds().getWidth() / 2.0;*/
            boolean fxApplicationThread = Platform.isFxApplicationThread();
            System.out.println("Is call on FXApplicationThread: " + fxApplicationThread);
            while (!isCancelled()) {
                if (plate.getLayoutY() + plate.getTranslateY() >= 500) {
                    plate.setTranslateX(0);
                    plate.setTranslateY(0);
                } else if (Math.abs(350 - (plate.getLayoutX() + 61 / 2.0 +
                        plate
                                .getTranslateX() -
                        sign * PLATE_X_SPEED)) <
                        61) {
                    plate.setTranslateY(plate.getTranslateY() +
                            PLATE_Y_SPEED);
                } else {
                    plate.setTranslateX(plate.getTranslateX() + sign *
                            PLATE_X_SPEED);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Thread (" + Thread.currentThread()
                            .getName() + ") Interrupted");
                }
            }
            return null;
        }
    };


        public void move() {
            TranslateTransition tth = new TranslateTransition(Duration.millis
                    (PLATE_SPEED * STEP * 2), plate);
            TranslateTransition ttv = new TranslateTransition(Duration.millis
                    (PLATE_SPEED * STEP * 3), plate);
            tth.setByX(sign * rodLength);
            ttv.setByY(500);
            SequentialTransition sq = new SequentialTransition(plate, tth, ttv);
            sq.setCycleCount(Animation.INDEFINITE);
            sq.setDelay(Duration.ZERO);
            if (state == PlateState.MOVING) {
                sq.play();
            } else {
                sq.pause();
            }
        }

        @Override
        public synchronized void run() {
            double center = plate.getParent().getLayoutBounds().getMinX() +
                    plate
                    .getParent()
                    .getLayoutBounds().getWidth() / 2.0;
            //       /2.0;
            // double boundary = center - sign * 61 / 2.0;
            while (true) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (plate.getLayoutY() + plate.getTranslateY() >= 500) {
                            plate.setTranslateX(0);
                            plate.setTranslateY(0);
                        } else if (Math.abs(350 - (plate.getLayoutX() + plate
                                    .getTranslateX() + offset)) < Math.abs(350 -
                                rodLength)) {
                            /*Math.abs(350 - (plate.getLayoutX() + 61 /
                                    2.0 +
                                    plate
                                            .getTranslateX() -
                                    sign * PLATE_SPEED / 7.5)) <
                                    61*/

                            plate.setTranslateY(plate.getTranslateY() +
                                    PLATE_SPEED / 4);
                        } else {
                            plate.setTranslateX(plate.getTranslateX() + sign *
                                    PLATE_SPEED / 7.5);
                        }
                    }
                });
                try {
                    this.wait(10);
                } catch (InterruptedException e) {
                    System.out.println("Thread (" + Thread.currentThread()
                            .getName() + ") Interrupted");
                    break;
                }
            }
        }
    }
