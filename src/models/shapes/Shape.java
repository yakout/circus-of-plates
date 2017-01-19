package models.shapes;

import java.lang.Thread.State; // TODO Not this state :D
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import models.Point;
import models.levels.Level;


public abstract class Shape {

    public static String getIdentifier() {
        return null;
    }

	public abstract State getState();
	public abstract void setState(State newState);

	public abstract Color getColor();
	public abstract void setColor(Color newColor);

	public abstract DoubleProperty getWidth();
	public abstract DoubleProperty getHeight();

	public abstract Point getPosition();
	public abstract void setPosition();

	public abstract double getHorizontalVelocity();
	public abstract double getVerticalVelocity();

	public abstract String getShapeURL();

	public abstract void translate(double x, double y);
	public abstract void setVisible(boolean isVisible);

	public abstract List<Level> getSupportedLevels();
	public abstract boolean isSupportedLevel();

}
