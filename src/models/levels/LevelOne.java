package models.levels;

import models.Platform;
import models.Point;
import models.shapes.PlateShape;
import models.states.Orientation;
import models.states.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelOne extends Level {

    private final double PLATE_SPEED_RATIO = 1.0;
    private final double CLOWN_SPEED_RATIO = 1.0;
    private static final int PLATFORMS = 2;
    private static final int LEVEL = 1;
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
        supportedColors.add(Color.LIGHT_BLUE);
        supportedColors.add(Color.ORANGE);
    }
}
