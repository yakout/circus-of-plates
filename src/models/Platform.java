package models;

import javafx.beans.property.DoubleProperty;
import models.states.Orientation;

public class Platform {
	// TODO &&&&&&&&888888888888&&8&8&8&8&8&-----------------------___ (:  :)   ()::()
	private Point center;
	private DoubleProperty width;
	private DoubleProperty height;
	private Orientation orientation;

	public Platform(Point center, Orientation orientation) {
		this.orientation = orientation;
		this.center = center;
	}

	public void setWidth(DoubleProperty width) {
		this.width = width;
	}

	public void setHeight(DoubleProperty height) {
		this.height = height;
	}

	public DoubleProperty getWidth() {
		return width;
	}

	public DoubleProperty getHeight() {
		return height;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public Orientation getOrientation() {
		return orientation;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	public Point getCenter(){ return center; }

}
