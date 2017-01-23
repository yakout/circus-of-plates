package controllers.player;


import controllers.shape.ShapeController;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
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
    private models.players.Player playerModel;

    PlayerController(Node rightStick, Node leftStick, Node playerPane, models
            .players.Player playerModel) {
        this.playerModel = playerModel;
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.playerPane = playerPane;
    }

    public Node getLeftStick() {
        return leftStick;
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

    public synchronized boolean intersectsLeftStick(ShapeController<? extends Node>
                                               shapeController) {
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        double leftStickIntersectionMaxY = leftStick.getLayoutY() - 0.1 *
                leftStick.getLayoutBounds().getHeight();
        double leftStickIntersectionMinY = leftStick.getLayoutY();
        double leftStickMinX = leftStick.getLayoutX();
        double leftStickMaxX = leftStick.getLayoutX() + leftStick
                .getLayoutBounds().getWidth();
        if (intersects(shapeModel, leftStickMinX,
                leftStickMaxX, leftStickIntersectionMinY,
                leftStickIntersectionMaxY)) {
            playerModel.pushPlateLeft(shapeModel);
            return true;
        }
        return false;
    }

    public synchronized boolean intersectsRightStick(ShapeController<? extends Node>
                                                 shapeController) {
        Shape shapeModel = shapeController.getShapeModel();
        if (shapeModel.getState() != ShapeState.FALLING) {
            return false;
        }
        double rightStickIntersectionMaxY = rightStick.getLayoutY() - 0.1 *
                rightStick.getLayoutBounds().getHeight();
        double rightStickIntersectionMinY = rightStick.getLayoutY();
        double rightStickMinX = rightStick.getLayoutX();
        double rightStickMaxX = rightStick.getLayoutX() + rightStick
                .getLayoutBounds().getWidth();
        if (intersects(shapeModel, rightStickMinX,
                rightStickMaxX, rightStickIntersectionMinY,
                rightStickIntersectionMaxY)) {
            playerModel.pushPlateRight(shapeModel);
            return true;
        }
        return false;
    }

    private synchronized boolean intersects(Shape shapeModel, double stickMinX, double
            stickMaxX, double stickMinY, double stickMaxY) {
        double shapeMinY = shapeModel.getPosition().getY();
        double shapeMaxY = shapeModel.getPosition().getY() + shapeModel
                .getHeight().doubleValue();
        double shapeMinX = shapeModel.getPosition().getX();
        double shapeMaxX = shapeModel.getPosition().getX() + shapeModel
                .getWidth().doubleValue();
        if (shapeMinX >= shapeMaxX || stickMinX >= stickMaxX || stickMinY >=
                stickMaxY || shapeMinY >= shapeMaxY) {
            return false;
        }
        Bounds shapeBounds = new BoundingBox(shapeMinX, shapeMinY, shapeMaxX
                - shapeMinX, shapeMaxY - shapeMinY);
        Bounds stickBounds = new BoundingBox(stickMinX, stickMinY, stickMaxX
                - stickMinX, stickMaxY - stickMinY);
        return stickBounds.intersects(shapeBounds);
    }
}