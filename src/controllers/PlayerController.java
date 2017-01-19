package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import models.players.PlayerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class PlayerController {
    private Map<String, Node> players;

    PlayerController() {
    }

    public Node createPlayer(String playerName, URL url) throws IOException {
        Node player = FXMLLoader.load(url);
        players.put(playerName, player);
        return player;
    }

    public void moveLeft(String playerName) {
        players.get(playerName).setLayoutX(players.get(playerName).getLayoutX()
                - PlayerFactory.getFactory().getPlayer(playerName).getSpeed());
    }

    public void moveRight(String playerName) {
        players.get(playerName).setLayoutX(players.get(playerName).getLayoutX()
                + PlayerFactory.getFactory().getPlayer(playerName).getSpeed());
    }

}
