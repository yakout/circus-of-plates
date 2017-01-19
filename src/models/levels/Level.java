package models.levels;

import java.util.List;

import javafx.scene.image.Image;
import models.states.Color;
import models.Platform;

public abstract class Level {

    private final String IMG_EXTENSION = ".png";
    private final String DIRECTORY = "assets/images/backgrounds/background_";
    protected int noOfPlatforms;
    protected Image background;
    protected int currentLevel;
    // TODO not an object :3

    public Level(int noOfPlatforms, Image background, int currentLevel) {
        this.noOfPlatforms = noOfPlatforms;
        this.background = background;
        this.currentLevel = currentLevel;
    }

    public Level(int noOfPlatforms, int currentLevel) {
        this.noOfPlatforms = noOfPlatforms;
        this.currentLevel = currentLevel;
        background = new Image(DIRECTORY + currentLevel + IMG_EXTENSION);
    }

    public abstract Image getBackground();
    public abstract void setBackground(Image background);

    public abstract double getPlatesSpeed();
    public abstract double getPlayerSpeed();

    public abstract List<String> getSupportedShapes();
    public abstract boolean isSupportedShape(String shape);

    public abstract List<Color> getSupportedColors();
    public abstract boolean isSupportedColor(Color color);

    public abstract int getNumPlatforms();
    public abstract List<Platform> getPlatforms();

    public abstract void setNumberOfPlatforms(int size);
}
