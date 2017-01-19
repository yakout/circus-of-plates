package models.levels;

import javafx.scene.paint.Color;
import models.Platform;

import java.util.List;

/**
 * Created by Ahmed Khaled on 19/01/2017.
 */
public class LevelTwo implements Level {
    @Override
    public Object getBackground() {
        return null;
    }

    @Override
    public void setBackground(Object newBackground) {

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
        return 0;
    }

    @Override
    public List<Platform> getPlatforms() {
        return null;
    }
}
