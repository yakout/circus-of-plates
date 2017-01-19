package models.levels;

import java.util.List;

import javafx.scene.image.Image;
import models.states.Color;
import models.Platform;

public abstract class Level {

    private final String IMG_EXTENSION = ".png";
    private final String DIRECTORY = "assets/images/backgrounds/background_";
    protected double plateSpeed = 2.0;
    protected double clownSpeed = 20.0;
    protected int noOfPlatforms;
    protected Image background;
    protected int currentLevel;
    // TODO not an object :3

    public Level(int currentLevel, int noOfPlatforms) {
        this.noOfPlatforms = noOfPlatforms;
        this.currentLevel = currentLevel;
        background = new Image(DIRECTORY + currentLevel + IMG_EXTENSION);
    }

    public Image getBackground() {
        return background;
    }
    public void setBackground(Image background) {
        this.background = background;
    }

    public double getPlatesSpeed() {
        return plateSpeed;
    }
    public double getPlayerSpeed() {
        return clownSpeed;
    }

    public abstract List<String> getSupportedShapes();
    public abstract boolean isSupportedShape(String shape);

    public abstract List<Color> getSupportedColors();
    public abstract boolean isSupportedColor(Color color);

    public int getNumPlatforms() {
        return noOfPlatforms;
    }
    public abstract List<Platform> getPlatforms();

    public void setNumberOfPlatforms(int size) {
        this.noOfPlatforms = size;
    }
}
