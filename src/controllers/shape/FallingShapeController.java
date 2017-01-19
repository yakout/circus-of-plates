package controllers.shape;

import controllers.shape.util.ShapeFallingObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;

public class FallingShapeController<T extends Node> extends ShapeMovementController<T> {
	private static final Long THREAD_SLEEP_TIME = 10L;
	private final ShapeFallingObserver shapeFallingObserver;
	private final Runnable shapeMover = new Runnable() {

		@Override
		public synchronized void run() {
			while (threadRunning) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (shape.getLayoutY() + shape.getTranslateY() >= shape.getParent()
								.getLayoutBounds().getHeight()) {
							shapeFallingObserver.shapeShouldStopFalling();
						} else {
							shape.setTranslateY(shape.getTranslateY() +
									shapeModel.getVerticalVelocity());
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
	public FallingShapeController(final T shape, final Shape model
			,final ShapeFallingObserver shapeFallingObserver) {
		super(shape, model);
		this.shapeFallingObserver = shapeFallingObserver;
		shapeMovementThread = new Thread(shapeMover,
				"Horizontal Movement Thread:" + shape.getId());
		shapeMovementThread.setDaemon(true);
		shapeMovementThread.start();
	}
}
