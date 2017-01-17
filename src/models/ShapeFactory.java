package models;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.paint.Color;
import models.levels.Level;
import models.shapes.Shape;

public class ShapeFactory {

    public static Color getRandomColor(Level curLevel) {
        return curLevel.getSupportedColors().get(ThreadLocalRandom.current().nextInt(0, curLevel.getSupportedColors().size()));
    }
    
    public static Class<?> getRandomShapeClass(Level curLevel) {
        return curLevel.getSupportedShapes().get(ThreadLocalRandom.current().nextInt(0, curLevel.getSupportedShapes().size()));
    }
    
    public static Shape getShape(Level curLevel) {

        Shape newShape = null;
        try {
            newShape = (Shape) getRandomShapeClass(curLevel).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        newShape.setColor(getRandomColor(curLevel));
        resetShape(newShape);
        
        return newShape;
    }
    
    public static Shape getShape(Level curLevel, Color color, Class<?> shapeClass) {
        Shape newShape = null;
        try {
            newShape = (Shape) shapeClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        newShape.setColor(color);
        resetShape(newShape);
        
        return newShape;
    }
    
    public static void resetShape(Shape shape) {
        shape.setVisible(false);
        shape.setState(null); // TODO
    }

}
