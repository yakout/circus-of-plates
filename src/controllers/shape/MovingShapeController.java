package controllers.shape;

import controllers.shape.util.ShapeMovingObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;

public class MovingShapeController<T extends Node> extends ShapeMovementController<T> {
	private final models.Platform platform;
	private double sign;
	private static final Long THREAD_SLEEP_TIME = 10L;
	private final ShapeMovingObserver shapeMovingObserver;
	private final Runnable shapeMover = new Runnable() {

		@Override
		public synchronized void run() {
			while (threadRunning) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						final double width = shape.getLayoutBounds().getWidth();
						if (Math.abs(shape.getParent()
								.getLayoutBounds().getWidth() / 2.0 - (shape.getLayoutX() + shape
										.getTranslateX() + shapeModel.getWidth().doubleValue()))
								< Math.abs(width / 2.0 -
										platform.getWidth().doubleValue())) {
							shapeMovingObserver.shapeShouldStartFalling();
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
			System.out.println("Thread: " + Thread.currentThread().getName() + " Stopped");
		}
	};
	public MovingShapeController(final T shape, final Shape model,
			final models.Platform platform, final ShapeMovingObserver shapeMovingObserver) {
		super(shape, model);
		this.shapeMovingObserver = shapeMovingObserver;
		this.platform = platform;
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
		shapeMovementThread = new Thread(shapeMover,
				"Horizontal Movement Thread " + shape.getId());
		shapeMovementThread.setDaemon(true);
		shapeMovementThread.start();
	}

}
