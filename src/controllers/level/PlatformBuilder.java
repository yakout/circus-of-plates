package controllers.level;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Moham on 22-Jan-17.
 */
public class PlatformBuilder {
    private static Logger logger = LogManager.getLogger(PlatformBuilder.class);
    
    /**
     * Converts the upcoming platform model to an image view.
     * @param platformModel the platform model.
     * @return {@link Rectangle} rectangle representation for platform in view.
     */
    public Rectangle build(Platform platformModel) {
        logger.info("Platform Creation Requested");
        Rectangle platformView = new Rectangle();
        platformView.setHeight(platformModel.getHeight().doubleValue());
        platformModel.getHeight().bind(platformView.heightProperty());
        platformView.setWidth(platformModel.getWidth().doubleValue());
        platformModel.getWidth().bind(platformView.widthProperty());
        platformView.setLayoutX(platformModel.getCenter().getX() -
                platformModel.getWidth().doubleValue() / 2.0);
        platformView.setLayoutY(platformModel.getCenter().getY() -
                platformModel.getHeight().doubleValue() / 2.0);
        platformModel.getCenter().xProperty().bind(platformView
                .layoutXProperty().add(platformView.getWidth() / 2.0));
        platformModel.getCenter().yProperty().bind(platformView
                .layoutYProperty().add(platformView.getHeight() / 2.0));
        Image image = new Image(platformModel.getUrl());
        ImagePattern imagePattern = new ImagePattern(image);
        platformView.setFill(imagePattern);
        logger.debug("Platform View Created Successfully With Center "
                + "Position: " + platformModel.getCenter() + " And Width " +
                platformModel.getWidth().doubleValue());
        return platformView;
    }
}
