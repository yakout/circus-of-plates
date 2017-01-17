package models.shapes;

import java.lang.Thread.State; // TODO Not this state :D
import java.util.List;

import javafx.scene.paint.Color;
import models.Point;
import models.levels.Level;


public interface Shape {
    
    public State getState();
    public void setState(State newState);
    
    public Color getColor();
    public void setColor(Color newColor);
    
    public int getWidth();
    public int getHeight();
    
    public Point getPosition();
    public void setPosition();
    
    public double getHorizontalVelocity();
    public double getVerticalVelocity();
    
    public void translate(double x, double y);
    public void setVisible(boolean isVisible);

    public List<Level> getSupportedLevels();
    public boolean isSupportedLevel();
        
}
