package controllers.shape.util;

import controllers.shape.ShapeController;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Moham on 24-Jan-17.
 */
public class ShapeControllerPool {
    private static ShapeControllerPool instance;
    private Queue<ShapeController<? extends Node>> shapeControllerQueue;
    private static final int MAX_SIZE = 100;

    private ShapeControllerPool() {
        shapeControllerQueue = new LinkedList<>();
    }
    public boolean isEmpty() {
        return shapeControllerQueue.isEmpty();
    }

    public static synchronized ShapeControllerPool getInstance() {
        if (instance == null) {
            instance = new ShapeControllerPool();
        }
        return instance;
    }

    public synchronized ShapeController<? extends Node> getShapeController() {
        return shapeControllerQueue.poll();
    }

    public synchronized void storeShapeController(ShapeController<? extends
            Node> shapeController) {
        if (shapeControllerQueue.size() >= MAX_SIZE) {
            return;
        }
        shapeControllerQueue.add(shapeController);
    }
}
