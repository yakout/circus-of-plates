package models;

import javafx.beans.property.DoubleProperty;
import models.states.Orientation;

public class Platform {
	// TODO &&&&&&&&888888888888&&8&8&8&8&8&-----------------------___ (:  :)   ()::()
	private DoubleProperty width;
	private Orientation orientation;
	private Point center;
	public DoubleProperty getWidth() {
		return width;
	}
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * To get the place of the platform in order to generate
	 * plates on it.
	 * @return
	 */
	public Point getCenter(){ return center; }
}
