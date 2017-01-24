package controllers.shape;

import controllers.main.GameController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.shapes.util.ShapePool;
import models.levels.Level;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeGenerator {

    private final long THREAD_SLEEP_TIME = 50;
    private final long THREAD_PULSE_RATE = 150;
    private Level level;
    private final Thread shapeGeneratorThread;
    private volatile boolean generationThreadIsNotStopped;
    private volatile boolean generationThreadPaused;
    private static Logger logger = LogManager.getLogger(ShapeGenerator.class);
    private Pane parent;
    private final Runnable shapeGenerator = new Runnable() {
        @Override
        public synchronized void run() {
            long counter = THREAD_PULSE_RATE;
            while (generationThreadIsNotStopped) {
                counter++;
                while (generationThreadPaused) {
                    try {
                        logger.debug("Generation Thread Paused");
                        Thread.currentThread().sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        logger.info("Generation Thread Resumed");
                        break;
                    }
                }
                if (counter >= THREAD_PULSE_RATE) {
                    counter = 0;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            List<models.Platform> platforms = level.getPlatforms();
                            for (models.Platform platform : platforms) {
                                ShapeController<? extends Node> shapeController =
                                        null;
//                                    ShapeControllerPool.getInstance()
//                                            .getShapeController(platform);
                                if (shapeController != null) {
                                    shapeController.resetShape();
                                    shapeController.startMoving();
                                } else {
                                    Shape shapeModel = ShapePool.getShape(level);
                                    GameController.getInstance()
                                            .getModelDataHolder().addShape(
                                            shapeModel);
                                    PositionInitializer.normalize(platform,
                                            shapeModel);
                                    ImageView imgView = (ImageView) ShapeBuilder
                                            .getInstance().build(shapeModel);
                                    if (imgView == null) {
                                        logger.debug("Couldn't find shapes in the pool.");
                                        continue;
                                    }
//                                    logger.debug("Shape object is built successfully.");
                                    generateShape(imgView, platform, shapeModel);
                                }
                            }
                        }
                    });
                }
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    logger.info("Plate-generator Thread has been interrupted");
                    if (generationThreadIsNotStopped) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }
    };


    public ShapeGenerator(Level level, Pane parent) {
        this.level = level;
        this.parent = parent;
        shapeGeneratorThread = new Thread(shapeGenerator);
//        setGenerationThreadIsNotStopped(true);
        generationThreadIsNotStopped = true;
//        setGenerationThreadPaused(false);
        generationThreadPaused = false;
        shapeGeneratorThread.setDaemon(true);
        shapeGeneratorThread.start();
        logger.debug("Shape Generator is Created");
        logger.debug("Shape Generation Thread Started Running");
    }

    private void generateShape(ImageView imgView, models.Platform platform,
                               Shape shapeModel) {
        imgView.setLayoutY(imgView.getLayoutY()
                - shapeModel.getHeight().doubleValue());
        shapeModel.getInitialPosition().setX(shapeModel.getPosition()
                .getX());
        shapeModel.getInitialPosition().setY(shapeModel.getPosition().getY()
                - shapeModel.getHeight().doubleValue());
        parent.getChildren().add(imgView);
        ShapeController<ImageView> shapeController = new ShapeController<>
                (imgView, shapeModel, platform);
        shapeController.startMoving();
    }

    /**
     * Pauses the thread-generator.
     */
    public synchronized void pauseGenerator() {
        logger.info("Generation Thread Pause Requested");
//        setGenerationThreadPaused(true);
        generationThreadPaused = true;
    }

    /**
     * Resumes the thread-generator.
     */
    public synchronized void resumeGenerator() {
        logger.info("Generation Thread Resume Requested");
//        setGenerationThreadPaused(false);
        generationThreadPaused = false;
        shapeGeneratorThread.interrupt();
    }

    public synchronized void stopGeneration() {
/*        setGenerationThreadIsNotStopped(false);*/
        generationThreadIsNotStopped = false;
        shapeGeneratorThread.interrupt();
    }

    private synchronized boolean isGenerationThreadIsNotStopped() {
        return generationThreadIsNotStopped;
    }

    private synchronized boolean isGenerationThreadPaused() {
        return generationThreadPaused;
    }

    private synchronized void setGenerationThreadIsNotStopped(boolean generationThreadIsNotStopped) {
        this.generationThreadIsNotStopped = generationThreadIsNotStopped;
    }

    private synchronized void setGenerationThreadPaused(boolean generationThreadPaused) {
        this.generationThreadPaused = generationThreadPaused;
    }
}
