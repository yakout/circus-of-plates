package views.test;

import javafx.application.Platform;
import javafx.scene.Node;

/**
 * Created by Moham on 26-Dec-16.
 */
public class PlateController<T extends Node> implements Runnable {
	private final T plate;
	private static final double PLATE_SPEED = 20;
	private static final double PLATE_X_SPEED = 2.3;
	private static final double PLATE_Y_SPEED = 2.0;
	private static final double STEP = 50;
	private double sign;
	private final double rodLength;
	private double offset = 61;

	private enum PlateState {
		MOVING,
		IN_STACK,
		INACTIVE
	}

	private final PlateState state;

	public PlateController(final T plate, final boolean left, final double rodLength) {
		this.plate = plate;
		this.rodLength = rodLength;
		if (left) {
			sign = 1;
			offset = 0;
		} else {
			sign = -1;
		}
		state = PlateState.MOVING;
		/*Thread movingThread = new Thread(moveTask, "plate movement thread");
        movingThread.setDaemon(true);
        movingThread.start();*/
	}

	/*public void move() {
        TranslateTransition tth = new TranslateTransition(Duration.millis
                (PLATE_SPEED * STEP * 2), plate);
        TranslateTransition ttv = new TranslateTransition(Duration.millis
                (PLATE_SPEED * STEP * 3), plate);
        tth.setByX(sign * rodLength);
        ttv.setByY(500);
        SequentialTransition sq = new SequentialTransition(plate, tth, ttv);
        sq.setCycleCount(Animation.INDEFINITE);
        sq.setDelay(Duration.ZERO);
        if (state == PlateState.MOVING) {
            sq.play();
        } else {
            sq.pause();
        }
    }*/

	@Override
	public synchronized void run() {
		final double center = plate.getParent().getLayoutBounds().getMinX() +
				plate.getParent().getLayoutBounds().getWidth() / 2.0;
		//       /2.0;
		// double boundary = center - sign * 61 / 2.0;
		while (true) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (plate.getLayoutY() + plate.getTranslateY() >= plate.getParent()
							.getLayoutBounds().getHeight()) {
						plate.setTranslateX(0);
						plate.setTranslateY(0);
					} else if (Math.abs(plate.getParent()
							.getLayoutBounds().getWidth() / 2.0 - (plate.getLayoutX() + plate
									.getTranslateX() + offset)) < Math.abs(plate.getParent()
											.getLayoutBounds().getWidth() / 2.0 -
											rodLength)) {
						/*Math.abs(350 - (plate.getLayoutX() + 61 /
                                    2.0 +
                                    plate.getTranslateX() -
                                    sign * PLATE_SPEED / 7.5)) <
                                    61*/
						plate.setTranslateY(plate.getTranslateY() +
								PLATE_SPEED / 4);
					} else {
						plate.setTranslateX(plate.getTranslateX() + sign *
								PLATE_SPEED / 7.5);
					}
				}
			});
			try {
				this.wait(10);
			} catch (final InterruptedException e) {
				System.out.println("Thread (" + Thread.currentThread()
				.getName() + ") Interrupted");
				break;
			}
		}
	}
}
