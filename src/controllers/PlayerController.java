package controllers;

import controllers.input.InputType;
import controllers.main.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import models.players.PlayerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    private Map<String, Node> players;

    public PlayerController() {
        players = new HashMap<>();
    }

    public Node createPlayer(String playerName, URL url) throws IOException {
        Node player = FXMLLoader.load(url);
        players.put(playerName, player);
        PlayerFactory.getFactory().createPlayer(playerName).setInputType(InputType.KEYBOARD_TWO);
        PlayerFactory.getFactory().getPlayer(playerName).setSpeed(20);
        return player;
    }

    public void moveLeft(String playerName) {
        double transsition = players.get(playerName).getLayoutX()
                - PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0,
                Math.min(transsition, GameController.getInstance().getStageWidth()));
        players.get(playerName).setLayoutX(newX);
    }

    public void moveRight(String playerName) {
        double transsition = players.get(playerName).getLayoutX()
                + PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0,
                Math.min(transsition, GameController.getInstance().getStageWidth()));
        players.get(playerName).setLayoutX(newX);
    }

}
