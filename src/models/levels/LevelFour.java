package models.levels;

import models.levels.util.LevelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Khaled on 24/01/2017.
 */
public class LevelFour extends Level {
    private final double PLATE_SPEED_RATIO = 1.6;
    private final double CLOWN_SPEED_RATIO = 1.6;
    private static final int PLATFORMS = 8;
    private static final int LEVEL = 4;
    private static List<String> supportedShapes
            = new ArrayList<>();
    static {
        LevelFactory.getInstance().registerLevel(LEVEL, LevelFour.class);
    }
    public LevelFour(double minX, double minY, double maxX, double maxY) {
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
        LevelFour.supportedShapes = supportedShapes;
    }

    private void addSupportedColors() {
        supportedColors.add(models.states.Color.GREEN);
        supportedColors.add(models.states.Color.YELLOW);
        supportedColors.add(models.states.Color.CYAN);
        supportedColors.add(models.states.Color.ORANGE);
    }
}
