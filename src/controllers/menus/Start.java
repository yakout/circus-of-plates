package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Start extends MenuController {
    private VBox menu;
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
        menu = startMenu;
        requestFocus(0);
        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
    }

    @FXML
    private VBox startMenu;

    @Override
    void handle(String id) {
        startMenu.setVisible(false);
        switch (id) {
            case "newGame":
                GameMode.getInstance().setMenuVisible(true);
                updateCurrentMenu(GameMode.getInstance());
                break;
            case "continue":
                GameController.getInstance().getMainGame().setVisible(true);
                break;
            case "loadGame":
                LoadGame.getInstance().setMenuVisible(true);
                updateCurrentMenu(LoadGame.getInstance());
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
            default:
                break;
        }
    }

    @Override
    protected VBox getMenu() {
        return menu;
    }

    @Override
    public void setMenuVisible(boolean visible) {
        startMenu.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return startMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
