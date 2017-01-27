package controllers.main;

import controllers.AudioPlayer;
import controllers.board.GameBoard;
import controllers.input.ActionType;
import controllers.input.InputAction;
import controllers.input.InputType;
import controllers.input.joystick.Joystick;
import controllers.input.joystick.JoystickCode;
import controllers.input.joystick.JoystickEvent;
import controllers.input.joystick.JoystickType;
import controllers.menus.MenuController;
import controllers.menus.Start;
import controllers.player.ScoreObserver;
import controllers.shape.ShapeBuilder;
import controllers.shape.ShapeController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.GameMode;
import models.data.ModelDataHolder;
import models.players.Player;
import models.players.PlayerFactory;
import models.players.Stick;
import models.settings.FileConstants;
import models.shapes.util.ShapePlatformPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.file.FileHandler;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameController implements Initializable, ScoreObserver {

    private static GameController instance;
    private static Logger logger = LogManager.getLogger(GameController.class);
    private MenuController currentMenu;
    private BooleanProperty newGameStarted;
    private Map<KeyCode, Boolean> keyMap;
    private volatile boolean gamePaused = false;
    private FileHandler handler;
    private Game currentGame;
    private int currentLevel;
    private List<Player> players;
    private GameMode gameMode;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane mainGame;
    @FXML
    private AnchorPane winPane;

    /**
     * Gets the instance of the GameController using Singleton.
     * @return returns the instance of GameController class.
     */
    public synchronized static GameController getInstance() {
        return instance;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        currentLevel = 1;
        currentMenu = Start.getInstance();
        players = new ArrayList<>();
        handler = FileHandler.getInstance();
        currentGame = new Game();
        currentGame.setLevel(currentLevel);
        newGameStarted = new SimpleBooleanProperty(false);
        initializeKeyMaps();
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
    }

    private void initializeKeyMaps() {
        keyMap = new HashMap<>();
        keyMap.put(KeyCode.A, false);
        keyMap.put(KeyCode.D, false);
        keyMap.put(KeyCode.LEFT, false);
        keyMap.put(KeyCode.RIGHT, false);
    }

    /**
     * Sets the current menu that is loaded on screen.
     * @param currentMenu {@link MenuController} the curren menu that is on
     *                    screen.
     */
    public void setCurrentMenu(MenuController currentMenu) {
        this.currentMenu = currentMenu;
    }

    /**
     * Gets the main game Anchor pane.
     * @return {@link AnchorPane} returns the anchor pane containing the game.
     */
    public AnchorPane getMainGame() {
        return mainGame;
    }

    /**
     * Gets the root pane of the game Anchor pane.
     * @return {@link AnchorPane} returns the anchor pane of thhe game.
     */
    public AnchorPane getRootPane() {
        return rootPane;
    }


    private synchronized void updatePlayers() {
        if (!newGameStarted.get()) {
            return;
        }
        if (PlayerFactory.getFactory()
                .getPlayerNameWithController(InputType.KEYBOARD_SECONDARY) !=
                null) {
            if (keyMap.get(KeyCode.A)) {
                currentGame.getPlayersController().moveLeft(PlayerFactory
                        .getFactory()
                        .getPlayerNameWithController(InputType
                                .KEYBOARD_SECONDARY));
            }
            if (keyMap.get(KeyCode.D)) {
                currentGame.getPlayersController().moveRight(PlayerFactory
                        .getFactory()
                        .getPlayerNameWithController(InputType
                                .KEYBOARD_SECONDARY));
            }
        }
        if (PlayerFactory.getFactory()
                .getPlayerNameWithController(InputType.KEYBOARD_PRIMARY) !=
                null) {
            if (keyMap.get(KeyCode.LEFT)) {
                currentGame.getPlayersController().moveLeft(PlayerFactory
                        .getFactory()
                        .getPlayerNameWithController(InputType
                                .KEYBOARD_PRIMARY));
            }
            if (keyMap.get(KeyCode.RIGHT)) {
                currentGame.getPlayersController().moveRight(PlayerFactory
                        .getFactory()
                        .getPlayerNameWithController(InputType
                                .KEYBOARD_PRIMARY));
            }
        }
    }

    /**
     * Handles the released key.
     * @param event {@link KeyEvent} Event done by the user.
     */
    @FXML
    public synchronized void keyHandlerReleased(KeyEvent event) {
        keyMap.put(event.getCode(), false);
    }

    /**
     * Handles the pressed key.
     * @param event {@link KeyEvent} Event done by the user.
     */
    @FXML
    public synchronized void keyHandler(KeyEvent event) {
        keyMap.put(event.getCode(), true);
        switch (event.getCode()) {
            case ESCAPE:
                AudioPlayer.winMediaPlayer.stop();
                if (newGameStarted.get()) {
                    if (currentMenu.isVisible()) {
                        continueGame();
                        logger.info("Game is continued.");
                    } else if (winPane.isVisible()) {
                        winPane.setVisible(false);
                        currentMenu.setMenuVisible(true);
                    } else {
                        pauseGame();
                        logger.info("Game is paused.");
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Handles the pressed joystick-buttons.
     * @param event {@link KeyEvent} Event done by the user.
     */
    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType
            .JOYSTICK)
    public void performJoystickAction(JoystickEvent event) {
        String playerName2 = PlayerFactory.getFactory()
                .getPlayerNameWithController(InputType.JOYSTICK_SECONDARY);
        String playerName1 = PlayerFactory.getFactory()
                .getPlayerNameWithController(InputType.JOYSTICK_PRIMARY);

        Platform.runLater(() -> {
            if (event.getJoystickType() == JoystickType.PRIMARY) {
                if (event.getJoystickCode() == JoystickCode.LEFT) {
                    if (playerName1 != null) {
                        currentGame.getPlayersController().moveLeft
                                (playerName1);
                    }
                } else if (event.getJoystickCode() == JoystickCode.RIGHT
                        && playerName1 != null) {
                    currentGame.getPlayersController().moveRight(playerName1);
                }
            } else {
                if (event.getJoystickCode() == JoystickCode.LEFT
                        && playerName2 != null) {
                    currentGame.getPlayersController().moveLeft(playerName2);
                } else if (event.getJoystickCode() == JoystickCode.RIGHT
                        && playerName2 != null) {
                    currentGame.getPlayersController().moveRight(playerName2);
                }
            }
        });
    }


    /**
     * Saves the current game with the given name.
     * @param name the name of the game.
     */
    public void saveGame(String name) {
        System.err.println(name);
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy HH,mm,ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String fileName = name + " - " + currentDate;
        ModelDataHolder modelData = new ModelDataHolder();
        modelData.setActiveLevel(currentGame.getCurrentLevel());
        modelData.setGameMode(gameMode);
        //TODO: add player
//        modelData.addPlayer(cu)
        for (ShapeController<? extends Node> shapeController : currentGame
                .getShapeControllers()) {
            modelData.addShape(new ShapePlatformPair(shapeController
                    .getShapeModel(), shapeController.getPlatform()));
        }
        for (String player : currentGame.getPlayersController()
                .getPlayersNames()) {
            modelData.addPlayer(currentGame.getPlayersController()
                    .getPlayerModel(player));
        }
        modelData.setGeneratorCounter(currentGame.getShapeGeneratorCounter());
        if (this.gameMode == GameMode.TIME_ATTACK) {
            modelData.setRemainingTimeAttack(GameBoard.getInstance()
                    .getRemainingTime());
        }
        this.handler.write(modelData, "." + File.separator +
                        FileConstants.SAVE_PATH,
                fileName);
        logger.info("Game is saved successfully.");
    }

    /**
     * Gets the stage width in view.
     * @return the width of the stage.
     */
    public double getStageWidth() {
        return mainGame.getWidth();
    }

    /**
     * Starts the game with the given game mode.
     * @param gameMode {@link GameMode} the chosen game mode.
     */
    public void startGame(GameMode gameMode) {
        ((Start) Start.getInstance()).activeDisabledButtons(true);
        GameController.getInstance().getMainGame().setVisible(true);

        resetGame();
        newGameStarted.set(true);

        logger.info("Game is launched successfully.");
        this.gameMode = gameMode;
        System.out.println(gameMode);
        switch (gameMode) {
            case NORMAL:
                currentGame.startNormalGame();
                break;
            case TIME_ATTACK:
                currentGame.startNewTimeAttack();
                break;
            case LEVEL:
                break;
            case SANDBOX:
                break;
            default:
                break;
        }
        players.clear();
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        currentGame.destroy();
        currentGame = new Game();
        currentGame.setLevel(currentLevel);
        currentGame.createPlayer(players);
    }

    /**
     * Starts the key board listener for any action.
     */
    private void startKeyboardListener() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (!gamePaused) {
                Platform.runLater(() -> updatePlayers());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    logger.info("keyboard listener thread interrupted");
                    if (gamePaused) {
                        break;
                    }
                }
            }
        });
        executor.shutdown();
    }

    /**
     * Starts the saved data game.
     * @param modelDataHolder {@link ModelDataHolder} data model that holds
     * saved game data.
     */
    /**
     * Starts the saved data game.
     * @param modelDataHolder {@link ModelDataHolder} data model that holds
     *                        saved game data.
     */
    public void startNewLoadGame(ModelDataHolder modelDataHolder) {
        resetGame();
        players.clear();
        GameBoard.getInstance().reset();
        for (Player player : modelDataHolder.getPlayers()) {
            System.out.printf("%s has %d Shapes on his Right Stack\n",
                    player.getName(), player.getRightStack().size());
            System.out.printf("%s has %d Shapes on his Left Stack\n",
                    player.getName(), player.getLeftStack().size());
            currentGame.createPlayer(player);
            GameBoard.getInstance().updateScore(player.getScore(), player
                    .getName());
        }
        for (ShapePlatformPair shapePlatformPair : modelDataHolder.getShapes
                ()) {
            switch (shapePlatformPair.getShape().getState()) {
                case MOVING_HORIZONTALLY:
                case FALLING:
                    Node shapeView = ShapeBuilder.getInstance().build
                            (shapePlatformPair.getShape());
                    mainGame.getChildren().add(shapeView);
                    ShapeController<? extends Node> shapeController = new
                            ShapeController<>
                            (shapeView, shapePlatformPair
                                    .getShape(), shapePlatformPair
                                    .getPlatform());
                    shapeController.startMoving();
                    currentGame.getShapeControllers().add(shapeController);
                    break;
                case ON_THE_STACK:
                    logger.error("A Shape That is on the Stack Should not be"
                            + " saved with moving shapes");
                    break;
                default:
                    break;
            }
        }
        this.gameMode = modelDataHolder.getGameMode();
        currentMenu.setMenuVisible(false);
        currentGame.setCurrentLevel(modelDataHolder.getActiveLevel());
        switch (modelDataHolder.getGameMode()) {
            case NORMAL:
                currentGame.startNormalGame(modelDataHolder
                        .getGeneratorCounter());
                break;
            case TIME_ATTACK:
                GameBoard.getInstance().setGameTime(modelDataHolder
                        .getRemainingTimeAttack());
                currentGame.startNewTimeAttack(modelDataHolder
                        .getGeneratorCounter());
                break;
            default:
                break;
        }
        newGameStarted.set(true);
        ((Start) Start.getInstance()).activeDisabledButtons(true);
        System.out.println(modelDataHolder.getGeneratorCounter());
    }

    /**
     * Checks the intersection for the current state shape.
     * @param shapeController {@link ShapeController} the shape controller.
     * @return whether the the Shape intersects the stack or not.
     */
    public synchronized boolean checkIntersection(
            ShapeController<? extends Node> shapeController) {
        if (currentGame.getPlayersController().checkIntersection
                (shapeController)) {
            shapeController.shapeFellOnTheStack();
            return true;
        }
        return false;
    }

    /**
     * Pauses the game view.
     * Pauses the audio player.
     */
    public void pauseGame() {
        gamePaused = true;
        currentMenu = Start.getInstance();
        currentMenu.setMenuVisible(true);
        currentMenu.requestFocus(0);
        mainGame.setVisible(false);

        currentGame.pause();
        AudioPlayer.backgroundMediaPlayer.pause();
    }

    /**
     * Continues the current paused game.
     * Resumes the audio payer.
     */
    public void continueGame() {
        gamePaused = false;
        currentMenu.setMenuVisible(false);
        mainGame.requestFocus();
        mainGame.setVisible(true);
        currentGame.resume();
        startKeyboardListener();
        AudioPlayer.backgroundMediaPlayer.play();
    }

    /**
     * Gets the curretn game.
     * @return {@link Game}instance of the current game.
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Sets the current game level.
     * @param level the current level in integer form.
     */
    public void setCurrentGameLevel(int level) {
        currentLevel = level;
        currentGame.setLevel(level);
    }

    /**
     * Sets the current game players.
     * @param players players
     */
    public void setPlayersToCurrentGame(List<Player> players) {
        this.players = players;
    }

    /**
     * Called when any player has lost the game in order to pause running
     * threads and the whole game.
     * @param playerName the name of the player.
     */
    public synchronized void playerLost(String playerName) {
        currentGame.pause();
        gamePaused = true;
        AudioPlayer.backgroundMediaPlayer.stop();

        AudioPlayer.winMediaPlayer.play();
        AudioPlayer.winMediaPlayer.seek(Duration.ZERO);

        ((Start) Start.getInstance()).activeDisabledButtons(false);

        int maxScore = Integer.MIN_VALUE;
        String winner = "";
        System.out.println(currentGame.getPlayersController().getPlayersNames
                ().size());
        Collection<String> playerNames = currentGame.getPlayersController()
                .getPlayersNames();
        for (String name : playerNames) {
            Player playerModel = currentGame.getPlayersController()
                    .getPlayerModel(name);
            if (name != null && name.equals(playerName)) {
                playerModel.setScore(playerModel.getScore() / 2);
            }
            if (maxScore < playerModel.getScore()) {
                maxScore = playerModel.getScore();
                winner = name;
            } else if (maxScore == playerModel.getScore()) {
                winner = null;
            }
        }
        winPane.setVisible(true);
        if (winner == null) {
            ((Label) winPane.getChildren().get(0)).setText("The Game Ended in"
                    + " A Draw With Score " + maxScore);
            logger.info("The Game Ended in"
                    + " A Draw With Score " + maxScore);

        } else {
            ((Label) winPane.getChildren().get(0)).setText("Player: "
                    + winner + " has won with score " + maxScore);
            logger.info("Player: " + winner + " has won with score " +
                    maxScore);
        }
        players.clear();
        resetGame();
    }

    /**
     * updates the score and the removes the last 3 shapes on the stack.
     * @param score      the new of the current scored-player.
     * @param playerName the name of the current player who won the points.
     * @param stick      {@link Stick} the stick which contains the new
     *                   explosion.
     */
    @Override
    public void update(int score, String playerName, Stick stick) {
        currentGame.getPlayersController().removeShapes(playerName, stick);
        currentGame.updateScore(score, playerName, stick);
    }

}