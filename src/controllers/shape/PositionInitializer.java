package controllers.shape;

import javafx.scene.image.ImageView;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class PositionInitializer {

    private static Logger logger = LogManager.getLogger();
    public static void normalize(models.Platform platform, Shape shapeModel) {
        switch (platform.getOrientation()) {
            case LEFT:
                shapeModel.getPosition().setX(platform.getCenter().getX() -
                        platform.getWidth().doubleValue() / 2.0);
                break;
            case RIGHT:
                shapeModel.getPosition().setX(platform.getCenter().getX() +
                        platform.getWidth().doubleValue() / 2.0);
                break;
            default:
                break;
        }
        shapeModel.getPosition().setY(platform.getCenter().getY() - platform
                .getHeight
                ().doubleValue() / 2.0);
        shapeModel.getInitialPosition().setX(shapeModel.getPosition().getX());
        shapeModel.getInitialPosition().setY(shapeModel.getPosition().getY());
//        logger.info("Shape object is positioned on the platform.");
    }
}
