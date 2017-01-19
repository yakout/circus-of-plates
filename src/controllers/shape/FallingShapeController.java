package controllers.shape;

import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;

public class FallingShapeController<T extends Node> {
	private final T shape;
	private final Shape shapeModel;
	private static final Long THREAD_SLEEP_TIME = 10L;
	private final Thread shapeFallingThread;
	private final Runnable shapeMover = new Runnable() {

		@Override
		public synchronized void run() {
			while (true) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (shape.getLayoutY() + shape.getTranslateY() >= shape.getParent()
								.getLayoutBounds().getHeight()) {
							//TODO notify the main controller that the plate has stopped moving
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
		}
	};
	public FallingShapeController(final T shape, final Shape model) {
		this.shape = shape;
		this.shapeModel = model;
		shapeFallingThread = new Thread(shapeMover);
		shapeFallingThread.setDaemon(true);
		shapeFallingThread.start();
	}
	public void stopMoving() {
		shapeFallingThread.interrupt();
	}
}
