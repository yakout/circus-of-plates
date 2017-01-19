package models.shapes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import models.Point;
import models.levels.Level;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class PlateShape extends Shape {
    
    public static String getIdentifier() {
        return "PLATE";
    }
    
    public Thread.State getState() {
        return null;
    }

    public void setState(Thread.State newState) {

    }

    public Color getColor() {
        return null;
    }

    public void setColor(Color newColor) {

    }

    public DoubleProperty getWidth() {
        return null;
    }

    public DoubleProperty getHeight() {
        return null;
    }

    public Point getPosition() {
        return null;
    }

    public void setPosition() {

    }

    public double getHorizontalVelocity() {
        return 0;
    }

    public double getVerticalVelocity() {
        return 0;
    }

    public String getShapeURL() {
        return null;
    }

    public void translate(double x, double y) {

    }

    public void setVisible(boolean isVisible) {

    }

    public List<Level> getSupportedLevels() {
        return null;
    }

    public boolean isSupportedLevel() {
        return false;
    }
}
