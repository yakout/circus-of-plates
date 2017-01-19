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

    public static synchronized void createPlate(final Orientation direction, final Shape shape) {
        final javafx.scene.image.ImageView newPlate = new javafx.scene.image.ImageView();
        final Image img = new Image("assets/images/Plates/plate1.png");
        newPlate.setImage(img);
        newPlate.setFitHeight(32);
        newPlate.setFitWidth(61);
        newPlate.setPickOnBounds(true);
        newPlate.setPreserveRatio(true);
        switch (direction) {
            case LEFT:
                newPlate.setLayoutX(0);
                break;
            case RIGHT:
                newPlate.setLayoutX(clown.getParent().getBoundsInParent().getWidth());
                break;
            default:
                break;
        }
        newPlate.setLayoutY(rightRod.getLayoutY());
        anchorPane.getChildren().add(newPlate);
        final PlateController<ImageView> plateRController
                = new PlateController<>(newPlate, isLeftPlate, rightRod.getWidth());
        final Thread plateThread = new Thread(plateRController, "New Right plate");
        plateThread.setDaemon(true);
        plateThread.start();
    }
}
