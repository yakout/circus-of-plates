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

    public synchronized void createShape(final Platform platform, final Shape shapeModel) {
        final ImageView shapeView = ViewConverter.convertToImageView(shapeModel);
        switch (platform.getOrientation()) {
            case LEFT:
                shapeView.setLayoutX(platform.getCenter().getX() - platform.getWidth().doubleValue());
                break;
            case RIGHT:
                shapeView.setLayoutX(platform.getCenter().getX() + platform.getWidth().doubleValue());
                break;
            default:
                break;
        }
        shapeView.setLayoutY(platform.getCenter().getY() - platform.getHeight().doubleValue() / 2.0);
        ShapeController<ImageView> shapeController = new ShapeController<>(shapeView, shapeModel, platform);
        shapeController.startMoving();
    }
}