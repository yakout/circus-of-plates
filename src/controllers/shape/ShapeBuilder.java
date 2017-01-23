package controllers.shape;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import models.Platform;
import models.shapes.Shape;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeBuilder {
    private static ShapeBuilder creatorInstance = null;

    private ShapeBuilder() {
    }

    public synchronized static ShapeBuilder getInstance() {
        if (creatorInstance == null) {
            creatorInstance = new ShapeBuilder();
        }
        return creatorInstance;
    }

    public synchronized Node createShape(final Platform platform, final Shape
            shapeModel) {
        if (shapeModel == null) {
            return null;
        }
        final ImageView shapeView = ViewConverter.convertToImageView
                (shapeModel);
        switch (platform.getOrientation()) {
            case LEFT:
                shapeView.setLayoutX(platform.getCenter().getX() - platform
                        .getWidth().doubleValue());
                break;
            case RIGHT:
                shapeView.setLayoutX(platform.getCenter().getX() + platform
                        .getWidth().doubleValue());
                break;
            default:
                break;
        }
        return shapeView;
    }
}