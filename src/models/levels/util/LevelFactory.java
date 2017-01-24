package models.levels.util;

import models.levels.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moham on 24-Jan-17.
 */
public class LevelFactory {
    private static LevelFactory factoryInstance;
    Map<Integer, Class<? extends Level>> registeredLevels;

    private LevelFactory() {
        registeredLevels = new HashMap<>();
    }

    public static synchronized LevelFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new LevelFactory();
        }
        return factoryInstance;
    }

    public void registerLevel(int levelNumber, Class<? extends Level>
            levelClass) {
        registeredLevels.put(levelNumber, levelClass);
    }

    public Level createLevel(int levelNumber, double minX, double minY, double
            maxX, double maxY) {
        Class<? extends Level> levelClass =
                registeredLevels.get(levelNumber);
        try {
            Constructor<? extends Level> levelConstructor =
                    levelClass.getConstructor(double.class, double.class,
                            double.class, double.class);
            Level level = levelConstructor.newInstance(minX, minY, maxX, maxY);
            return level;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            return null;
        }
    }
}
