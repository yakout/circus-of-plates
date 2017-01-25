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
public class LevelTwo extends Level {

    private final double PLATE_SPEED_RATIO = 1.2;
    private final double CLOWN_SPEED_RATIO = 1.2;
    private static final int PLATFORMS = 4;
    private static final int LEVEL = 2;
    private static List<String> supportedShapes
            = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(LevelTwo.class);
    static {
        LevelFactory.getInstance().registerLevel(LEVEL, LevelTwo.class);
        logger.debug("Class " + LevelTwo.class.getName() + " Initialized");
    }
    public LevelTwo(double minX, double minY, double maxX, double maxY) {
        super(LEVEL, PLATFORMS);
        supportedColors = new ArrayList<>();
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
        LevelTwo.supportedShapes = supportedShapes;
    }

    private void addSupportedColors() {
        supportedColors.add(Color.CYAN);
        supportedColors.add(Color.YELLOW);
        supportedColors.add(Color.RED);
        supportedColors.add(Color.GREEN);
        supportedColors.add(Color.BLUE);
        supportedColors.add(Color.DARKRED);
        supportedColors.add(Color.GOLD);
        supportedColors.add(Color.ORANGE);
        supportedColors.add(Color.PINK);
        supportedColors.add(Color.PURPLE);
    }
}
