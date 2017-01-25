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
import controllers.input.keyboard.KeyboardEvent;
import controllers.level.PlatformBuilder;
import controllers.menus.MenuController;
import controllers.menus.Start;
import controllers.player.PlayersController;
import controllers.player.ScoreObserver;
import controllers.shape.ShapeBuilder;
import controllers.shape.ShapeController;
import controllers.shape.ShapeGenerator;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.GameMode;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.levels.LevelOne;
import models.players.Player;
import models.players.PlayerFactory;
import models.players.Stick;
import models.shapes.util.ShapePlatformPair;
import services.file.FileHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class GameController implements Initializable, ScoreObserver {
    private static GameController instance;
    private MenuController currentMenu;
    private PlayersController playersController;
    private GameBoard gameBoard;
    private ShapeGenerator shapeGenerator;
    private BooleanProperty newGameStarted;
    private Collection<ShapeController<? extends Node>> shapeControllers;
    private Map<KeyCode, Boolean> keyMap;
    private volatile boolean gamePaused = false;
    private ModelDataHolder modelDataHolder;
    private FileHandler handler;
    private Double currentX;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainGame;

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

        // Controllers
        currentMenu = Start.getInstance();
        gameBoard = GameBoard.getInstance();
        playersController = new PlayersController(mainGame);
        handler = new FileHandler();
        newGameStarted = new SimpleBooleanProperty(false);
        modelDataHolder = new ModelDataHolder();
        shapeControllers = new ArrayList<>();

        keyMap = new HashMap<>();
        keyMap.put(KeyCode.A, false);
        keyMap.put(KeyCode.D, false);
        keyMap.put(KeyCode.LEFT, false);
        keyMap.put(KeyCode.RIGHT, false);

        registerLevels();
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
    }

    public void registerLevels() {
        try {
            Class.forName("models.levels.LevelOne");
            Class.forName("models.levels.LevelTwo");
            Class.forName("models.levels.LevelThree");
            Class.forName("models.levels.LevelFour");
            Class.forName("models.levels.LevelFive");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentMenu(MenuController currentMenu) {
        this.currentMenu = currentMenu;
    }

    public MenuController getCurrentMenu() {
        return currentMenu;
    }

    public AnchorPane getMainGame() {
        return mainGame;
    }

    public AnchorPane getRootPane() {
        return rootPane;
    }

    public ModelDataHolder getModelDataHolder() {
        return modelDataHolder;
    }

    public void addShapeController(ShapeController<? extends Node>
                                           shapeController) {
        shapeControllers.add(shapeController);
    }

    public void removeShapeController(ShapeController<? extends Node>
                                              shapeController) {
        shapeControllers.remove(shapeController);
    }


    private synchronized void updatePlayers() {
        if (keyMap.get(KeyCode.A)) {
            playersController.moveLeft(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.KEYBOARD_SECONDARY));
        }
        if (keyMap.get(KeyCode.D)) {
            playersController.moveRight(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.KEYBOARD_SECONDARY));
        }
        if (keyMap.get(KeyCode.LEFT)) {
            playersController.moveLeft(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.KEYBOARD_PRIMARY));
        }
        if (keyMap.get(KeyCode.RIGHT)) {
            playersController.moveRight(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.KEYBOARD_PRIMARY));
        }
    }

    @FXML
    public synchronized void keyHandlerReleased(KeyEvent event) {
        keyMap.put(event.getCode(), false);
//        updatePlayers();
    }

    @FXML
    public synchronized void keyHandler(KeyEvent event) {
        keyMap.put(event.getCode(), true);
//        updatePlayers();
        switch (event.getCode()) {
            // KEYBOARD_PRIMARY
//            case LEFT:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType
// .KEYBOARD_PRIMARY);
//                    if (playerName != null) {
//                        playersController.moveLeft(playerName);
//                    }
//                }
//                break;
//            case RIGHT:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType
// .KEYBOARD_PRIMARY);
//                    if (playerName != null) {
//                        playersController.moveRight(playerName);
//                    }
//                }
//                break;
            case ESCAPE:
                if (newGameStarted.get()) {
                    if (currentMenu.isVisible()) {
                        continueGame();
                    } else {
                        pauseGame();
                    }
                }
                break;
//            // keyboard_two
//            case A:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType
// .KEYBOARD_SECONDARY);
//                    if (playerName != null) {
//                        playersController.moveLeft(playerName);
//                    }
//                }
//                break;
//            case D:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType
// .KEYBOARD_SECONDARY);
//                    if (playerName != null) {
//                        playersController.moveRight(playerName);
//                    }
//                }
//                break;
//            default:
//                break;
        }
    }


