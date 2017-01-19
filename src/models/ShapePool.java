package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import models.levels.Level;
import models.shapes.Shape;

public class ShapePool {
    
    private static List<Shape> pool;
    public static Shape getShape(Level curLevel) {
        if (pool == null) {
            pool = new ArrayList<>();
        }
        Color color = ShapeFactory.getRandomColor(curLevel);
        Class<?> shapeClass = ShapeFactory.getRandomShapeClass(curLevel);
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i).getColor().equals(color) && pool.get(i).getClass().equals(shapeClass)){
                return pool.remove(i);
            }
        }
        return ShapeFactory.getShape(curLevel, color, shapeClass);
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