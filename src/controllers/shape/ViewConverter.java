package controllers.shape;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.shapes.Shape;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ViewConverter {
    private ViewConverter() {
    }

    /**
     * convert shape requested from model to imageView to get
     * represented on view.
     * @param shapeModel shapeModel requested from model.
     * @return shape in imageView form.
     */
    public static ImageView convertToImageView(Shape shapeModel) {
        final Image img = new Image(shapeModel.getShapeURL());
        final ImageView shapeView = new ImageView();
        shapeView.setImage(img);
        shapeView.setFitHeight(shapeModel.getHeight().doubleValue());
        shapeView.setFitWidth(shapeModel.getWidth().doubleValue());
        shapeView.setPickOnBounds(true);
        shapeView.setPreserveRatio(true);
        return shapeView;
    }
}
