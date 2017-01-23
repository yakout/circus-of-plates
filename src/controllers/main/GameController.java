package controllers.main;

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
import controllers.player.PlayerController;
import controllers.shape.ShapeGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.GameMode;
import models.levels.Level;
import models.levels.LevelOne;
import models.players.PlayerFactory;


public class GameController implements Initializable {
    private static GameController instance;
    private MenuController currentMenu;
    private PlayerController playerController;
    // TODO: 1/19/17 plate Controller

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainGame;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controllers
        currentMenu = Start.getInstance();
        playerController = new PlayerController();


        instance  = this;

        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
        Keyboard.getInstance().registerClassForInputAction(getClass(), instance);
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


    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            // KEYBOARD_PRIMARY
//            case LEFT:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType.KEYBOARD_PRIMARY);
//                    if (playerName != null) {
//                        playerController.moveLeft(playerName);
//                    }
//                }
//                break;
//            case RIGHT:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType.KEYBOARD_PRIMARY);
//                    if (playerName != null) {
//                        playerController.moveRight(playerName);
//                    }
//                }
//                break;
            case ESCAPE:
                Platform.runLater(() -> {
                    if (currentMenu.isVisible()) {
                        currentMenu.setMenuVisible(false);
                        mainGame.requestFocus();
                        mainGame.setVisible(true);
                    } else {
                        currentMenu = Start.getInstance();
                        currentMenu.setMenuVisible(true);
                        currentMenu.requestFocus(0);
                        mainGame.setVisible(false);
                    }
                });
                break;
//            // keyboard_two
//            case A:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType.KEYBOARD_SECONDARY);
//                    if (playerName != null) {
//                        playerController.moveLeft(playerName);
//                    }
//                }
//                break;
//            case D:
//                if (mainGame.isVisible()) {
//                    String playerName = PlayerFactory.getFactory()
//                            .getPlayerNameWithController(InputType.KEYBOARD_SECONDARY);
//                    if (playerName != null) {
//                        playerController.moveRight(playerName);
//                    }
//                }
//                break;
//            default:
//                break;
        }
    }


//    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType.KEYBOARD_PRIMARY)
//    public void primaryKeyboardHandler(KeyboardEvent keyboardEvent) {
//        Platform.runLater(() -> {
//            switch (keyboardEvent.getKeyboardCode()) {
//                case LEFT:
//                    playerController.moveLeft(PlayerFactory
//                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_PRIMARY));
//                    break;
//                case RIGHT:
//                    playerController.moveRight(PlayerFactory
//                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_PRIMARY));
//            }
//        });
//    }

    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType.KEYBOARD_SECONDARY)
    public void secondaryKeyboardHandler(KeyboardEvent keyboardEvent) {
        Platform.runLater(() -> {
            switch (keyboardEvent.getKeyboardCode()) {
                case A:
                    playerController.moveLeft(PlayerFactory
                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_SECONDARY));
                    break;
                case D:
                    playerController.moveRight(PlayerFactory
                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_SECONDARY));
                    break;
                case LEFT:
                    playerController.moveLeft(PlayerFactory
                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_PRIMARY));
                    break;
                case RIGHT:
                    playerController.moveRight(PlayerFactory
                            .getFactory().getPlayerNameWithController(InputType.KEYBOARD_PRIMARY));
            }
        });
    }


    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType.JOYSTICK)
    public void performJoystickAction(JoystickEvent event) {
        String playerName2 = PlayerFactory.getFactory().getPlayerNameWithController(InputType.JOYSTICK_SECONDARY);
        String playerName1 = PlayerFactory.getFactory().getPlayerNameWithController(InputType.JOYSTICK_PRIMARY);

        Platform.runLater(() -> {
            if (event.getJoystickType() == JoystickType.PRIMARY) {
                if (event.getJoystickCode() == JoystickCode.LEFT) {
                    if (playerName1 != null) {
                        playerController.moveLeft(playerName1);
                    }
                } else if (event.getJoystickCode() == JoystickCode.RIGHT) {
                    if (playerName1 != null) {
                        playerController.moveRight(playerName1);
                    }
                }
            } else {
                if (event.getJoystickCode() == JoystickCode.LEFT) {
                    if (playerName2 != null) {
                        playerController.moveLeft(playerName2);
                    }
                } else if (event.getJoystickCode() == JoystickCode.RIGHT) {
                    if (playerName2 != null) {
                        playerController.moveRight(playerName2);
                    }
                }
            }
        });
    }

    public double getStageWidth() {
        return mainGame.getWidth();
    }


    public void startGame(GameMode gameMode) {
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
        String path_1_ = "src/views/clowns/clown_6/clown.fxml";

        try {
            Node player1 = playerController.createPlayer(path_0, "player1", InputType.KEYBOARD_PRIMARY);
            Node player2 = playerController.createPlayer(path_1_, "player2", InputType.KEYBOARD_SECONDARY);

            mainGame.getChildren().add(player2);
            mainGame.getChildren().add(player1);

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
        PlatformBuilder builder = new PlatformBuilder();
        for (models.Platform platform : level.getPlatforms()) {
            mainGame.getChildren().add(builder.build(platform));
        }
        System.out.println(level.getSupportedShapes().size());
        ShapeGenerator generator
                = new ShapeGenerator(level, mainGame);

//        ShapeGenerator<Rectangle> generator = new ShapeGenerator<>(
//                new LevelOne());
    }


    // TODO: Mouse handler
    private Double currentX;
    @FXML
    public void mouseHandler(MouseEvent event) {
        if (currentX == null) {
            currentX = event.getX();
        } else {
            if (currentX > event.getX()) {
                // rect.setLayoutX(Math.max(rect.getLayoutX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
            } else {
                // rect.setLayoutX(Math.min(rect.getLayoutX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
            }
        }
    }
}

