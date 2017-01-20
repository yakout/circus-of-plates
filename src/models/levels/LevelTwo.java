package models.levels;

import javafx.scene.paint.Color;
import models.Platform;
import models.shapes.PlateShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelTwo extends Level {

    private List<Platform> platforms;
    private List<String> supportedShapes;
    private List<models.states.Color> supportedColors;
    private final double PLATE_SPEED_RATIO = 1.2;
    private final double CLOWN_SPEED_RATIO = 1.2;
    private static final int PLATFORMS = 4;
    private static final int LEVEL = 2;

    public LevelTwo() {
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
        supportedColors.add(models.states.Color.GREEN);
        supportedColors.add(models.states.Color.YELLOW);
        supportedColors.add(models.states.Color.LIGHT_BLUE);
        supportedColors.add(models.states.Color.ORANGE);
    }
}
