package models.players;

import controllers.input.InputType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class PlayerFactory {
    private static PlayerFactory factory;
    private static Logger logger = LogManager.getLogger(PlayerFactory.class);
    private Map<String, Player> players;

    private PlayerFactory() {
        players = new HashMap<>();
    }

    public static PlayerFactory getFactory() {
        if (factory == null) {
            factory = new PlayerFactory();
        }
        return factory;
    }

    public Player registerPlayer(String name) {
        players.put(name, new Player(name));
        logger.info("Player: " + name + " Registered to the Factory");
        return players.get(name);
    }

    public void removePlayer(String name) {
        players.remove(name);
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public int getPlayersSize() {
        return players.size();
    }

    public String getPlayerNameWithController(InputType inputType) {
        for (Map.Entry<String, Player> playerEntry : players.entrySet()) {
            if (playerEntry.getValue().getInputType() == inputType) {
                return playerEntry.getValue().getName();
            }
        }
        return null;
    }

    public void reset() {
        players.clear();
    }
}
