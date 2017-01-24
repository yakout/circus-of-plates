package models.data;

import models.levels.Level;
import models.players.Player;
import models.shapes.Shape;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mohammed Abdelbarry on 23-Jan-17.
 */
public class ModelDataHolder {
    private Level activeLevel;
    private Set<Shape> shapes;
    private Set<Player> players;
    public ModelDataHolder() {
        shapes = new HashSet<>();
        players = new HashSet<>();
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(Level activeLevel) {
        this.activeLevel = activeLevel;
    }

    public boolean addShape(Shape shape) {
        return shapes.add(shape);
    }

    public boolean removeShape(Shape shape) {
        return shapes.remove(shape);
    }

    public boolean addPlayer(Player player) {
        return players.add(player);
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }
}
