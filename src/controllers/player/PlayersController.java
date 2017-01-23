package controllers.player;

import controllers.input.InputType;
import controllers.main.GameController;
import controllers.shape.ShapeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import models.players.Player;
import models.players.PlayerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class PlayersController {
    private Map<String, PlayerController> players;
    private Pane gamePane;
    private static Logger logger = LogManager.getLogger(PlayersController
            .class);
    public PlayersController(Pane gamePane) {
        players = new HashMap<>();
        this.gamePane = gamePane;
    }

    public Node createPlayer(String path, String playerName, InputType
            inputType) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node player = FXMLLoader.load(url);
        PlayerFactory.getFactory().registerPlayer
                (playerName).setInputType(inputType);
        Player playerModel = PlayerFactory.getFactory().getPlayer(playerName);
        playerModel.setInputType(inputType);
        playerModel.setPlayerUrl(path);
        playerModel.setSpeed(1); // 5
        // for primary joystick as it's too fast
        // 20 is default
        gamePane.getChildren().add(player);
        PlayerController playerController = new PlayerController(playerName, player, playerModel);
        players.put(playerName, playerController);
        return player;
    }

    public void moveLeft(String playerName) {
        double playerWidth = players.get(playerName).getPlayerView()
                .getLayoutBounds().getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() -
                playerWidth;

        double transition = getTransition(playerName, -1);
        double newX = Math.max(0, Math.min(transition, maxDistance));
        players.get(playerName).getPlayerView().setLayoutX(newX);
    }

    public void moveRight(String playerName) {
        double playerWidth = getPlayerWidth(playerName);
        double maxDistance = GameController.getInstance().getStageWidth() -
                playerWidth;
        double transition = getTransition(playerName, 1);
        double newX = Math.max(0,
                Math.min(transition, maxDistance));
        players.get(playerName).getPlayerView().setLayoutX(newX);
    }

    private double getPlayerWidth(String playerName) {
        return players.get(playerName).getPlayerView()
                .getLayoutBounds().getWidth();
    }

    private double getTransition(String playerName, double sign) {
        return players.get(playerName).getPlayerView().getLayoutX()
                + sign * players.get(playerName).getPlayerModel().getSpeed();
    }

    public void bindLeftStickWithPlayer(Node player, Node stick) {
        System.out.println("Stick Layout X Before Binding " + stick.getLayoutX
                ());
        System.out.println("Player Layout X Before Binding " + player.getLayoutX
                ());
        stick.setLayoutX(player.getLayoutX());
        stick.setLayoutY(player.getLayoutY());
        System.out.println("Stick Layout X After Binding " + stick.getLayoutX
                ());
        System.out.println("Player Layout X After Binding " + player.getLayoutX
                ());
        stick.translateXProperty().bind(player.translateXProperty());
    }

    public void bindRightStickWithPlayer(Node player, Node stick) {
        stick.setLayoutX(player.getLayoutX() + player.getLayoutBounds().getWidth());
        stick.setLayoutY(player.getLayoutY());
        stick.translateXProperty().bind(player.translateXProperty());
    }

    public synchronized boolean checkIntersection(
            ShapeController<? extends Node> shapeController) {
        for (String name : players.keySet()) {
            if (players.get(name).intersectsLeftStick(shapeController)) {
                logger.debug("A Shape Intersected With the Left Stick of "
                        + "Player: " + name);
                return true;
            }
            if (players.get(name).intersectsRightStick(shapeController)) {
                logger.debug("A Shape Intersected With the Right Stick of "
                        + "Player: " + name);
                return true;
            }
        }
        return false;
    }
}
