package models.levels;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import models.Platform;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelOne implements Level {

    private Image background;

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
        return 0;
    }

    @Override
    public double getPlayerSpeed() {
        return 0;
    }

    @Override
    public List<Class<?>> getSupportedShapes() {
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
        return 0;
    }

    @Override
    public List<Platform> getPlatforms() {
        return null;
    }

    @Override
    public void setNumberOfPlatforms(int size) {

    }
}
