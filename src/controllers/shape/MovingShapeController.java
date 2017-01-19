package controllers.shape;

import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;

public class MovingShapeController<T extends Node> {
	private final T shape;
	private final Shape shapeModel;
	private final models.Platform platform;
	private final ShapeController<T> parent;
	private double sign;
	private static final Long THREAD_SLEEP_TIME = 10L;
	private final Thread shapeMovementThread;
	private final Runnable shapeMover = new Runnable() {

		@Override
		public synchronized void run() {
			while (true) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						final double width = shape.getLayoutBounds().getWidth();
						if (Math.abs(shape.getParent()
								.getLayoutBounds().getWidth() / 2.0 - (shape.getLayoutX() + shape
										.getTranslateX() + shapeModel.getWidth().doubleValue()))
								< Math.abs(width / 2.0 -
										platform.getWidth().doubleValue())) {
							//TODO notify the main controller that
							// the plate should start falling
						} else {
							shape.setTranslateX(shape.getTranslateX() + sign *
									shapeModel.getHorizontalVelocity());
						}
					}
				});
				try {
					this.wait(THREAD_SLEEP_TIME);
				} catch (final InterruptedException e) {
					System.out.println("Thread (" + Thread.currentThread()
					.getName() + ") Interrupted");
					break;
				}
			}
		}
	};
	public MovingShapeController(final T shape, final Shape model,
			final models.Platform platform, final ShapeController<T> parent) {
		this.shape = shape;
		this.shapeModel = model;
		this.platform = platform;
		this.parent = parent;
		switch(platform.getOrientation()) {
		case LEFT:
			sign = 1;
			break;
		case RIGHT:
			sign = -1;
			break;
		default:
			break;
		}
		shapeMovementThread = new Thread(shapeMover);
		shapeMovementThread.setDaemon(true);
		shapeMovementThread.start();
	}
	public void stopMoving() {
		shapeMovementThread.interrupt();
	}
	/*
	 * if (shape.getLayoutY() + shape.getTranslateY() >= shape.getParent()
								.getLayoutBounds().getHeight()) {
							shape.setTranslateX(0);
							shape.setTranslateY(0);
						} else



shape.setTranslateY(shape.getTranslateY() +
									shapeModel.getVerticalVelocity() / 4);
	 */

}
