package models.levels;

import models.Platform;
import models.shapes.PlateShape;
import models.states.Orientation;
import models.states.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelOne extends Level {

    private List<Platform> platforms;
    private List<String> supportedShapes;
    private List<Color> supportedColors;
    private final double PLATE_SPEED_RATIO = 1.0;
    private final double CLOWN_SPEED_RATIO = 1.0;
    private static final int PLATFORMS = 2;
    private static final int LEVEL = 1;

    public LevelOne() {
        super(LEVEL, PLATFORMS);
        supportedShapes = new ArrayList<>();
        supportedColors = new ArrayList<>();
        setClownSpeed(CLOWN_SPEED_RATIO);
        setPlateSpeed(PLATE_SPEED_RATIO);
        addSupportedColors();
        // To be checked later.........................
        supportedShapes.add(PlateShape.class.getName());
        // TODO (adding more shapes goes here).


        setSupportedColors(supportedColors);
        setSupportedShapes(supportedShapes);
    }

    @Override
    public List<Platform> getPlatforms() {
        for (int i = 0; i < noOfPlatforms; i++) {
            if (i % 2 == 0) {
                // TODO...
            }
        }

        return null;
    }

    private void addSupportedColors() {
        supportedColors.add(Color.GREEN);
        supportedColors.add(Color.YELLOW);
        supportedColors.add(Color.LIGHT_BLUE);
        supportedColors.add(Color.ORANGE);
    }
}
