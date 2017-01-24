package models.shapes.util;

import models.Platform;
import models.shapes.Shape;

/**
 * Created by Moham on 25-Jan-17.
 */
public class ShapePlatformPair {
    Platform platform;
    Shape shape;
    public ShapePlatformPair() {

    }
    public ShapePlatformPair(Shape shape, Platform platform) {
        this.platform = platform;
        this.shape = shape;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
