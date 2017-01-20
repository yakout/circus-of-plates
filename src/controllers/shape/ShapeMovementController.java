package controllers.shape;

import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ShapeMovementController<T extends Node> {
	protected T shape;
	protected final Shape shapeModel;
	protected Thread shapeMovementThread;
	protected boolean threadRunning;
	private static Logger logger = LogManager.getLogger(ShapeMovementController.class);
	public ShapeMovementController(final T shape, final Shape model) {
		this.shape = shape;
		this.shapeModel = model;
		this.shapeMovementThread = null;
		this.threadRunning = true;
	}
	public void setMovementThread(final Thread shapeMovementThread) {
		this.shapeMovementThread = shapeMovementThread;
	}
	public void stopMoving() {
        logger.info("A Shape Should Stop Moving");
		threadRunning = false;
		shapeMovementThread.interrupt();
	}
}
