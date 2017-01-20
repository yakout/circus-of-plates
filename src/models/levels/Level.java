package models.levels;

import java.util.List;

import javafx.scene.image.Image;
import models.states.Color;
import models.Platform;

public abstract class Level {

    private final String IMG_EXTENSION = ".png";
    private final String DIRECTORY = "assets/images/backgrounds/background_";
    private double plateSpeedRatio = 1.0;
    private double clownSpeedRatio = 1.0;
    protected int noOfPlatforms;
    protected Image background;
    protected int currentLevel;
    private List<Platform> platforms;
    private List<String> supportedShapes;
    private List<Color> supportedColors;

    // TODO not an object :3

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

    protected void setSupportedShapes(List<String> supportedShapes) {
        this.supportedShapes = supportedShapes;
    }

    protected void setSupportedColors(List<Color> supportedColors) {
        this.supportedColors = supportedColors;
    }

    public Image getBackground() {
        return background;
    }
    public void setBackground(Image background) {
        this.background = background;
    }

    public List<String> getSupportedShapes() {
        return supportedShapes;
    }
    public boolean isSupportedShape(String shape) {
        for (String key : supportedShapes) {
            if (key.equals(shape)) {
                return true;
            }
        }
        return false;
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

    public abstract List<Platform> getPlatforms();

    public int getNumPlatforms() {
        return noOfPlatforms;
    }

    public void setNumberOfPlatforms(int size) {
        this.noOfPlatforms = size;
    }
}
