package models.levels;
import models.levels.util.LevelFactory;
import models.states.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelOne extends Level {

    private final double PLATE_SPEED_RATIO = 1.0;
    private final double CLOWN_SPEED_RATIO = 1.0;
    private static final int PLATFORMS = 10;
    private static final int LEVEL = 1;
    private static Logger logger = LogManager.getLogger(LevelOne.class);
    static {
        LevelFactory.getInstance().registerLevel(LEVEL, LevelOne.class);
        logger.debug("Class " + LevelOne.class.getName() + " Initialized");
    }
    private static List<String> supportedShapes
            = new ArrayList<>();
    public LevelOne(double minX, double minY, double maxX, double maxY) {
        super(LEVEL, PLATFORMS);
        supportedColors = new ArrayList<>();
        platforms = new ArrayList<>();
        setClownSpeed(CLOWN_SPEED_RATIO);
        setPlateSpeed(PLATE_SPEED_RATIO);
        addSupportedColors();
        super.minX = minX;
        super.minY = minY;
        super.maxX = maxX;
        super.maxY = maxY;
        super.setNumberOfPlatforms(PLATFORMS);
        addPlatforms();
    }

    public static void registerShape(String key) {
        supportedShapes.add(key);
    }
    @Override
    public List<String> getSupportedShapes() {
        return supportedShapes;
    }
    @Override
    public boolean isSupportedShape(String shape) {
        for (String key : supportedShapes) {
            if (key.equals(shape)) {
                return true;
            }
        }
        return false;
    }

    protected static void setSupportedShapes(List<String> supportedShapes) {
        LevelOne.supportedShapes = supportedShapes;
    }
    private void addSupportedColors() {
        supportedColors.add(Color.GREEN);
        supportedColors.add(Color.YELLOW);
        supportedColors.add(Color.CYAN);
        supportedColors.add(Color.ORANGE);
    }
}
