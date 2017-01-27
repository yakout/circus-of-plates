package controllers.shape;

import controllers.shape.util.ShapeState;
import javafx.scene.Node;
import models.shapes.Shape;

public abstract class ShapeMovementController<T extends Node>
        implements ShapeState {
    protected T shape;
    protected final Shape shapeModel;
    protected Thread shapeMovementThread;
    protected boolean threadRunning;
    protected boolean threadPaused;

    public ShapeMovementController(final T shape, final Shape model) {
        this.shape = shape;
        this.shapeModel = model;
        this.shapeMovementThread = null;
        this.threadRunning = true;
        threadPaused = false;
    }

    public void setMovementThread(final Thread shapeMovementThread) {
        this.shapeMovementThread = shapeMovementThread;
    }

    public void stopMoving() {
//        logger.info("A Shape Should Stop Moving");
        threadRunning = false;
        shapeMovementThread.interrupt();
    }

    public void pauseMovement() {
//		logger.info("A Shape Should Pause Movement");
        threadPaused = true;
        shapeMovementThread.interrupt();
    }

    public void resumeMovement() {
//		logger.info("A Shape Should Resume Movement");
        threadPaused = false;
        shapeMovementThread.interrupt();
    }
}
