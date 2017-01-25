package models.data;

import models.levels.Level;
import models.players.Player;
import models.shapes.Shape;
import models.shapes.util.ShapePlatformPair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mohammed Abdelbarry on 23-Jan-17.
 */
public class ModelDataHolder {
    private Level activeLevel;

    private Set<ShapePlatformPair> shapes;

    private Set<Player> players;

    private long generatorCounter;

    public static final long INVALID_COUNTER_VALUE = -1;

    public ModelDataHolder() {
        shapes = new HashSet<>();
        players = new HashSet<>();
        generatorCounter = INVALID_COUNTER_VALUE;
    }
    public Level getActiveLevel() {
        return activeLevel;
    }
    public void setActiveLevel(Level activeLevel) {
        this.activeLevel = activeLevel;
    }

    public Set<ShapePlatformPair> getShapes() {
        return shapes;
    }

    public void setShapes(Set<ShapePlatformPair> shapes) {
        this.shapes = shapes;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public boolean addShape(ShapePlatformPair shape) {
        return shapes.add(shape);
    }

    public boolean removeShape(ShapePlatformPair shape) {
        return shapes.remove(shape);
    }

    public boolean addPlayer(Player player) {
        return players.add(player);
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    public long getGeneratorCounter() {
        return generatorCounter;
    }

    public void setGeneratorCounter(long generatorCounter) {
        this.generatorCounter = generatorCounter;
    }
}