//    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType
// .KEYBOARD_PRIMARY)
//    public void primaryKeyboardHandler(KeyboardEvent keyboardEvent) {
//        Platform.runLater(() -> {
//            switch (keyboardEvent.getKeyboardCode()) {
//                case LEFT:
//                    playersController.moveLeft(PlayerFactory
//                            .getFactory().getPlayerNameWithController
// (InputType.KEYBOARD_PRIMARY));
//                    break;
//                case RIGHT:
//                    playersController.moveRight(PlayerFactory
//                            .getFactory().getPlayerNameWithController
// (InputType.KEYBOARD_PRIMARY));
//            }
//        });
//    }

    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType
            .KEYBOARD_SECONDARY)
    public void secondaryKeyboardHandler(KeyboardEvent keyboardEvent) {
        if (!mainGame.isVisible()) {
            return;
        }
        Platform.runLater(() -> {
            switch (keyboardEvent.getKeyboardCode()) {
                case A:
                    playersController.moveLeft(PlayerFactory
                            .getFactory().getPlayerNameWithController
                                    (InputType.KEYBOARD_SECONDARY));
                    break;
                case D:
                    playersController.moveRight(PlayerFactory
                            .getFactory().getPlayerNameWithController
                                    (InputType.KEYBOARD_SECONDARY));
                    break;
                case LEFT:
                    playersController.moveLeft(PlayerFactory
                            .getFactory().getPlayerNameWithController
                                    (InputType.KEYBOARD_PRIMARY));
                    break;
                case RIGHT:
                    playersController.moveRight(PlayerFactory
                            .getFactory().getPlayerNameWithController
                                    (InputType.KEYBOARD_PRIMARY));
            }
        });
    }


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
                        playersController.moveLeft(playerName1);
                    }
                } else if (event.getJoystickCode() == JoystickCode.RIGHT) {
                    if (playerName1 != null) {
                        playersController.moveRight(playerName1);
                    }
                }
            } else {
                if (event.getJoystickCode() == JoystickCode.LEFT) {
                    if (playerName2 != null) {
                        playersController.moveLeft(playerName2);
                    }
                } else if (event.getJoystickCode() == JoystickCode.RIGHT) {
                    if (playerName2 != null) {
                        playersController.moveRight(playerName2);
                    }
                }
            }
        });
    }

    @FXML
    public void onMousePressedHandler(MouseEvent event) {
        currentX = event.getSceneX();
    }

    @FXML
    public void onMouseDraggedHandler(MouseEvent event) {
        if (currentX > event.getSceneX()) {
            playersController.moveLeft(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.MOUSE));
        } else {
            playersController.moveLeft(PlayerFactory
                    .getFactory().getPlayerNameWithController
                            (InputType.MOUSE));
        }
    }

    public void saveGame(String name) {
        System.err.println(name);
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy HH,mm,ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String fileName = name + " - " + currentDate;
        this.handler.write(modelDataHolder, "." + File.separator + "save",
                fileName);
    }


    public double getStageWidth() {
        return mainGame.getWidth();
    }

    public void startGame(GameMode gameMode) {
        ((Start) Start.getInstance()).activeDisabledButtons();

        GameController.getInstance().getMainGame().setVisible(true);
        AudioPlayer.backgroundMediaPlayer.play();
        newGameStarted.set(true);
        switch (gameMode) {
            case NORMAL:
                resetGame();
                break;
            case TIME_ATTACK:
                break;
            case LEVEL:
                break;
            case SANDBOX:
                break;
        }
    }

    public void resetGame() {
        String path_0 = "src/views/clowns/clown_5/clown.fxml";
        String path_1 = "src/views/clowns/clown_6/clown.fxml";

        try {
            playersController.createPlayer(path_0, "player1", InputType
                    .KEYBOARD_PRIMARY);
            playersController.createPlayer(path_1, "player2", InputType
                    .KEYBOARD_SECONDARY);

            gameBoard.addPlayerPanel("player1");
            gameBoard.addPlayerPanel("player2");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ===========================
        try {
            Class.forName("models.shapes.PlateShape");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Level level = new
                LevelOne(rootPane.getLayoutX(),
                rootPane.getLayoutY(), rootPane.getLayoutX()
                + rootPane.getWidth(), rootPane.getLayoutY()
                + rootPane.getHeight());
        modelDataHolder.setActiveLevel(level);
        startNormalGame(level);
    }

    private void startNormalGame(Level level) {
        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : level.getPlatforms()) {
            mainGame.getChildren().add(builder.build(platform));
        }
        shapeGenerator = new ShapeGenerator(level, mainGame);

        startKeyboardListener();
//        ShapeGenerator<Rectangle> generator = new ShapeGenerator<>(
//                new LevelOne());
    }

    public synchronized void startKeyboardListener() {
        new Thread(() -> {
            while (!gamePaused) {
                Platform.runLater(this::updatePlayers);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startNewLoadGame(ModelDataHolder modelDataHolder) {
        shapeControllers = new ArrayList<>();
        this.modelDataHolder = modelDataHolder;
        try {
            for (Player player : modelDataHolder.getPlayers()) {
                playersController.createPlayer(player.getPlayerUrl(), player
                        .getName(), player.getInputType());
                gameBoard.addPlayerPanel(player.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ShapePlatformPair shapePlatformPair : modelDataHolder.getShapes
                ()) {
            switch (shapePlatformPair.getShape().getState()) {
                case MOVING_HORIZONTALLY:
                case FALLING:
                    Node shapeView = ShapeBuilder.getInstance().build
                            (shapePlatformPair.getShape());
                    new ShapeController<>(shapeView, shapePlatformPair
                            .getShape(), shapePlatformPair.getPlatform())
                            .startMoving();
                    mainGame.getChildren().add(shapeView);
                    break;
                case ON_THE_STACK:
                    System.out.println("ERRROROOROROROR");//TODO: LOG.
                    break;
                default:
                    break;
            }
        }
    }

    public synchronized boolean checkIntersection(
            ShapeController<? extends Node> shapeController) {
        if (playersController.checkIntersection(shapeController)) {
            shapeController.shapeFellOnTheStack();
            return true;
        }
        return false;
    }

    public void pauseGame() {
        gamePaused = true;
        currentMenu = Start.getInstance();
        currentMenu.setMenuVisible(true);
        currentMenu.requestFocus(0);
        mainGame.setVisible(false);
        for (ShapeController<? extends Node> shapeController :
                shapeControllers) {
            shapeController.gamePaused();
        }
        gameBoard.pause();
        shapeGenerator.pauseGenerator();
        AudioPlayer.backgroundMediaPlayer.pause();
    }


    public void continueGame() {
        gamePaused = false;
        currentMenu.setMenuVisible(false);
        mainGame.requestFocus();
        mainGame.setVisible(true);
        for (ShapeController<? extends Node> shapeController :
                shapeControllers) {
            shapeController.gameResumed();
        }
        gameBoard.resume();
        shapeGenerator.resumeGenerator();
        startKeyboardListener();
        AudioPlayer.backgroundMediaPlayer.play();
    }

    public void startLevel(String level) {
        //
    }

    @Override
    public void update(int score, String playerName, Stick stick) {
        playersController.removeShapes(playerName, stick);
        gameBoard.updateScore(score, playerName);
    }
}