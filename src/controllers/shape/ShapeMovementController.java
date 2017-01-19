package controllers.shape;

import javafx.scene.Node;
import models.shapes.Shape;

public abstract class ShapeMovementController<T extends Node> {
	protected T shape;
	protected final Shape shapeModel;
	protected Thread shapeMovementThread;
	protected boolean threadRunning;
	public ShapeMovementController(final T shape, final Shape model) {
		this.shape = shape;
		this.shapeModel = model;
		this.shapeMovementThread = null;
	}
	public void setMovementThread(final Thread shapeMovementThread) {
		this.shapeMovementThread = shapeMovementThread;
	}
	public void stopMoving() {
		threadRunning = false;
	}
}
