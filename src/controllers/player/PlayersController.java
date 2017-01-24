package controllers.player;

import controllers.AudioPlayer;
import controllers.input.InputType;
import controllers.main.GameController;
import controllers.shape.ShapeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import models.ShapePool;
import models.players.Player;
import models.players.PlayerFactory;
import models.players.Stick;
import models.shapes.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
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
        playerModel.setSpeed(0.2); // 5
        // for primary joystick as it's too fast
        // 20 is default
        gamePane.getChildren().add(player);
        PlayerController playerController = new PlayerController(playerName, player, playerModel);
        players.put(playerName, playerController);
        return player;
    }

    public synchronized void moveLeft(String playerName) {
        double playerWidth = players.get(playerName).getPlayerView()
                .getLayoutBounds().getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() -
                playerWidth;

        double transition = getTransition(playerName, -1);
        double newX = Math.max(0, Math.min(transition, maxDistance));
        players.get(playerName).getPlayerView().setLayoutX(newX);
    }

    public synchronized void moveRight(String playerName) {
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

    public void removeShapes(String playerName, Stick stick) {
        players.get(playerName).removeShape(stick);
        new Thread(() -> {
            new MediaPlayer(AudioPlayer.newScoreMedia).play();
        });
    }
}
