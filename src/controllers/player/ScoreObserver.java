package controllers.player;

import models.players.Player;
import models.players.Stick;
import models.shapes.Shape;

import java.util.Collection;

/**
 * Created by ahmedyakout on 1/24/17.
 */
public interface ScoreObserver {
    void update(int score, String playerName, Stick stick);
}
