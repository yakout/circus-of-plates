package controllers.shape;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

    public synchronized void createShape(final Platform platform, final Shape
            shapeModel, Pane parent) {
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
        parent.getChildren().add(shapeView);
        shapeView.setLayoutY(platform.getCenter().getY() - platform.getHeight
                ().doubleValue() / 2.0 - shapeView.getLayoutBounds().getHeight());
        //shapeModel.getWidth().bind(shapeView.fitWidthProperty());
        //shapeModel.getHeight().bind(shapeView.fitHeightProperty());//can't
        // bind it, image views :(
        shapeModel.getPosition().xProperty().bind(shapeView.translateXProperty()
        .add(shapeView.getLayoutX()));
        shapeModel.getPosition().yProperty().bind(shapeView.translateYProperty()
                .add(shapeView.getLayoutY()));
        ShapeController<ImageView> shapeController = new ShapeController<>
                (shapeView, shapeModel, platform);
        shapeController.startMoving();
    }
}