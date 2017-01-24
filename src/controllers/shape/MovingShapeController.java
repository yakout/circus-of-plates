package controllers.shape;

import controllers.shape.util.ShapeMovingObserver;
import controllers.shape.util.ShapeState;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovingShapeController<T extends Node> extends
        ShapeMovementController<T> {
    private final models.Platform platform;
    private double sign;
    private static final Long THREAD_SLEEP_TIME = 10L;
    private final ShapeMovingObserver shapeMovingObserver;
    private static Logger logger = LogManager.getLogger(MovingShapeController
            .class);
    private double offset;
    private final Runnable shapeMover = new Runnable() {

        @Override
        public synchronized void run() {
            while (threadRunning) {
                while (threadPaused) {
                    try {
                        logger.debug("Generation Thread Paused");
                        Thread.currentThread().sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        logger.info("Generation Thread Resumed");
                        break;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final double width = shape.getParent().getLayoutBounds()
                                .getWidth();
                        if (Math.abs(width / 2.0
                                - (shape.getLayoutX() + shape.getTranslateX()
                                + offset))
                                < Math.abs(width / 2.0 -
                                platform.getWidth().doubleValue())) {
                            logger.info("A Shape Reached the End of The "
                                    + "Shelf");
                            shapeMovingObserver.shapeShouldStartFalling();
                        } else {
                            shape.setTranslateX(shape.getTranslateX() + sign *
                                    shapeModel.getHorizontalVelocity());
                        }
                    }
                });
                try {
                    this.wait(THREAD_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    logger.debug("Thread (" + Thread.currentThread()
                            .getName() + ") Interrupted");
                    if (!threadRunning) {
                        break;
                    } else {
                        continue;
                    }
                }
            }
            logger.debug("Thread: " + Thread.currentThread().getName()
                    + " Stopped");
        }
    };

    public MovingShapeController(final T shape, final Shape model,
                                 final models.Platform platform, final
                                 ShapeMovingObserver shapeMovingObserver) {
        super(shape, model);
        this.shapeMovingObserver = shapeMovingObserver;
        this.platform = platform;
        offset = 0;
        switch (platform.getOrientation()) {
            case LEFT:
                logger.info("Movement Requested for A Shape in The Left Half");
                sign = 1;
                break;
            case RIGHT:
                logger.info("Movement Requested for A Shape in The Left Half");
                sign = -1;
                offset = shape.getLayoutBounds().getWidth();
                break;
            default:
                break;
        }
        shapeMovementThread = new Thread(shapeMover,
                "Horizontal Movement Thread " + shape.getId());
        shapeMovementThread.setDaemon(true);
        shapeMovementThread.start();
        logger.debug("A Shape Started Moving");
    }

    @Override
    public void nextState() {
        super.stopMoving();
    }

    @Override
    public boolean hasNextState() {
        return true;
    }

    @Override
    public void pauseCurrentState() {
        super.pauseMovement();
    }

    @Override
    public void resumeCurrentState() {
        super.resumeMovement();
    }
}
