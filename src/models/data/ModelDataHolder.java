package models.data;

import models.levels.Level;
import models.players.Player;
import models.shapes.Shape;

import java.util.Collection;

/**
 * Created by Mohammed Abdelbarry on 23-Jan-17.
 */
public class ModelDataHolder {
    private Level activeLevel;
    private Collection<Shape> shapes;
    private Collection<Player> players;
}
