package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class Start extends MenuController {

    private static Logger logger = LogManager.getLogger(Start.class);
    private BooleanProperty newGameIsDisabled;
    private String fileNameRegex; // TODO: 1/24/17

    @FXML
    private AnchorPane startMenu;

    @FXML
    private VBox menu;

    @FXML
    private AnchorPane saveGamePane;

    @FXML
    private TextField gameName;

    private static Start instance;

    public Start() {
        super();
        instance = this;
//        logger.debug("Start menu is loaded successfully.");
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the
     *                  root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newGameIsDisabled = new SimpleBooleanProperty(true);
        getButton(1).disableProperty().bindBidirectional(newGameIsDisabled);
        getButton(2).disableProperty().bindBidirectional(newGameIsDisabled);
        gameName.textProperty().addListener(
                new ChangeListener<String>() {

                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("[a-zA-Z\\d ]*")) {
                            gameName.setText(newValue.replaceAll("[^a-zA-Z\\d "
                                    + "]", ""));
                        }
                    }
                });
        requestFocus(0);
        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
    }


    /**
     * Handles the menu of given id.
     * @param id states tha id of the chosen button.
     */
    @Override
    void handle(String id) {
        startMenu.setVisible(false);
        switch (id) {
            case "newGame":
                GameMode.getInstance().setMenuVisible(true);
                updateCurrentMenu(GameMode.getInstance());
                logger.debug("New game menu is loaded.");
                break;
            case "continue":
                GameController.getInstance().continueGame();
                logger.debug("Loaded game is started.");
                break;
            case "loadGame":
                startMenu.setVisible(true);
                menu.setVisible(false);
                LoadGame.getInstance().setVisible(true);
                logger.debug("Loaded game menu is loaded.");
                break;
            case "saveGame":
                startMenu.setVisible(true);
                menu.setVisible(false);
                saveGamePane.setVisible(true);
                logger.debug("Save menu is loaded");
                break;
            case "options":
                Options.getInstance().setMenuVisible(true);
                updateCurrentMenu(Options.getInstance());
                logger.debug("Options menus is loaded.");
                break;
            case "help":
                Help.getInstance().setMenuVisible(true);
                updateCurrentMenu(Help.getInstance());
                logger.debug("Help menu is loaded.");
                break;
            case "exit":
                Platform.exit();
                System.exit(0);
                logger.debug("Game is exited.");
                break;
            case "save":
                GameController.getInstance().saveGame(gameName.getText());
                hideSaveGamePanel();
                logger.info("Game is saved successfully.");
                break;
            case "cancelSave":
                hideSaveGamePanel();
                break;
            default:
                break;
        }
    }

    private void hideSaveGamePanel() {
        saveGamePane.setVisible(false);
        menu.setVisible(true);
        this.requestFocus(0);
        startMenu.setVisible(true);
    }

    /**
     * Gets the menu of the start.
     * @return {@link VBox} the Vboox that coontains the menu.
     */
    @Override
    protected VBox getMenu() {
        return menu;
    }

    /**
     * Sets the visibility of the menu.
     * @param visible whether the menu is visible or not.
     */
    @Override
    public void setMenuVisible(boolean visible) {
        startMenu.setVisible(visible);

        menu.setVisible(visible);
        this.requestFocus(0);

        saveGamePane.setVisible(false);
        LoadGame.getInstance().setVisible(false);
    }

    /**
     * Sets the buttons to discactive mode.
     * @param active whether active or not.
     */
    public void activeDisabledButtons(boolean active) {
        this.newGameIsDisabled.set(!active);
        this.newGameIsDisabled.set(!active);
    }

    /**
     * Asks whether visible or not.
     * @return visibilty of this menu.
     */
    @Override
    public boolean isVisible() {
        return startMenu.isVisible();
    }

    /**
     * Gets the instance of the Start menu.
     * @return the instance of start menu.
     */
    public static MenuController getInstance() {
        return instance;
    }
}
