package controllers.main;

import controllers.AudioPlayer;
import controllers.board.GameBoard;
import controllers.input.InputType;
import controllers.level.PlatformBuilder;
import controllers.player.PlayersController;
import controllers.shape.ShapeController;
import controllers.shape.ShapeGenerator;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.levels.Level;
import models.levels.util.LevelFactory;
import models.players.Player;
import models.players.PlayerFactory;
import models.players.Stick;
import models.shapes.util.ShapePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ahmed Yakout on 1/25/17.
 */
public class Game {
    private static Logger logger = LogManager.getLogger(Game.class);
    private final String DEFAULT_PLAYER_1 = "src/views/clowns/clown_5/clown.fxml";
    private final String DEFAULT_PLAYER_2 = "src/views/clowns/clown_6/clown.fxml";
    private PlayersController playersController;
    private int level;
    private Level currentLevel;
    private Collection<ShapeController<? extends Node>> shapeControllers;
    private ShapeGenerator shapeGenerator;
    private GameBoard gameBoard;

    /**
     * Game constructor and sets default values.
     */
    Game() {
        logger.info("new Game Object is created");
        initialize();
    }

    /**
     * Sets the current level that the user is in.
     * @param level the current level of the game.
     */
    public void setLevel(int level) {
        this.level = level;
        AnchorPane rootPane = GameController.getInstance().getRootPane();
        currentLevel = LevelFactory.getInstance().createLevel(level, rootPane
                        .getLayoutX(),
                rootPane.getLayoutY(), rootPane.getLayoutX()
                        + rootPane.getWidth(), rootPane.getLayoutY()
                        + rootPane.getHeight());
        if (currentLevel == null) {
            logger.fatal("Couldn't Create Level " + level);
        }
    }

    /**
     * Gets the current level of the game as an integer.
     * @return the level of the game.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current level of the game.
     * @return {@link Level} the level of the game.
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level of the game.
     * @param currentLevel {@link Level} the current level of the game.
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Initialized used data structures used in this class.
     * Got called in the constructor.
     */
    void initialize() {
        shapeControllers = new ArrayList<>();
        GameController.getInstance().getMainGame().getChildren().clear();
        playersController = new PlayersController(GameController.getInstance().getMainGame());
        gameBoard = GameBoard.getInstance();
        gameBoard.reset();
    }

