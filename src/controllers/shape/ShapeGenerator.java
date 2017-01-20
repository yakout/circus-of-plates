package controllers.shape;

import javafx.application.Platform;
import javafx.scene.Node;
import models.ShapePool;
import models.levels.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class ShapeGenerator<T extends Node> {

    private final long THREAD_SLEEP_TIME = 2000;
    private Level level;
    private final Thread shapeGeneratorThread;
    private boolean generationThreadRunning;
    private boolean generationThreadPaused;
    private static Logger logger = LogManager.getLogger(ShapeGenerator.class);
    private final Runnable shapeGenerator = new Runnable() {
        @Override
        public synchronized void run() {
            while(generationThreadRunning) {
                while (generationThreadPaused) {
                    try {
                        logger.debug("Generation Thread Paused")
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
                            ShapeBuilder.getInstance().createShape(platform,
                                    ShapePool.getShape(level));
                        }
                    }
                });
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (final InterruptedException e) {
                    System.out.println("Plate-generator Thread has been interrupted");
                    if (generationThreadRunning) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }
    };

    public ShapeGenerator(Level level) {
        this.level = level;
        shapeGeneratorThread = new Thread(shapeGenerator);
        generationThreadRunning = true;
        generationThreadPaused = false;
        shapeGeneratorThread.setDaemon(true);
        shapeGeneratorThread.start();
        logger.debug("Shape Generator Created");
        logger.debug("Shape Generation Thread Started Running")
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
        generationThreadRunning = false;
        shapeGeneratorThread.interrupt();
    }
}
