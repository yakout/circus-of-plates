package models.shapes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import models.Point;
import models.levels.Level;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class PlateShape implements Shape {
    @Override
    public Thread.State getState() {
        return null;
    }
x
    @Override
    public void setState(Thread.State newState) {

    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color newColor) {

    }

    @Override
    public DoubleProperty getWidth() {
        return null;
    }

    @Override
    public DoubleProperty getHeight() {
        return null;
    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public void setPosition() {

    }

    @Override
    public double getHorizontalVelocity() {
        return 0;
    }

    @Override
    public double getVerticalVelocity() {
        return 0;
    }

    @Override
    public String getShapeURL() {
        return null;
    }

    @Override
    public void translate(double x, double y) {

    }

    @Override
    public void setVisible(boolean isVisible) {

    }

    @Override
    public List<Level> getSupportedLevels() {
        return null;
    }

    @Override
    public boolean isSupportedLevel() {
        return false;
    }
}