    /**
     * Creates player with the given data.
     * Delegates it to the player controller.
     * This is called after loading a saved game.
     * @param path the path of the player clown.
     * @param playerName the name of the player.
     * @param inputType {@link InputType} input type controller for the
     * current player.
     */
    public void createPlayer(String path, String playerName, InputType inputType) {
        try {
            playersController.createPlayer(path, playerName, inputType);
            gameBoard.addPlayerPanel(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates player with the given data.
     * @param player {@link Player} Player model.
     */
    void createPlayer(Player player) {
        playersController.createPlayer(player);
        gameBoard.addPlayerPanel(player.getName());
        logger.info("created player with input type "
                + player.getInputType() + " speed = " + player.getSpeed());
    }

    void createPlayer(List<Player> players) {
        for (Player player : players) {
            createPlayer(player);
        }
    }

    /**
     * Removes the given shape controller from the list.
     * @param shapeController {@link ShapeController} shape controller of the
     * given shape.
     */
    public void removeShapeController(ShapeController<? extends Node>
                                              shapeController) {
        shapeControllers.remove(shapeController);
    }

    /**
     * Adds the given shape controller from the list.
     * @param shapeController {@link ShapeController} shape controller of the
     * given shape.
     */
    public void addShapeController(ShapeController<? extends Node>
                                           shapeController) {
        shapeControllers.add(shapeController);
    }

    /**
     * Pauses the current played game.
     * Pauses all the controllers that are running.
     */
    void pause() {
        shapeControllers.forEach(ShapeController::gamePaused);
        gameBoard.pause();
        if(shapeGenerator != null) {
            shapeGenerator.pauseGenerator();
        }
    }

    /**
     * Resumes the current paused game.
     * Resumes all the controllers that are paused.
     */
    void resume() {
        shapeControllers.forEach(ShapeController::gameResumed);
        gameBoard.resume();
        shapeGenerator.resumeGenerator();
    }

    /**
     * Starts a normal game type.
     */
    void startNormalGame() {
        if (PlayerFactory.getFactory().getPlayersSize() == 0) {
            addDefaultPlayers();
            logger.info("Players were created");
        }
        logger.info("players size in factory = " + PlayerFactory.getFactory().getPlayersSize());

        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : currentLevel.getPlatforms()) {
            GameController.getInstance().getMainGame().getChildren().add(builder.build(platform));
        }
        shapeGenerator = new ShapeGenerator(currentLevel, GameController.getInstance().getMainGame());

        AudioPlayer.backgroundMediaPlayer.setOnEndOfMedia(() ->
                AudioPlayer.backgroundMediaPlayer.seek(Duration.ZERO));
        AudioPlayer.backgroundMediaPlayer.play();

        GameController.getInstance().continueGame();
    }

    /**
     * Starts a normal game type.
     * Assigned counter to the argument in order to compute the time
     * remaining to generate new shape at shape generator.
     * @param counter the counter of the shape generator thread.
     */
    void startNormalGame(long counter) {
        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : currentLevel.getPlatforms()) {
            GameController.getInstance().getMainGame().getChildren().add(
                    builder.build(platform));
        }
        shapeGenerator = new ShapeGenerator(currentLevel, GameController
                .getInstance().getMainGame(), counter);
        AudioPlayer.backgroundMediaPlayer.play();
        GameController.getInstance().continueGame();
    }

    void startNewTimeAttack() {
        GameBoard.getInstance().initializeGameTimer();
        startNormalGame();
    }

    void startNewTimeAttack(long counter) {
        GameBoard.getInstance().initializeGameTimer();
        startNormalGame(counter);
    }

    private void addDefaultPlayers() {
        createPlayer(DEFAULT_PLAYER_1, "player1", InputType
                .KEYBOARD_PRIMARY);
        createPlayer(DEFAULT_PLAYER_2, "player2", InputType
                .KEYBOARD_SECONDARY);
    }

    /**
     * Updates the current score for the given player.
     * @param score the score of the given player.
     * @param playerName tha player name.
     * @param stick {@link Stick} the stick the the plate has fallen to.
     */
    void updateScore(int score, String playerName, Stick stick) {
        gameBoard.updateScore(score, playerName);
    }

    /**
     * Stops the the shape generator.
     * Stops shapes-movement.
     * Stops the audio player.
     * Clears the pool form shapes.
     */
    void destroy() {
        if (shapeGenerator != null) {
            shapeGenerator.stopGeneration();
        }
        PlayerFactory.getFactory().reset();
        for (ShapeController shapeController : shapeControllers) {
            shapeController.stop();
        }
        AudioPlayer.backgroundMediaPlayer.stop();
        ShapePool.clearPool();
    }

    /**
     * Gets the players controller.
     * @return {@link PlayersController} Players controller for both players.
     */
    public PlayersController getPlayersController() {
        return playersController;
    }

    /**
     * Gets all shape controllers.
     * @return {@link Collection<ShapeController>} all shape controllers.
     */
    public Collection<ShapeController<? extends Node>> getShapeControllers() {
        return shapeControllers;
    }

    /**
     * Assign the current shape controllers.
     * @param shapeControllers {@link Collection<ShapeController>} all shape
     * controllers.
     */
    public void setShapeControllers(Collection<ShapeController<? extends
            Node>> shapeControllers) {
        this.shapeControllers = shapeControllers;
    }

    /**
     * Gets the shape generator counter.
     * @return the counter of the shape generator to continue.
     */
    public long getShapeGeneratorCounter() {
        return shapeGenerator.getGenerationThreadCounter();
    }
}
