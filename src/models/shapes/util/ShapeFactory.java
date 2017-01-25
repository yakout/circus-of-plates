package models.shapes.util;

import models.levels.Level;
import models.shapes.Shape;
import models.states.Color;
import models.states.ShapeState;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ShapeFactory {

    private static Map<String, Class<? extends Shape>> registeredShapes;

    public static void registerShape(String key, Class<? extends Shape>
            shapeClass) {
        if (registeredShapes == null) {
            registeredShapes = new HashMap<>();
        }
        registeredShapes.put(key, shapeClass);
    }

    public static Color getRandomColor(Level curLevel) {
        return curLevel.getSupportedColors()
                .get(ThreadLocalRandom.current().nextInt(0, curLevel
                        .getSupportedColors().size()));
    }

    public static String getRandomShapeIdentifier(Level curLevel) {
        return curLevel.getSupportedShapes()
                .get(ThreadLocalRandom.current().nextInt(0, curLevel
                        .getSupportedShapes().size()));
    }

    public static Shape getShape(Level curLevel) {

        Shape newShape = null;
        try {
            newShape = (Shape) registeredShapes.get(getRandomShapeIdentifier
                    (curLevel)).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //newShape.setColor(getRandomColor(curLevel));
        resetShape(newShape);

        return newShape;
    }

    public static Shape getShape(Color color, String shapeIdentifier) {
        Shape newShape = null;
        try {
            newShape = registeredShapes.get(shapeIdentifier)
                    .getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        newShape.setColor(color);
        resetShape(newShape);

        return newShape;
    }

    public static void resetShape(Shape shape) {
        shape.setState(ShapeState.MOVING_HORIZONTALLY); // TODO
        shape.getPosition().xProperty().unbind();
        shape.getPosition().yProperty().unbind();
        shape.getInitialPosition().xProperty().unbind();
        shape.getInitialPosition().yProperty().unbind();
    }

}
