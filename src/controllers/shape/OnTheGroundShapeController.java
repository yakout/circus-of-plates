package controllers.shape;

import controllers.shape.util.OnTheGroundShapeObserver;
import controllers.shape.util.ShapeState;
import javafx.application.Platform;
import javafx.scene.Node;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Moham on 24-Jan-17.
 */
public class OnTheGroundShapeController<T extends Node> implements ShapeState {
    private static Logger logger = LogManager.getLogger
            (OnTheGroundShapeController.class);
    private static final int THREAD_SLEEP_TIME = 5000;
    private OnTheGroundShapeObserver observer;

    Runnable onTheGroundIdleRunnable = new Runnable() {
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(THREAD_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        logger.info("On The Ground Sleep Thread is "
                                + "Interrupted");
                    }
                    observer.shapeShouldEnterThePool();
                }
            });
        }
    };

    public OnTheGroundShapeController(OnTheGroundShapeObserver observer) {
        this.observer = observer;
        Thread idleThread = new Thread(onTheGroundIdleRunnable);
        idleThread.setDaemon(true);
        idleThread.start();
    }

    @Override
    public void nextState() {

    }

    @Override
    public boolean hasNextState() {
        return false;
    }

    @Override
    public void pauseCurrentState() {
        return;
    }

    @Override
    public void resumeCurrentState() {
        return;
    }
}
