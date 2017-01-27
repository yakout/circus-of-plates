package controllers.shape;

import controllers.main.GameController;
import controllers.shape.util.OnTheGroundShapeObserver;
import controllers.shape.util.ShapeFallingObserver;
import controllers.shape.util.ShapeMovingObserver;
import javafx.scene.Node;
import models.Platform;
import models.shapes.Shape;
import models.states.ShapeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShapeController<T extends Node> implements ShapeFallingObserver,
        ShapeMovingObserver, OnTheGroundShapeObserver {
    private static Logger logger = LogManager.getLogger(ShapeController.class);
    private final T shape;
    private final Shape shapeModel;
    private final Platform platform;
    private controllers.shape.util.ShapeState currentState;

    /**
     * Default constructor.
     * @param shape Shape view.
     * @param model Shape model.
     * @param platform {@link Platform} Platform that this will positioned to.
     */
    public ShapeController(final T shape, final Shape model,
                           final models.Platform platform) {
        this.shape = shape;
        this.shapeModel = model;
        this.platform = platform;
        currentState = null;
        GameController.getInstance().getCurrentGame().addShapeController(this);
//		logger.info("Shape Controller Created");
    }

    /**
     * Starts moving the shape.
     */
    public void startMoving() {
        //logger.debug("Shape " + " Movement Requested");
        switch (shapeModel.getState()) {
            case MOVING_HORIZONTALLY:
                currentState
                        = new MovingShapeStateController<>(
                        shape, shapeModel,
                        platform, this);
                break;
            case FALLING:
                currentState
                        = new FallingShapeStateController<>(shape,
                        shapeModel, this);
                break;
            default:
                break;
        }
    }

    /**
     * The shape should start to fall out of the platform.
     */
    @Override
    public void shapeShouldStartFalling() {
        //logger.debug("A Shape Should Start Falling");
        if (currentState == null) {
            return;
        }
        currentState.nextState();
        shapeModel.setState(ShapeState.FALLING);
        currentState
                = new FallingShapeStateController<>(shape, shapeModel, this);
    }

    /**
     * The shape should stop falling as reaches the ground.
     */
    @Override
    public void shapeShouldStopFalling() {
        if (currentState == null) {
            return;
        }
        currentState.nextState();
        shapeModel.setState(ShapeState.ON_THE_GROUND);
        currentState = new OnTheGroundShapeStateController<>(this);
    }

    /**
     * Checks for intersection.
     */
    @Override
    public void checkIntersection() {
        GameController.getInstance().checkIntersection(this);
    }

    /**
     * Shape should be on the stack.
     */
    public void shapeFellOnTheStack() {
        if (currentState == null) {
            return;
        }
        currentState.nextState();
        shapeModel.setState(ShapeState.ON_THE_STACK);
    }

    /**
     * Sets the state of the game to be paused.
     */
    public void gamePaused() {
        if (currentState == null) {
            return;
        }
        currentState.pauseCurrentState();
    }

    /**
     * Sets the state of the game to be resumed.
     */
    public void gameResumed() {
        if (currentState == null) {
            return;
        }
        currentState.resumeCurrentState();
    }

    /**
     * Gets the shape model of the current shape.
     * @return {@link Shape} Shape model.
     */
    public Shape getShapeModel() {
        return shapeModel;
    }

    /**
     * Gets the shape controller.
     * @return
     */
    public T getShape() {
        return shape;
    }

    /**
     * Gets the platform of this shape.
     * @return
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * This shape should enter the pool as it is removed from the stack.
     */
    @Override
    public void shapeShouldEnterThePool() {
        shape.setVisible(false);
        shapeModel.setState(ShapeState.INACTIVE);
        GameController.getInstance().getCurrentGame().removeShapeController(this);
    }

    /**
     * Resets the shape model to its default values as to be generated from
     * the begining.
     */
    public void resetShape() {
        shapeModel.setState(ShapeState.MOVING_HORIZONTALLY);
        shape.setLayoutX(shapeModel.getInitialPosition().getX());
        shape.setLayoutY(shapeModel.getInitialPosition().getY());
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shapeModel.getPosition().xProperty().bind(shape.translateXProperty()
                .add(shape.getLayoutX()));
        shapeModel.getPosition().yProperty().bind(shape.translateYProperty()
                .add(shape.getLayoutY()));
        shape.setVisible(true);
    }

    /**
     * Stops the the current shape.g
     */
    public void stop() {
        if (currentState != null) {
            currentState.nextState();
        }
    }
}