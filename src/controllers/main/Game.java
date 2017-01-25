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


    Game() {
        initilize();
    }

    public void setLevel(int level) {
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

    public int getLevel() {
        return level;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    void initilize() {
        level = 1;
        setLevel(level);
        shapeControllers = new ArrayList<>();
        GameController.getInstance().getMainGame().getChildren().clear();
        playersController = new PlayersController(GameController.getInstance().getMainGame());
        gameBoard = GameBoard.getInstance();
        gameBoard.reset();
    }

    public void createPlayer(String path, String playerName, InputType inputType) {
        try {
            playersController.createPlayer(path, playerName, inputType);
            gameBoard.addPlayerPanel(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createPlayer(Player player) {
        playersController.createPlayer(player);
    }


    public void removeShapeController(ShapeController<? extends Node>
                                              shapeController) {
        shapeControllers.remove(shapeController);
    }

    public void addShapeController(ShapeController<? extends Node>
                                           shapeController) {
        shapeControllers.add(shapeController);
    }

    void pause() {
        shapeControllers.forEach(ShapeController::gamePaused);
        gameBoard.pause();
        if(shapeGenerator != null) {
            shapeGenerator.pauseGenerator();
        }
    }

    void resume() {
        shapeControllers.forEach(ShapeController::gameResumed);
        gameBoard.resume();
        shapeGenerator.resumeGenerator();
    }

    void startNormalGame() {
        if (PlayerFactory.getFactory().getPlayersSize() == 0) {
            addDefaultPlayers();
            logger.info("Players has created");
        }

        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : currentLevel.getPlatforms()) {
            GameController.getInstance().getMainGame().getChildren().add(builder.build(platform));
        }
        shapeGenerator = new ShapeGenerator(currentLevel, GameController.getInstance().getMainGame());
        AudioPlayer.backgroundMediaPlayer.play();
        GameController.getInstance().continueGame();
    }

    void startNormalGame(long counter) {
        if (PlayerFactory.getFactory().getPlayersSize() == 0) {
            addDefaultPlayers();
        }

        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : currentLevel.getPlatforms()) {
            GameController.getInstance().getMainGame().getChildren().add(builder.build(platform));
        }
        shapeGenerator = new ShapeGenerator(currentLevel, GameController
                .getInstance().getMainGame(), counter);
        GameController.getInstance().continueGame();
    }

    private void addDefaultPlayers() {
        createPlayer(DEFAULT_PLAYER_1, "player1", InputType
                .KEYBOARD_PRIMARY);
        createPlayer(DEFAULT_PLAYER_2, "player2", InputType
                .KEYBOARD_SECONDARY);
    }

    void updateScore(int score, String playerName, Stick stick) {
        gameBoard.updateScore(score, playerName);
    }

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

    public PlayersController getPlayersController() {
        return playersController;
    }

    public Collection<ShapeController<? extends Node>> getShapeControllers() {
        return shapeControllers;
    }

    public void setShapeControllers(Collection<ShapeController<? extends
            Node>> shapeControllers) {
        this.shapeControllers = shapeControllers;
    }

    public long getShapeGeneratorCounter() {
        return shapeGenerator.getGenerationThreadCounter();
    }
}
