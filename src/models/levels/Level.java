package models.levels;

import java.util.List;

import javafx.scene.paint.Color;
import models.Platform;
import models.shapes.Shape;

public interface Level {
    
    // TODO not an object :3
    public Object getBackground();
    public void setBackground(Object newBackground);
    

    public double getPlatesSpeed();
    public double getPlayerSpeed();

    public List<String> getSupportedShapes();
    public boolean isSupportedShape();

    public List<Color> getSupportedColors();
    public boolean isSupportedColor(Color color);

    public int getNumPlatforms();
    public List<Platform> getPlatforms();
}
