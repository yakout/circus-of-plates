package controllers.shape;

import controllers.shape.util.ShapeFallingObserver;
import controllers.shape.util.ShapeMovingObserver;
import javafx.scene.Node;
import models.Platform;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShapeController<T extends Node> implements ShapeFallingObserver,
		ShapeMovingObserver {
	static Logger logger = LogManager.getLogger(ShapeController.class);
	private final T shape;
	private final Shape shapeModel;
	private final Platform platform;
	private ShapeMovementController<T> currentController;
	public ShapeController(final T shape, final Shape model,
						   final models.Platform platform) {
		this.shape = shape;
		this.shapeModel = model;
		this.platform = platform;
		currentController = null;
	}

	public void startMoving() {
		logger.debug("Shape " + shape.getId() + " Movement Requested");
		currentController
		= new MovingShapeController<>(shape, shapeModel, platform, this);
	}

	@Override
	public void shapeShouldStartFalling() {
	    logger.debug("A Shape Should Start Falling");
		if (currentController == null) {
			return;
		}
		currentController.stopMoving();
		currentController
				= new FallingShapeController<>(shape, shapeModel, this);
	}

	@Override
	public void shapeShouldStopFalling() {
		if (currentController == null) {
			return;
		}
		currentController.stopMoving();
		//TODO:- Ask the main controller to add the plate to the pool.
	}

	public void shapeFellOnTheStack() {
		if (currentController == null) {
			return;
		}
		currentController.stopMoving();
	}
}