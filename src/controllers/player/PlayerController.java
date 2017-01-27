package controllers.player;


import controllers.main.GameController;
import controllers.shape.ShapeBuilder;
import controllers.shape.ShapeController;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import models.GameRules;
import models.players.Player;
import models.players.Stick;
import models.shapes.Shape;
import models.states.ShapeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

/**
 * Created by Ahmed Yakout on 1/23/17.
 */
public class PlayerController {
    private static final double STICK_BASE_RATIO = 0.275;
    private static final double STACK_Y_RATIO = 0.05;
    private static Logger logger = LogManager.getLogger(PlayersController
            .class);
    private String name;
    private Node leftStick;
    private Node rightStick;
    private Node playerPane;
    private Node clown;
    private Player playerModel;
    private Stack<ShapeController<? extends Node>> leftStack;
    private Stack<ShapeController<? extends Node>> rightStack;
    private double leftStickYConstant;
    private double rightStickYConstant;

    PlayerController(String name, Node playerPane, Player playerModel) {
        this.name = name;
        this.playerModel = playerModel;
        this.playerPane = playerPane;
        this.leftStick = getNodeWithId("leftstick");
        this.rightStick = getNodeWithId("rightstick");
        this.clown = getNodeWithId("clown");
        leftStack = new Stack<>();
        rightStack = new Stack<>();
        playerModel.registerObserver(GameController.getInstance());
        leftStickYConstant = STACK_Y_RATIO * leftStick.getLayoutBounds()
                .getHeight();
        rightStickYConstant = STACK_Y_RATIO * leftStick.getLayoutBounds()
                .getHeight();
    }

    /**
     * Gets the node with the given id.
     * @param id the ID of the required node.
     * @return {@link Node} the node with given id.
     */
    public Node getNodeWithId(String id) {
        return playerPane.getScene().lookup("#" + id);
    }

    public Node getClown() {
        return clown;
    }

    public Node getLeftStick() {
        return leftStick;
    }

    public String getName() {
        return name;
    }

    public Node getRightStick() {
        return rightStick;
    }

    public Node getPlayerView() {
        return playerPane;
    }

    public Player getPlayerModel() {
        return playerModel;
    }

    /**
     * Checks whether the shape intersects with the left stack or not.
     * @param shapeController {@link ShapeController} the controller of the
     *                        current shape.
     * @return whether it intersects or not.
     */
    public synchronized boolean intersectsLeftStick(ShapeController<? extends
            Node> shapeController) {
        if (playerModel.isLeftStackFull()) {
            return false;
        }
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        double leftStickIntersectionMinY = calculateLeftStackY();
//        double leftStickIntersectionMaxY = leftStickIntersectionMinY +
//                STACK_Y_RATIO * calculateLeftStackHeight();
        double leftStickIntersectionMaxY = leftStickIntersectionMinY +
                leftStickYConstant;
        double leftStickMinX = playerPane.getLayoutX() + leftStick.getLayoutX();
        double leftStickMaxX = playerPane.getLayoutX() + leftStick.getLayoutX
                () + leftStick
                .getLayoutBounds().getWidth() * STICK_BASE_RATIO;
        if (intersects(shapeModel, leftStickMinX,
                leftStickMaxX, leftStickIntersectionMinY,
                leftStickIntersectionMaxY)) {
            bindLeftStick(shapeController);
            leftStack.push(shapeController);
            playerModel.pushPlateLeft(shapeModel);
            GameController.getInstance().getCurrentGame().getShapeControllers
                    ().remove(shapeController);
            if (calculateLeftStackY() < GameController.getInstance()
                    .getCurrentGame().getCurrentLevel().getHighestPlatformY()) {
                playerModel.setLeftStackFull(true);
                if (playerModel.isRightStackFull()) {
                    GameController.getInstance().playerLost(playerModel
                            .getName());
                }
            }
            logger.debug("Shape intersects the stick.");
            return true;
        }
        return false;
    }

