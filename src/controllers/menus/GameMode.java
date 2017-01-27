package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.levels.util.LevelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMode extends MenuController {

    private static Logger logger = LogManager.getLogger(GameMode.class);
    private static GameMode instance;
    private final String LOAD_GAME_PANE_PATH
            = "views/menus/ChoosePlayer/choosePlayer.fxml";
    private final int LEVEL_INDEX = 6;
    @FXML
    private VBox menu;

    @FXML
    private AnchorPane gameModeMenu;

    @FXML
    private AnchorPane chooseLevel;
    private ChoiceBox<String> choiceBox;

    /**
     * Default constructor.
     */
    public GameMode() {
        super();
    }

    /**
     * Gets the instace of that class.
     * @return instance.
     */
    public static MenuController getInstance() {
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
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
        choiceBox = (ChoiceBox<String>) chooseLevel.getChildren().get(0);

        // // TODO: 1/25/17 get the levels from model
        for (Integer levelNumber : LevelFactory.getInstance()
                .getRegisteredLevels()) {
            choiceBox.getItems().add("Level " + levelNumber);
        }
        if (!choiceBox.getItems().isEmpty()) {
            choiceBox.setValue(choiceBox.getItems().get(0));
        }
    }

    /**
     * Handles the menu of given id.
     * @param id
     */
    @Override
    protected void handle(String id) {
        gameModeMenu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().setMenuVisible(true);
                updateCurrentMenu(Start.getInstance());
                break;
            case "timeAttack":
                GameController.getInstance().startGame(models.GameMode
                        .TIME_ATTACK);
                break;
            case "normal":
                GameController.getInstance().startGame(models.GameMode.NORMAL);
                break;
            case "chooseLevel":
                gameModeMenu.setVisible(true);
                chooseLevel.setVisible(true);
                menu.setVisible(false);
                logger.info("Level is set successfully");
                break;
            case "choosePlayer":
                if (PlayerChooser.getInstance() == null) {
                    loadPlayerChooser();
                }
                gameModeMenu.setVisible(true);
                menu.setVisible(false);
                PlayerChooser.getInstance().setVisible(true);
                break;
            case "doneChoosingLevel":
                GameController.getInstance().setCurrentGameLevel(
                        Integer.parseInt(choiceBox.getValue().substring
                                (LEVEL_INDEX)));
                setMenuVisible(true);
                break;
            default:
                break;
        }
    }

    private void loadPlayerChooser() {
        URL url;
        try {
            url = ClassLoader.getSystemResource(LOAD_GAME_PANE_PATH);
            System.out.println(LOAD_GAME_PANE_PATH);
            AnchorPane playerChooser = FXMLLoader.load(url);
            GameController.getInstance().getRootPane().getChildren().add
                    (playerChooser);

            double width = GameController.getInstance().getStageWidth();
            AnchorPane.setLeftAnchor(playerChooser,
                    width / 2 - playerChooser.getPrefWidth() / 2);
            AnchorPane.setTopAnchor(playerChooser, 50.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected VBox getMenu() {
        return menu;
    }

    /**
     * sets the curren menu visible.
     * @param visible states where visible or not.
     */
    @Override
    public void setMenuVisible(boolean visible) {
        gameModeMenu.setVisible(visible);
        menu.setVisible(visible);
        requestFocus(0);
        chooseLevel.setVisible(false);
    }

    /**
     * Checks whether the menu is visible or not.
     * @return whether true or false.
     */
    @Override
    public boolean isVisible() {
        return gameModeMenu.isVisible();
    }
}
