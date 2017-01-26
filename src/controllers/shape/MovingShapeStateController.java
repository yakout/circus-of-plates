package controllers.shape;

import controllers.main.GameController;
import controllers.shape.util.ShapeMovingObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovingShapeStateController<T extends Node> extends
        ShapeMovementController<T> {
    private final models.Platform platform;
    private double sign;
    private static final Long THREAD_SLEEP_TIME = 10L;
    private final ShapeMovingObserver shapeMovingObserver;
    private static Logger logger = LogManager.getLogger
            (MovingShapeStateController
                    .class);
    private double offset;
    private final Runnable shapeMover = new Runnable() {

        @Override
        public synchronized void run() {
            while (threadRunning) {
                while (threadPaused) {
                    try {
                        //logger.debug("Horizontal Movement Thread Paused");
                        Thread.currentThread().sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        //logger.info("Horizontal Movement Thread Resumed");
                        break;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (shape == null) {
                            return;
                        }
                        if (shape.getParent() == null) {
                            return;
                        }
                        final double width = shape.getParent().getLayoutBounds()
                                .getWidth();
                        if (Math.abs(width / 2.0
                                - (shape.getLayoutX() + shape.getTranslateX()
                                + offset))
                                < Math.abs(width / 2.0 -
                                platform.getWidth().doubleValue())) {
                            //  logger.info("A Shape Reached the End of The "
                            //        + "Shelf");
                            shapeMovingObserver.shapeShouldStartFalling();
                        } else {
                            shape.setTranslateX(shape.getTranslateX() + sign *
                                    GameController.getInstance()
                                            .getCurrentGame().getCurrentLevel
                                            ().getShapeSpeedRatio() * shapeModel
                                    .getHorizontalVelocity());
                        }
                    }
                });
                if (shape == null) {
                    return;
                }
                if (shape.getParent() == null) {
                    return;
                }
                try {
                    this.wait(THREAD_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    //logger.debug("Thread (" + Thread.currentThread()
                    //.getName() + ") Interrupted");
                    if (!threadRunning) {
                        break;
                    } else {
                        continue;
                    }
                }
            }
            //logger.debug("Thread: " + Thread.currentThread().getName()
            //      + " Stopped");
        }
    };

    /**
     * default constructor.
     * @param shape Shape view.
     * @param model Shape model.
     * @param platform {@link models.Platform} platform model.
     * @param shapeMovingObserver {@link ShapeMovingObserver} obserber of the
     * moving shape.
     */
    public MovingShapeStateController(final T shape, final Shape model,
                                      final models.Platform platform, final
                                      ShapeMovingObserver shapeMovingObserver) {
        super(shape, model);
        this.shapeMovingObserver = shapeMovingObserver;
        this.platform = platform;
        offset = 0;
        switch (platform.getOrientation()) {
            case LEFT:
                //logger.info("Movement Requested for A Shape in The Left "
                //   + "Half");
                sign = 1;
                break;
            case RIGHT:
                //logger.info("Movement Requested for A Shape in The Left "
                //  + "Half");
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
        //logger.debug("A Shape Started Moving");
    }

    /**
     * Next state after falling.
     */
    @Override
    public void nextState() {
        super.stopMoving();
    }

    /**
     * Checks whether this state has next or not.
     * @return whether this state has next or not.
     */
    @Override
    public boolean hasNextState() {
        return true;
    }

    /**
     * Pauses the moving shape-thread.
     */
    @Override
    public void pauseCurrentState() {
        super.pauseMovement();
    }

    /**
     * Resumes the moving shape-thread.
     */
    @Override
    public void resumeCurrentState() {
        super.resumeMovement();
    }
}
