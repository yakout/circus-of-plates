package controllers.shape;

import controllers.main.GameController;
import controllers.shape.util.ShapeFallingObserver;
import controllers.shape.util.ShapeMovingObserver;
import javafx.scene.Node;
import models.Platform;
import models.shapes.Shape;
import models.states.ShapeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShapeController<T extends Node> implements ShapeFallingObserver,
		ShapeMovingObserver {
	private static Logger logger = LogManager.getLogger(ShapeController.class);
	private final T shape;
	private final Shape shapeModel;
	private final Platform platform;
	private controllers.shape.util.ShapeState currentState;
	public ShapeController(final T shape, final Shape model,
						   final models.Platform platform) {
		this.shape = shape;
		this.shapeModel = model;
		this.platform = platform;
		currentState = null;
	}

	public void startMoving() {
		logger.debug("Shape " + " Movement Requested");
		switch (shapeModel.getState()) {
			case MOVING_HORIZONTALLY:
				currentState
						= new MovingShapeController<>(
								shape, shapeModel,
						platform, this);
				break;
			case FALLING:
				currentState
						= new FallingShapeController<>(shape,
						shapeModel, this);
				break;
			default:
				break;
		}
	}

	@Override
	public void shapeShouldStartFalling() {
	    logger.debug("A Shape Should Start Falling");
		if (currentState == null) {
			return;
		}
		currentState.nextState();
		shapeModel.setState(ShapeState.FALLING);
		currentState
				= new FallingShapeController<>(shape, shapeModel, this);
	}

	@Override
	public void shapeShouldStopFalling() {
		if (currentState == null) {
			return;
		}
		currentState.nextState();
		shapeModel.setState(ShapeState.ON_THE_GROUND);

		//TODO:- Ask the main controller to add the plate to the pool.
	}

	@Override
	public void checkIntersection() {
		GameController.getInstance().checkIntersection(this);
	}

	public void shapeFellOnTheStack() {
		if (currentState == null) {
			return;
		}
		currentState.nextState();
		shapeModel.setState(ShapeState.ON_THE_STACK);
	}

	public void gamePaused() {
		currentState.pauseCurrentState();
	}

	public void gameResumed() {
		currentState.resumeCurrentState();
	}

	public Shape getShapeModel() {
		return shapeModel;
	}

	public T getShape() {
		return shape;
	}

	public Platform getPlatform() {
		return platform;
	}

}