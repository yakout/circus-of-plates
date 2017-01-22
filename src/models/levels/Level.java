package models.levels;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import models.Point;
import models.states.Color;
import models.Platform;
import models.states.Orientation;

public abstract class Level {

    private final String IMG_EXTENSION = ".png";
    private final String DIRECTORY = "assets/images/backgrounds/background_";
    private double plateSpeedRatio = 1.0;
    private double clownSpeedRatio = 1.0;
    protected int noOfPlatforms;
    protected Image background;
    protected int currentLevel;
    protected List<Platform> platforms;
    protected List<Color> supportedColors;
    protected double minX, minY, maxX, maxY;
    protected static final double PLATFORM_BASE_WIDTH = 120;
    protected static final double PLATFORM_BASE_Y_Factor = 0.1;
    protected static final double PLATFORM_HEIGHT = 5;

    public Level(int currentLevel, int noOfPlatforms) {
        this.noOfPlatforms = noOfPlatforms;
        this.currentLevel = currentLevel;
        background = new Image(DIRECTORY + currentLevel + IMG_EXTENSION);
    }

    protected void setPlateSpeed(double speedRatio) {
        plateSpeedRatio = speedRatio;
    }
    protected void setClownSpeed(double speedRatio) {
        clownSpeedRatio = speedRatio;
    }

    public abstract List<String> getSupportedShapes();
    public abstract boolean isSupportedShape(String shape);
    protected void setSupportedColors(List<Color> supportedColors) {
        this.supportedColors = supportedColors;
    }
    public Image getBackground() {
        return background;
    }
    public void setBackground(Image background) {
        this.background = background;
    }
    public List<Color> getSupportedColors() {
        return supportedColors;
    }
    public boolean isSupportedColor(Color color) {
        for (Color currColor : supportedColors) {
            if (currColor == color) {
                return true;
            }
        }
        return false;
    }
    protected void addPlatforms() {
        Platform newPlatform = null;
        double stageHeight = maxY - minY;
        //TODO: Make the ratios more dependent on the stage's dimensions
        for (int i = 0 ; i < getNumPlatforms() ; i++) {
            double platformNewWidth = PLATFORM_BASE_WIDTH * (1 - 0.1 * i);
            double platformNewY = stageHeight * (0.1 + 0.05 * i);
            if(isEven(i)) {
                newPlatform = new Platform(new Point(minX
                        + platformNewWidth / 2.0,
                        platformNewY -PLATFORM_HEIGHT / 2.0),
                        Orientation.LEFT);
            } else {
                newPlatform = new Platform(new Point(maxX
                        - platformNewWidth / 2.0, platformNewY
                        -PLATFORM_HEIGHT / 2.0),
                        Orientation.RIGHT);
            }
            newPlatform.setHeight(new SimpleDoubleProperty(PLATFORM_HEIGHT));
            newPlatform.setWidth(new SimpleDoubleProperty(platformNewWidth));
            platforms.add(newPlatform);
        }
    }

    private boolean isEven(int i) {
        return (i % 2) == 0;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public int getNumPlatforms() {
        return noOfPlatforms;
    }

    public void setNumberOfPlatforms(int size) {
        this.noOfPlatforms = size;
    }
}
