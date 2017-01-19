package models.levels;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import models.Platform;
import models.Point;
import models.states.Orientation;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelOne implements Level {

    private Image background;
    private int noOfPlatforms = 2;
    private List<Platform> platforms;
    final double PLATE_SPEED = 2.0;
    final double CLOWN_SPEED = 20.0;
    final double platformOffset = 30.0;

    public void LevelOne() {
        background = new Image("assets/images/backgrounds/background_1.png");
    }

    @Override
    public Image getBackground() {
        return background;
    }

    @Override
    public void setBackground(Image background) {
        this.background = background;
    }

    @Override
    public double getPlatesSpeed() {
        return PLATE_SPEED;
    }

    @Override
    public double getPlayerSpeed() {
        return CLOWN_SPEED;
    }

    @Override
    public List<String> getSupportedShapes() {
        return null;
    }

    @Override
    public boolean isSupportedShape() {
        return false;
    }

    @Override
    public List<Color> getSupportedColors() {
        return null;
    }

    @Override
    public boolean isSupportedColor(Color color) {
        return false;
    }

    @Override
    public int getNumPlatforms() {
        return noOfPlatforms;
    }

    @Override
    public List<Platform> getPlatforms() {
        for (int i = 0; i < noOfPlatforms; i++) {
            if (i % 2 == 0) {
                Platform platform = new Platform(, Orientation.RIGHT);
                platform.setWidth();
                platforms.add();
            } else {
                platforms.add(new Platform(, Orientation.LEFT));
            }
        }

        return null;
    }

    @Override
    public void setNumberOfPlatforms(int size) {
        noOfPlatforms = size;
    }
}
