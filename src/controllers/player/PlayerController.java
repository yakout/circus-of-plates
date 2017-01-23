package controllers.player;


import controllers.shape.ShapeController;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import models.players.Player;
import models.shapes.Shape;
import models.states.ShapeState;

/**
 * Created by ahmedyakout on 1/23/17.
 */
public class PlayerController {
    private String name;
    private Node leftStick;
    private Node rightStick;
    private Node playerPane;
    private Node clown;
    private Player playerModel;
    private static final double STICK_BASE_RATIO = 0.55;
    PlayerController(String name, Node playerPane, models
            .players.Player playerModel) {
        this.name = name;
        this.playerModel = playerModel;
        this.playerPane = playerPane;
        this.leftStick = getNodeWithId("leftstick");
        this.rightStick = getNodeWithId("rightstick");
        this.clown = getNodeWithId("clown");
    }

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

    public models.players.Player getPlayerModel() {
        return playerModel;
    }

    public synchronized boolean intersectsLeftStick(ShapeController<? extends
            Node>
                                                            shapeController) {
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        //System.out.println(leftStick.getBoundsInParent().getMinX());
        double leftStickIntersectionMaxY = playerPane.getLayoutY() + leftStick
                .getLayoutY() + 0.1 *
                leftStick.getLayoutBounds().getHeight();
        double leftStickIntersectionMinY = playerPane.getLayoutY() + leftStick
                .getLayoutY();
        double leftStickMinX = playerPane.getLayoutX() + leftStick.getLayoutX();
        double leftStickMaxX = playerPane.getLayoutX() + leftStick.getLayoutX
                () + leftStick
                .getLayoutBounds().getWidth() * STICK_BASE_RATIO;
        if (intersects(shapeModel, leftStickMinX,
                leftStickMaxX, leftStickIntersectionMinY,
                leftStickIntersectionMaxY)) {
            playerModel.pushPlateLeft(shapeModel);
            bindLeftStick(shapeController);
            return true;
        }
        return false;
    }

    public synchronized boolean intersectsRightStick(ShapeController<?
            extends Node> shapeController) {
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        //System.out.println(rightStick.getBoundsInParent().getMinX());
        double rightStickIntersectionMaxY = playerPane.getLayoutY() +
                rightStick.getLayoutY() + 0.1 *
                        rightStick.getLayoutBounds().getHeight();
        double rightStickIntersectionMinY = playerPane.getLayoutY() + rightStick
                .getLayoutY();
        double rightStickMaxX = playerPane.getLayoutX() + rightStick
                .getLayoutX() + rightStick.getLayoutBounds().getWidth();
        double rightStickMinX = rightStickMaxX - STICK_BASE_RATIO * rightStick
                .getLayoutBounds().getWidth();
        if (intersects(shapeModel, rightStickMinX,
                rightStickMaxX, rightStickIntersectionMinY,
                rightStickIntersectionMaxY)) {
            playerModel.pushPlateRight(shapeModel);
            bindRightStick(shapeController);
            return true;
        }
        return false;
    }

    private synchronized boolean intersects(Shape shapeModel, double
            stickMinX, double
            stickMaxX, double stickMinY, double stickMaxY) {
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
            System.out.println("Shape Bounds: " + shapeBounds);
            System.out.println("Stick Bounds: " + stickBounds);
        }
        return stickBounds.intersects(shapeBounds);
    }

    public void bindLeftStick(ShapeController<? extends Node> shapeController) {
        Node shape = shapeController.getShape();
        double relativeLeftStickCenter = leftStick.getLayoutX()
                + STICK_BASE_RATIO * leftStick.getLayoutBounds()
                .getWidth() / 2.0;
        double leftStickCenter = playerPane.getLayoutX() +
                relativeLeftStickCenter;
        shape.setLayoutX(leftStickCenter - shape.getLayoutBounds().getWidth()
                / 2.0);
        double leftStickY = playerPane.getLayoutY() + leftStick
                .getLayoutY();
        shape.setLayoutY(leftStickY - shape.getLayoutBounds().getHeight());
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shape.translateXProperty().bind(playerPane.layoutXProperty().add
                (relativeLeftStickCenter - shape.getLayoutBounds().getWidth()
                / 2.0 - shape.getLayoutX()));
    }

    public void bindRightStick(ShapeController<? extends Node> shapeController) {
        Node shape = shapeController.getShape();
        double rightStickCenter = playerPane.getLayoutX() + rightStick
                .getLayoutX() + (1 - STICK_BASE_RATIO) * rightStick
                .getLayoutBounds()
                .getWidth();
        System.out.println("rsc: " + rightStickCenter);
        shape.setLayoutX(rightStickCenter - shape.getLayoutBounds().getWidth()
                / 2.0);
        double rightStickY = playerPane.getLayoutY() + rightStick
                .getLayoutY();
        System.out.println("rsy: " + rightStickY);
        shape.setLayoutY(rightStickY - shape.getLayoutBounds().getHeight());
        shape.translateXProperty().bind(playerPane.layoutXProperty().subtract
                (shape.getLayoutX()));
    }

    private double calculateLeftStackY() {
        double initialY = playerPane.getLayoutY() + rightStick
                .getLayoutY();
        for (Shape shape : playerModel.)
    }
}