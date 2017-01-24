package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Start extends MenuController {
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
        newGameIsDisabled = new SimpleBooleanProperty(true);
        getButton(1).disableProperty().bindBidirectional(newGameIsDisabled);
        getButton(2).disableProperty().bindBidirectional(newGameIsDisabled);

        requestFocus(0);
        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
    }


    @Override
    void handle(String id) {
        startMenu.setVisible(false);
        switch (id) {
            case "newGame":
                GameMode.getInstance().setMenuVisible(true);
                updateCurrentMenu(GameMode.getInstance());
                break;
            case "continue":
                 GameController.getInstance().continueGame();
                break;
            case "loadGame":
                LoadGame.getInstance().setMenuVisible(true);
                updateCurrentMenu(LoadGame.getInstance());
                break;
            case "saveGame":
                startMenu.setVisible(true);
                menu.setVisible(false);
                saveGamePane.setVisible(true);
                //
                break;
            case "options":
                Options.getInstance().setMenuVisible(true);
                updateCurrentMenu(Options.getInstance());
                break;
            case "help":
                Help.getInstance().setMenuVisible(true);
                updateCurrentMenu(Help.getInstance());
                break;
            case "exit":
                Platform.exit();
                System.exit(0);
                break;
            case "save":
                GameController.getInstance().saveGame(gameName.getText());
                hideSaveGamePanel();
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

    @Override
    protected VBox getMenu() {
        return menu;
    }

    @Override
    public void setMenuVisible(boolean visible) {
        startMenu.setVisible(visible);
        menu.setVisible(visible);
        saveGamePane.setVisible(false);
    }

    public void activeDisabledButtons() {
        this.newGameIsDisabled.set(false);
        this.newGameIsDisabled.set(false);
    }

    @Override
    public boolean isVisible() {
        return startMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