    /**
     * Checks whether the shape intersects with the right stack or not.
     * @param shapeController {@link ShapeController} the controller of the
     *                        current shape.
     * @return whether it intersects or not.
     */
    public synchronized boolean intersectsRightStick(ShapeController<?
            extends Node> shapeController) {
        if (playerModel.isRightStackFull()) {
            return false;
        }
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        //System.out.println(rightStick.getBoundsInParent().getMinX());
        double rightStickIntersectionMinY = calculateRightStackY();
//        double rightStickIntersectionMaxY = rightStickIntersectionMinY +
//                STACK_Y_RATIO * calculateRightStackHeight();
        double rightStickIntersectionMaxY = rightStickIntersectionMinY +
                rightStickYConstant;
        double rightStickMaxX = playerPane.getLayoutX() + rightStick
                .getLayoutX() + rightStick.getLayoutBounds().getWidth();
        double rightStickMinX = rightStickMaxX - STICK_BASE_RATIO * rightStick
                .getLayoutBounds().getWidth();
        if (intersects(shapeModel, rightStickMinX,
                rightStickMaxX, rightStickIntersectionMinY,
                rightStickIntersectionMaxY)) {
            bindRightStick(shapeController);
            rightStack.push(shapeController);
            playerModel.pushPlateRight(shapeModel);
            GameController.getInstance().getCurrentGame().getShapeControllers
                    ().remove(shapeController);
            if (calculateRightStackY() < GameController.getInstance()
                    .getCurrentGame().getCurrentLevel().getHighestPlatformY()) {
                playerModel.setRightStackFull(true);
                if (playerModel.isLeftStackFull()) {
                    GameController.getInstance().playerLost(playerModel
                            .getName());
                }
            }
            logger.debug("Shape intersects the stick.");
            return true;
        }
        return false;
    }

    private synchronized boolean intersects(Shape shapeModel, double
            stickMinX, double stickMaxX, double stickMinY, double stickMaxY) {
        double shapeMinY = shapeModel.getPosition().getY();
        double shapeMaxY = shapeModel.getPosition().getY() + shapeModel
                .getHeight().doubleValue();
        double shapeMinX = shapeModel.getPosition().getX();
        double shapeMaxX = shapeModel.getPosition().getX() + shapeModel
                .getWidth().doubleValue();
        Bounds shapeBounds = new BoundingBox(shapeMinX, shapeMinY, shapeMaxX
                - shapeMinX, shapeMaxY - shapeMinY);
        Bounds stickBounds = new BoundingBox(stickMinX, stickMinY, stickMaxX
                - stickMinX, stickMaxY - stickMinY);
        if (shapeMinX >= shapeMaxX || stickMinX >= stickMaxX || stickMinY >=
                stickMaxY || shapeMinY >= shapeMaxY) {
            return false;
        }
        if (shapeBounds.intersects(stickBounds)) {
            logger.info("Shape Bounds: " + shapeBounds);
            logger.info("Stick Bounds: " + stickBounds);
        }
        return stickBounds.intersects(shapeBounds);
    }

    /**
     * Binds the Shape to left stick.
     * @param shapeController {@link ShapeController} the controller of the
     *                        shape.
     */
    public synchronized void bindLeftStick(ShapeController<? extends Node>
                                                   shapeController) {
        Node shape = shapeController.getShape();
        double relativeLeftStickCenter = leftStick.getLayoutX()
                + STICK_BASE_RATIO * leftStick.getLayoutBounds().getWidth();
        double leftStickCenter = playerPane.getLayoutX() +
                relativeLeftStickCenter;
        shape.setLayoutX(leftStickCenter - shape.getLayoutBounds().getWidth()
                / 2.0);
        double leftStickY = calculateLeftStackY();
        shape.setLayoutY(leftStickY - shape.getLayoutBounds().getHeight());
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shape.translateXProperty().bind(playerPane.layoutXProperty().add(
                relativeLeftStickCenter - shape.getLayoutBounds().getWidth()
                        / 2.0 - shape.getLayoutX()));
        logger.debug("Shape is bound to the leftStick.");
    }

