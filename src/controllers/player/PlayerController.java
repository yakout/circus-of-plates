package controllers.player;

import controllers.main.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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
        PlayerFactory.getFactory().getPlayer(playerName).setSpeed(25); // 5 for primary joystick as it's too fast
                                                                        // 20 is default
        return player;
    }

    public void moveLeft(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName)).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transsition = players.get(playerName).getLayoutX()
                - PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0, Math.min(transsition, maxDistance));
        players.get(playerName).setLayoutX(newX);
    }

    public void moveRight(String playerName) {
        double playerWidth = ((AnchorPane) players.get(playerName)).getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() - playerWidth;

        double transsition = players.get(playerName).getLayoutX()
                + PlayerFactory.getFactory().getPlayer(playerName).getSpeed();
        double newX = Math.max(0,
                Math.min(transsition, maxDistance));
        players.get(playerName).setLayoutX(newX);
    }

}
