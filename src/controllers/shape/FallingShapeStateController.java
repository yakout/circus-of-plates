package controllers.shape;

import controllers.shape.util.ShapeFallingObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallingShapeStateController<T extends Node> extends
        ShapeMovementController<T> {
    private static Logger logger = LogManager.getLogger
            (FallingShapeStateController.class);
    private static final Long THREAD_SLEEP_TIME = 10L;
    private final ShapeFallingObserver shapeFallingObserver;
    private final Runnable shapeMover = new Runnable() {

        @Override
        public synchronized void run() {
            while (threadRunning) {
                while (threadPaused) {
                    try {
                        Thread.currentThread().sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (shape.getLayoutY()
                                + shape.getTranslateY()
                                + shape.getLayoutBounds().getHeight()
                                >= shape.getParent()
                                .getLayoutBounds().getHeight()) {
//							logger.info("A Shape Hit the Ground");
                            shapeFallingObserver.shapeShouldStopFalling();
                        } else {
                            shape.setTranslateY(shape.getTranslateY() +
                                    shapeModel.getVerticalVelocity());
                        }
                        shapeFallingObserver.checkIntersection();
                    }
                });
                try {
                    this.wait(THREAD_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    if (!threadRunning) {
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
    };

    public FallingShapeStateController(final T shape, final Shape model
            , final ShapeFallingObserver shapeFallingObserver) {
        super(shape, model);
        this.shapeFallingObserver = shapeFallingObserver;
        shapeMovementThread = new Thread(shapeMover,
                "Vertical Movement Thread:" + shape.getId());
        shapeMovementThread.setDaemon(true);
        shapeMovementThread.start();
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
