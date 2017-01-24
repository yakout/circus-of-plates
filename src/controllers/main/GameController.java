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
import controllers.input.keyboard.Keyboard;
import controllers.input.keyboard.KeyboardEvent;
import controllers.level.PlatformBuilder;
import controllers.menus.MenuController;
import controllers.menus.Start;
import controllers.player.PlayersController;
import controllers.player.ScoreObserver;
import controllers.shape.ShapeController;
import controllers.shape.ShapeGenerator;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.GameMode;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.levels.LevelOne;
import models.players.PlayerFactory;
import models.players.Stick;

import services.file.FileHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;


public class GameController implements Initializable, ScoreObserver {
    private static GameController instance;
    private MenuController currentMenu;
    private PlayersController playersController;
    private GameBoard gameBoard;
    private ShapeGenerator shapeGenerator;
    private BooleanProperty newGameStarted;
    private Collection<ShapeController<? extends Node>> shapeControllers;

    // TODO: 1/19/17 plate Controller

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainGame;
    private ModelDataHolder modelDataHolder;
    private FileHandler handler;
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
        registerLevels();
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
        Keyboard.getInstance().registerClassForInputAction(getClass(),
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

    @FXML
    public void keyHandler(KeyEvent event) {
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
        if (!mainGame.isVisible()) return;
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

    public double getStageWidth() {
        return mainGame.getWidth();
    }


    public void startGame(GameMode gameMode) {
        ((Start) Start.getInstance()).setContinueButtonDisabled(false);
        GameController.getInstance().getMainGame().setVisible(true);
        AudioPlayer.backgroundMediaPlayer.play();
        newGameStarted.set(true);
        switch (gameMode) {
            case NORMAL:
                startNormalGame();
                break;
            case TIME_ATTACK:
                break;
            case LEVEL:
                break;
            case SANDBOX:
                break;
        }
    }

    private void startNormalGame() {
        String path_0 = "src/views/clowns/clown_5/clown.fxml";
        String path_1 = "src/views/clowns/clown_6/clown.fxml";

        try {
            playersController.createPlayer(path_0, "player1", InputType.KEYBOARD_PRIMARY);
            playersController.createPlayer(path_1, "player2", InputType.KEYBOARD_SECONDARY);

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
        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : level.getPlatforms()) {
            mainGame.getChildren().add(builder.build(platform));
        }
        System.out.println(level.getSupportedShapes().size());
        shapeGenerator= new ShapeGenerator(level, mainGame);

//        ShapeGenerator<Rectangle> generator = new ShapeGenerator<>(
//                new LevelOne());
    }


    // TODO: Mouse handler
    private Double currentX;

    public synchronized boolean checkIntersection(
            ShapeController<? extends Node> shapeController) {
        if (playersController.checkIntersection(shapeController)) {
            shapeController.shapeFellOnTheStack();
            return true;
        }
        return false;
    }

    @FXML
    public void mouseHandler(MouseEvent event) {
        if (currentX == null) {
            currentX = event.getX();
        } else {
            if (currentX > event.getX()) {
                // rect.setLayoutX(Math.max(rect.getLayoutX() - CLOWNSPEED,
                // -350 + rect.getWidth() / 2.0));
            } else {
                // rect.setLayoutX(Math.min(rect.getLayoutX() + CLOWNSPEED,
                // 350 - rect.getWidth() / 2.0));
            }
        }
    }

    public void pauseGame() {
        currentMenu = Start.getInstance();
        currentMenu.setMenuVisible(true);
        currentMenu.requestFocus(0);
        mainGame.setVisible(false);
        for (ShapeController<? extends Node> shapeController :
                shapeControllers) {
            shapeController.gamePaused();
        }
        gameBoard.pause();
        playersController.pause();
        shapeGenerator.pauseGenerator();
        AudioPlayer.backgroundMediaPlayer.pause();
    }

    public void continueGame() {
//        if (!newGameStarted.get()) return;
        currentMenu.setMenuVisible(false);
        mainGame.requestFocus();
        mainGame.setVisible(true);
        for (ShapeController<? extends Node> shapeController :
                shapeControllers) {
            shapeController.gameResumed();
        }
        gameBoard.resume();
        playersController.resume();
        shapeGenerator.resumeGenerator();
        AudioPlayer.backgroundMediaPlayer.play();
    }

    @Override
    public void update(int score, String playerName, Stick stick) {
        //TODO:- UPDATE THE SCORING LABEL.

        playersController.removeShapes(playerName, stick);
        gameBoard.updateScore(score, playerName);
    }

}