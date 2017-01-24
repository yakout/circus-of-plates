package models.shapes.util;

import models.Platform;
import models.shapes.Shape;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShapePlatformPair that = (ShapePlatformPair) o;
        return Objects.equals(platform, that.platform) &&
                Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, shape);
    }
}
