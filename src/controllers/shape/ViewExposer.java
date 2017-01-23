package controllers.shape;

import javafx.scene.image.ImageView;
import models.shapes.Shape;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class ViewExposer {

    public static void normalize(ImageView imgView, models.Platform platform, Shape shapeModel) {
        switch (platform.getOrientation()) {
            case LEFT:
                imgView.setLayoutX(platform.getCenter().getX() - platform
                        .getWidth().doubleValue());
                break;
            case RIGHT:
                imgView.setLayoutX(platform.getCenter().getX() + platform
                        .getWidth().doubleValue());
                break;
            default:
                break;
        }
        imgView.setLayoutY(platform.getCenter().getY() - platform.getHeight
                ().doubleValue() / 2.0 - imgView.getLayoutBounds().getHeight());
    }
}
