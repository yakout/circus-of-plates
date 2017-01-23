package controllers.shape;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.ShapePool;
import models.levels.Level;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.View;
import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeGenerator {

    private final long THREAD_SLEEP_TIME = 10000;
    private Level level;
    private final Thread shapeGeneratorThread;
    private boolean generationThreadIsNotStopped;
    private boolean generationThreadPaused;
    private static Logger logger = LogManager.getLogger(ShapeGenerator.class);
    private Pane parent;
    private final Runnable shapeGenerator = new Runnable() {
        @Override
        public synchronized void run() {
            while(generationThreadIsNotStopped) {
                while (generationThreadPaused) {
                    try {
                        logger.debug("Generation Thread Paused");
                        Thread.currentThread().sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        logger.info("Generation Thread Resumed");
                        break;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        List<models.Platform> platforms = level.getPlatforms();
                        for (models.Platform platform : platforms) {
                            Shape shapeModel = ShapePool.getShape(level);
                            ImageView imgView = (ImageView) ShapeBuilder.getInstance().
                                    build(shapeModel);
                            if (imgView == null) {
                                continue;
                            }
                            generateShape(imgView, platform, shapeModel);
                        }
                    }
                });
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
        generationThreadIsNotStopped = true;
        generationThreadPaused = false;
        shapeGeneratorThread.setDaemon(true);
        shapeGeneratorThread.start();
        logger.debug("Shape Generator Created");
        logger.debug("Shape Generation Thread Started Running");
    }

    private void generateShape(ImageView imgView, models.Platform platform, Shape shapeModel) {
        ViewExposer.normalize(imgView, platform, shapeModel);
        parent.getChildren().add(imgView);
        ShapeController<ImageView> shapeController = new ShapeController<>
                (imgView, shapeModel, platform);
        shapeController.startMoving();
    }
    /**
     * Pauses the thread-generator.
     */
    public void pauseGenerator() {
        logger.info("Generation Thread Pause Requested");
        generationThreadPaused = true;
    }

    /**
     * Resumes the thread-generator.
     */
    public void resumeGenerator() {
        logger.info("Generation Thread Resume Requested");
        generationThreadPaused = false;
        shapeGeneratorThread.interrupt();
    }

    public void stopGeneration() {
        generationThreadIsNotStopped = false;
        shapeGeneratorThread.interrupt();
    }
}
