package models.levels;

import java.util.List;

import javafx.scene.paint.Color;
import models.shapes.Shape;

public interface Level {
    
    // TODO not an object :3
    public Object getBackground();
    public void setBackground(Object newBackground);
    
    public int getNumPlatforms();
    
    public double getPlatesSpeed();
    public double getPlayerSpeed();
    
    public List<Class<?>> getSupportedShapes();
    public boolean isSupportedShape();
    
    public List<Color> getSupportedColors();
    public boolean isSupportedColor(Color color);
}
