package controllers.shape.util;

import controllers.shape.ShapeController;
import javafx.scene.Node;
import models.Platform;

import java.util.*;

/**
 * Created by Moham on 24-Jan-17.
 */
public class ShapeControllerPool {
    private static ShapeControllerPool instance;
    private List<ShapeController<? extends Node>> shapeControllers;
    private static final int MAX_SIZE = 100;

    private ShapeControllerPool() {
        shapeControllers = new LinkedList<>();
    }
    public boolean isEmpty() {
        return shapeControllers.isEmpty();
    }

    public static synchronized ShapeControllerPool getInstance() {
        if (instance == null) {
            instance = new ShapeControllerPool();
        }
        return instance;
    }

    public synchronized ShapeController<? extends Node> getShapeController
            (Platform platform) {
        Iterator<ShapeController<? extends Node>> iterator = shapeControllers
                .iterator();
        System.out.println(shapeControllers.size());
        while (iterator.hasNext()) {
            ShapeController<? extends Node> shapeController = iterator.next();
            if (shapeController.getPlatform().equals(platform)) {
                iterator.remove();
                System.out.println(shapeControllers.size());
                return shapeController;
            }
        }
        return null;
    }

    public synchronized void storeShapeController(ShapeController<? extends
            Node> shapeController) {
        if (this.shapeControllers.size() >= MAX_SIZE) {
            return;
        }
        this.shapeControllers.add(shapeController);
    }
}
