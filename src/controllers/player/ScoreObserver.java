package controllers.player;

import models.players.Player;
import models.shapes.Shape;

import java.util.Collection;

/**
 * Created by ahmedyakout on 1/24/17.
 */
public interface ScoreObserver {
    void update(Player player, Collection<Shape> shapesToRemove);
}
