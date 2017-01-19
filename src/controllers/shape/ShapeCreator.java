package controllers.shape;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.shapes.Shape;
import models.states.Orientation;
import views.test.PlateController;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeCreator {
    private static ShapeCreator creatorInstance = null;
    private ShapeCreator() {
    }

    public static ShapeCreator getInstance() {
        if (creatorInstance == null) {
            creatorInstance = new ShapeCreator();
        }
        return creatorInstance;
    }

    public synchronized void createShape(final Orientation direction, final Shape shape) {
        final Image img = new Image(shape.getShapeURL());
        final javafx.scene.image.ImageView newShape = new javafx.scene.image.ImageView();
        newShape.setImage(img);
        newShape.setFitHeight(shape.getHeight().doubleValue());
        newShape.setFitWidth(shape.getWidth().doubleValue());
        newShape.setPickOnBounds(true);
        newShape.setPreserveRatio(true);
        switch (direction) {
            case LEFT:
                newShape.setLayoutX(0);
                break;
            case RIGHT:
                newShape.setLayoutX(clown.getParent().getBoundsInParent().getWidth());
                break;
            default:
                break;
        }
        newShape.setLayoutY(rightRod.getLayoutY());
        anchorPane.getChildren().add(newShape);
        final PlateController<ImageView> plateRController
                = new PlateController<>(newShape, isLeftPlate, rightRod.getWidth());
        final Thread plateThread = new Thread(plateRController, "New Right plate");
        plateThread.setDaemon(true);
        plateThread.start();
    }
}
