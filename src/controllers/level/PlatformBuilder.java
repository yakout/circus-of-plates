package controllers.level;

import javafx.scene.shape.Rectangle;
import models.Platform;

/**
 * Created by Moham on 22-Jan-17.
 */
public class PlatformBuilder {
    public PlatformBuilder() {

    }
    public Rectangle build(Platform platformModel) {
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
        return platformView;
    }
}
