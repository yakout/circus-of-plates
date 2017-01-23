package controllers.shape;

import javafx.scene.Node;
import javafx.scene.image.Image;
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

    public synchronized Node createShape(final Shape shapeModel) {
        if (shapeModel == null) {
            return null;
        }
        final Image img = new Image(shapeModel.getShapeURL());
        final ImageView shapeView = new ImageView();
        shapeView.setImage(img);
        shapeView.setFitHeight(shapeModel.getHeight().doubleValue());
        shapeView.setFitWidth(shapeModel.getWidth().doubleValue());
        shapeModel.getPosition().xProperty().bind(shapeView.translateXProperty()
                .add(shapeView.getLayoutX()));
        shapeModel.getPosition().yProperty().bind(shapeView.translateYProperty()
                .add(shapeView.getLayoutY()));
        shapeView.setPickOnBounds(true);
        shapeView.setPreserveRatio(true);
        return shapeView;
    }
}