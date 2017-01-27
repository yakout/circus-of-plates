package models.levels.util;

import models.levels.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Moham on 24-Jan-17.
 */
public class LevelFactory {
    private static LevelFactory factoryInstance;
    private Map<Integer, Class<? extends Level>> registeredLevels;
    private static Logger logger = LogManager.getLogger(LevelFactory.class);
    private LevelFactory() {
        registeredLevels = new LinkedHashMap<>();
    }

    public static synchronized LevelFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new LevelFactory();
        }
        return factoryInstance;
    }

    public void registerLevel(int levelNumber, Class<? extends Level>
            levelClass) {
        logger.info("Level " + levelNumber + " Registered in the Factory");
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
            logger.debug("Requested Creating Level " + levelNumber);
            Level level = levelConstructor.newInstance(minX, minY, maxX, maxY);
            if (level != null) {
                logger.debug("Successfully Created Level " + levelNumber);
            } else {
                logger.error("Failed to Create Level " + levelNumber);
            }
            return level;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            logger.error("Failed to Create Level " + levelNumber, e);
            return null;
        }
    }

    public Collection<Integer> getRegisteredLevels() {
        return registeredLevels.keySet();
    }
}
