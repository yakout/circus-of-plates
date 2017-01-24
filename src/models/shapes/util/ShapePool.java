package models.shapes.util;

import models.levels.Level;
import models.shapes.Shape;
import models.states.Color;

import java.util.ArrayList;
import java.util.List;

public class ShapePool {

    private static List<Shape> pool;

    public static Shape getShape(Level curLevel) {
        if (pool == null) {
            pool = new ArrayList<>();
        }
        Color color = ShapeFactory.getRandomColor(curLevel);
        String shapeIdentifier = ShapeFactory.getRandomShapeIdentifier
                (curLevel);
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).getColor().equals(color) && pool.get(i)
                    .getIdentifier().equals(shapeIdentifier)) {
                return pool.remove(i);
            }
        }
        return ShapeFactory.getShape(color, shapeIdentifier);
    }

    /**
     * Get called when plates are lost.
     * @param shape
     */
    public static void destroyShape(Shape shape) {
        ShapeFactory.resetShape(shape);
        pool.add(shape);
    }

    public static void clearPool() {
        pool.clear();
    }
}