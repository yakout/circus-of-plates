package controllers.player;

import controllers.AudioPlayer;
import controllers.input.InputType;
import controllers.main.GameController;
import controllers.shape.ShapeController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.players.Player;
import models.players.PlayerFactory;
import models.players.Stick;
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

    /**
     * Default constructor of this class.
     * @param gamePane {@link Pane} the game pane.
     */
    public PlayersController(Pane gamePane) {
        players = new HashMap<>();
        this.gamePane = gamePane;
    }

    /**
     * Creates the player as a node.
     * @param path the path of his clown.
     * @param playerName the name of the player.
     * @param inputType the input type of the current player.
     * @return the player as a node to the view.
     * @throws IOException if not found file with such URL.
     */
    public Node createPlayer(String path, String playerName, InputType
            inputType) throws IOException {
        URL url = new File(path).toURI().toURL();
        Node player = FXMLLoader.load(url);
        PlayerFactory.getFactory().registerPlayer
                (playerName).setInputType(inputType);
        Player playerModel = PlayerFactory.getFactory().getPlayer(playerName);
        playerModel.setInputType(inputType);
        playerModel.setPlayerUrl(path);
        playerModel.setSpeed(20); // 5
        // for primary joystick as it's too fast
        // 20 is default
        gamePane.getChildren().add(player);
        PlayerController playerController = new PlayerController(playerName,
                player, playerModel);
        players.put(playerName, playerController);
        return player;
    }

    /**
     * Creates the player with the given name.
     * @param playerModel {@link Player} the player model.
     * @return the Player as a node.
     */
    public Node createPlayer(Player playerModel) {
        try {
            URL url = new File(playerModel.getPlayerUrl()).toURI().toURL();
            Node player = FXMLLoader.load(url);
            String playerName = playerModel.getName();
            PlayerFactory.getFactory().registerPlayer
                    (playerName).setInputType(playerModel.getInputType());
            Player newPlayerModel = PlayerFactory.getFactory().getPlayer
                    (playerName);
            newPlayerModel.setInputType(playerModel.getInputType());
            newPlayerModel.setPlayerUrl(playerModel.getPlayerUrl());
            newPlayerModel.setSpeed(playerModel.getSpeed());
            newPlayerModel.setLeftStick(playerModel.getLeftStick());
            newPlayerModel.setRightStick(playerModel.getRightStick());
            newPlayerModel.setScore(playerModel.getScore());
            newPlayerModel.setPosition(playerModel.getPosition());
            gamePane.getChildren().add(player);
            PlayerController playerController = new PlayerController
                    (playerModel.getName(),
                            player, playerModel);
            players.put(playerModel.getName(), playerController);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playerController.addShapes();
                }
            });
            return player;
        } catch (IOException e) {
            logger.fatal("Failed to Get Player View", e);
        }
        return null;
    }

    /**
     * moves the player to the left.
     * @param playerName the name of the current player.
     */
    public synchronized void moveLeft(String playerName) {
        double playerWidth = players.get(playerName).getPlayerView()
                .getLayoutBounds().getWidth();
        double maxDistance = GameController.getInstance().getStageWidth() -
                playerWidth;

        double transition = getTransition(playerName, -1);
        double newX = Math.max(0, Math.min(transition, maxDistance));
        players.get(playerName).getPlayerView().setLayoutX(newX);
    }

    /**
     * moves the player to the right.
     * @param playerName the name of the current player.
     */
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

    /**
     * Checks for intersection with the given plate.
     * @param shapeController {@link ShapeController} the controller of the
     * shape.
     * @return where intersects or not.
     */
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

    /**
     * removes the shape out of the stick.
     * @param playerName the player name.
     * @param stick the sticks to move shapes from.
     */
    public void removeShapes(String playerName, Stick stick) {
        players.get(playerName).removeShape(stick);
        logger.debug("Last three shapes are removed from stick.");

        AudioPlayer.newScoreMediaPlayer.play();
        AudioPlayer.newScoreMediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Gets the players' names.
     * @return {@link Collection} collection of players' names.
     */
    public Collection<String> getPlayersNames() {
        return players.keySet();
    }

    /**
     * Gets the player model.
     * @param playerName the player name.
     * @return {@link Player} the player model.
     */
    public Player getPlayerModel(String playerName) {
        if (players.containsKey(playerName)) {
            return players.get(playerName).getPlayerModel();
        }
        return null;
    }
}