    /**
     * Binds the Shape to right stick.
     * @param shapeController {@link ShapeController} the controller of the
     *                        shape.
     */
    public synchronized void bindRightStick(ShapeController<? extends Node>
                                                    shapeController) {
        Node shape = shapeController.getShape();
        double relativeRightCenter = rightStick
                .getLayoutX() + (1 - STICK_BASE_RATIO) * rightStick
                .getLayoutBounds().getWidth();
        System.out.println(rightStick.getLayoutX());
        double rightStickCenter = playerPane.getLayoutX() + relativeRightCenter;
        shape.setLayoutX(rightStickCenter - shape.getLayoutBounds().getWidth()
                / 2.0);
        double rightStickY = calculateRightStackY();
        shape.setLayoutY(rightStickY - shape.getLayoutBounds().getHeight());
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shape.translateXProperty().bind(playerPane.layoutXProperty().add(
                relativeRightCenter - shape.getLayoutBounds().getWidth()
                        / 2.0 - shape.getLayoutX()));
        logger.debug("Shape is bound to the right stick.");
    }

    /**
     * Removes the shape from the given stick.
     * @param stick {@link Stick} the stick of required plates to move off.
     */
    public synchronized void removeShape(Stick stick) {
        for (int i = 0; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
            ShapeController<? extends Node> shapeController =
                    null;
            switch (stick) {
                case RIGHT:
                    shapeController = rightStack.pop();
                    break;
                case LEFT:
                    shapeController = leftStack.pop();
                    break;
                default:
                    break;
            }
            if (shapeController == null) {
                return;
            }
            shapeController.getShape().translateXProperty().unbind();
            shapeController.shapeShouldEnterThePool();
        }
    }

    private synchronized double calculateLeftStackY() {
        double y = playerPane.getLayoutY() + leftStick
                .getLayoutY();
        for (Shape shape : playerModel.getLeftStack()) {
            y -= shape.getHeight().doubleValue();
        }
        return y;
    }

    private synchronized double calculateRightStackY() {
        double y = playerPane.getLayoutY() + rightStick
                .getLayoutY();
        for (Shape shape : playerModel.getRightStack()) {
            y -= shape.getHeight().doubleValue();
        }
        return y;
    }

    private synchronized double calculateLeftStackHeight() {
        double height = leftStick.getLayoutBounds().getHeight();
        for (Shape shape : playerModel.getLeftStack()) {
            height += shape.getHeight().doubleValue();
        }
        return height;
    }

    private synchronized double calculateRightStackHeight() {
        double height = rightStick.getLayoutBounds().getHeight();
        for (Shape shape : playerModel.getRightStack()) {
            height += shape.getHeight().doubleValue();
        }
        return height;
    }

    /**
     * Adds the shapes to the to stacks and binds them.
     */
    public synchronized void addShapes() {
        Stack<Shape> leftShapes = new Stack<>();
        Stack<Shape> rightShapes = new Stack<>();
        System.out.println(playerModel.getLeftStack().size());
        while (!playerModel.getLeftStack().isEmpty()) {
            leftShapes.push(playerModel.getLeftStack().pop());
        }
        while (!playerModel.getRightStack().isEmpty()) {
            rightShapes.push(playerModel.getRightStack().pop());
        }
        while (!leftShapes.isEmpty()) {
            ShapeController<? extends Node> shapeController = new
                    ShapeController<>(ShapeBuilder
                    .getInstance().build(leftShapes.peek()), leftShapes
                    .peek(), null);
            leftShapes.pop();
            bindLeftStick(shapeController);
            leftStack.push(shapeController);
            playerModel.pushPlateLeft(shapeController.getShapeModel());
            GameController.getInstance().getCurrentGame().getShapeControllers
                    ().remove(shapeController);
            GameController.getInstance().getMainGame().getChildren().add
                    (shapeController.getShape());
            shapeController.getShape().setVisible(true);
        }
        while (!rightShapes.isEmpty()) {
            ShapeController<? extends Node> shapeController = new
                    ShapeController<>(ShapeBuilder
                    .getInstance().build(rightShapes.peek()), rightShapes
                    .peek(), null);
            rightShapes.pop();
            bindRightStick(shapeController);
            rightStack.push(shapeController);
            playerModel.pushPlateRight(shapeController.getShapeModel());
            GameController.getInstance().getCurrentGame().getShapeControllers
                    ().remove(shapeController);
            GameController.getInstance().getMainGame().getChildren().add
                    (shapeController.getShape());
            shapeController.getShape().setVisible(true);
        }
        logger.debug("Shapes are added to stacks.");
    }
}