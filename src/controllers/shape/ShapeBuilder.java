package controllers.shape;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Platform;
import models.shapes.Shape;
import views.test.PlateController;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeBuilder {
    private static ShapeBuilder creatorInstance = null;
    private ShapeBuilder() {
    }

    public static ShapeBuilder getInstance() {
        if (creatorInstance == null) {
            creatorInstance = new ShapeBuilder();
        }
        return creatorInstance;
    }

    public synchronized void createShape(final Platform platform, final Shape shape) {
        final Image img = new Image(shape.getShapeURL());
        final javafx.scene.image.ImageView newShape = new javafx.scene.image.ImageView();
        newShape.setImage(img);
        newShape.setFitHeight(shape.getHeight().doubleValue());
        newShape.setFitWidth(shape.getWidth().doubleValue());
        newShape.setPickOnBounds(true);
        newShape.setPreserveRatio(true);
        switch (platform.getOrientation()) {
            case LEFT:
                newShape.setLayoutX(platform.getCenter().getX() - platform.getWidth().doubleValue());
                break;
            case RIGHT:
                newShape.setLayoutX(platform.getCenter().getX() + platform.getWidth().doubleValue());
                break;
            default:
                break;
        }
        newShape.setLayoutY(platform.getCenter().getY() - platform.getHeight().doubleValue() / 2.0);
//        anchorPane.getChildren().add(newShape);
        final PlateController<ImageView> plateRController
//                = new PlateController<>(newShape, isLeftPlate, rightRod.getWidth());
        final Thread plateThread = new Thread(plateRController, "New Right plate");
        plateThread.setDaemon(true);
        plateThread.start();
    }
}
