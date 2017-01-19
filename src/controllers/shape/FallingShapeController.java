package controllers.shape;

import controllers.shape.util.ShapeFallingObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallingShapeController<T extends Node> extends ShapeMovementController<T> {
	private static Logger logger = LogManager.getLogger
			(FallingShapeController.class);
	private static final Long THREAD_SLEEP_TIME = 10L;
	private final ShapeFallingObserver shapeFallingObserver;
	private final Runnable shapeMover = new Runnable() {

		@Override
		public synchronized void run() {
			while (threadRunning) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (shape.getLayoutY()
								+ shape.getTranslateY()
								+ shape.getLayoutBounds().getHeight()
								>= shape.getParent()
								.getLayoutBounds().getHeight()) {
							logger.info("A Shape Hit the Ground");
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
					logger.debug("Thread (" + Thread.currentThread()
					.getName() + ") Interrupted");
					break;
				}
			}
			logger.debug("Thread: " + Thread.currentThread().getName() + " "
					+ "Stopped");
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
		logger.debug("A Shape Started Falling");
	}
}
