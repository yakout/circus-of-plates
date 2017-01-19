package models.levels;

import javafx.scene.image.Image;
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
public class LevelOne implements Level {

    private Image background;
    private int noOfPlatforms = 2;
    private List<Platform> platforms;
    private List<String> supportedShapes;
    private List<Color> supportedColors;
    final double PLATE_SPEED = 2.0;
    final double CLOWN_SPEED = 20.0;
    final double platformOffset = 30.0;

    public void LevelOne() {
        background = new Image("assets/images/backgrounds/background_1.png");
        supportedShapes = new ArrayList<>();
        supportedColors = new ArrayList<>();
        addSupportedColors();
        supportedShapes.add(PlateShape.getIdentifier());
        // TODO (adding more shapes goes here).
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

    @Override
    public List<Color> getSupportedColors() {
        return supportedColors;
    }

    @Override
    public boolean isSupportedColor(Color color) {
        for (Color currColor : supportedColors) {
            if (currColor == color) {
                return true;
            }
        }
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

    private void addSupportedColors() {
        supportedColors.add(Color.GREEN);
        supportedColors.add(Color.YELLOW);
        supportedColors.add(Color.LIGHT_BLUE);
        supportedColors.add(Color.ORANGE);
    }
}
