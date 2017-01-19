package controllers.shape;

import javafx.scene.Node;
import models.shapes.Shape;

public class ShapeController<T extends Node> {
	private final T shape;
	private final Shape shapeModel;
	private final models.Platform platform;
	public ShapeController(final T shape, final Shape model,
			final models.Platform platform) {
		this.shape = shape;
		this.shapeModel = model;
		this.platform = platform;
	}

}
