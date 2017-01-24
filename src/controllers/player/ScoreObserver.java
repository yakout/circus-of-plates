package controllers.player;

import models.players.Stick;

/**
 * Created by ahmedyakout on 1/24/17.
 */
public interface ScoreObserver {
    void update(int score, String playerName, Stick stick);
}
