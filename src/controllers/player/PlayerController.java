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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    private Map<String, Node> players;

    public PlayerController() {
        players = new HashMap<>();
    }

    public Node createPlayer(String path, String playerName, InputType inputType) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node player = FXMLLoader.load(url);
        players.put(playerName, player);
        PlayerFactory.getFactory().registerPlayer(playerName).setInputType(inputType);
        PlayerFactory.getFactory().getPlayer(playerName).setSpeed(1); // 5 for primary joystick as it's too fast
                                                                        // 20 is default
        return player;
    }

    public void moveLeft(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName)).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transition = players.get(playerName).getLayoutX()
                - PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0, Math.min(transition, maxDistance));
        players.get(playerName).setLayoutX(newX);
    }

    public void moveRight(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName)).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transition = players.get(playerName).getLayoutX()
                + PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0,
                Math.min(transition, maxDistance));
        players.get(playerName).setLayoutX(newX);
    }

    public Node createStick(String path) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node plate = FXMLLoader.load(url);

        return plate;
    }

    public void bindStickWithPlayer(Node player, Node stick) {
//        stick.layoutXProperty().bindBidirectional(player.layoutXProperty());
    }
}
