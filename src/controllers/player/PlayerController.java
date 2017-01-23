package controllers.player;

import controllers.input.InputType;
import controllers.main.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import models.players.Player;
import models.players.PlayerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    private Map<String, controllers.player.Player> players;

    public PlayerController() {
        players = new HashMap<>();
    }

    public Node createPlayer(String path, String playerName, InputType inputType) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node player = FXMLLoader.load(url);
        Player playerModel = PlayerFactory.getFactory().registerPlayer(playerName);
        players.put(playerName, new controllers.player.Player(playerName, player, playerModel));
        playerModel.setInputType(inputType);
        playerModel.setSpeed(0.15); // 5 for primary joystick as it's too fast
                                                                        // 20 is default
        return player;
    }

    public void moveLeft(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName).getPlayerNode()).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transition = players.get(playerName).getPlayerNode().getLayoutX()
                - PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0, Math.min(transition, maxDistance));
        players.get(playerName).getPlayerNode().setLayoutX(newX);
    }

    public void moveRight(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName).getPlayerNode()).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transition = players.get(playerName).getPlayerNode().getLayoutX()
                + PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0,
                Math.min(transition, maxDistance));
        players.get(playerName).getPlayerNode().setLayoutX(newX);
    }

    public Node createStick(String path) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node plate = FXMLLoader.load(url);

        return plate;
    }

    public void bindStickWithPlayer(Node player, Node stick) {
        stick.layoutXProperty().bind(player.translateXProperty());
    }
}
