package models.shapes;

import javafx.beans.property.DoubleProperty;
import models.Point;
import models.states.Color;

import java.lang.Thread.State;


public abstract class Shape {

	protected Color color;
	protected Point position;
	protected double xVelocity;
	protected double yVelocity;
	protected State state;
	protected DoubleProperty width;
	protected DoubleProperty height;
	protected String key;
	public String getIdentifier() {
		return key;
	}
	protected void setKey(String key) {
		this.key = key;
	}
	public Shape() {

	}
	public State getState() {
    	return state;
	}
	public void setState(State newState) {
		state = newState;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color newColor) {
		color = newColor;
	}

	public DoubleProperty getWidth() {
		return width;
	}
	public DoubleProperty getHeight() {
		return height;
	}

	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}

	public double getHorizontalVelocity() {
		return xVelocity;
	}
	public double getVerticalVelocity() {
		return yVelocity;
	}
	public void setHorizontalVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	public void setVerticalVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public abstract String getShapeURL();

	public void translate(double x, double y) {
		position.xProperty().add(x);
		position.yProperty().add(y);
	}

}
