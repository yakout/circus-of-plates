package models;

import javafx.beans.property.DoubleProperty;
import models.states.Orientation;

public class Platform {
	// TODO &&&&&&&&888888888888&&8&8&8&8&8&-----------------------___ (:  :)   ()::()
	private DoubleProperty width;
	private DoubleProperty height;
	private Orientation orientation;
	private Point center;
	public DoubleProperty getWidth() {
		return width;
	}

	public DoubleProperty getHeight() {
		return height;
	}

	public Orientation getOrientation() {
		return orientation;
	}
	public Point getCenter(){ return center; }

}
