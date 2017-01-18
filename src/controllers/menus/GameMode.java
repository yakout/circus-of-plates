package controllers.menus;

import controllers.input.joystick.Joystick;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMode extends MenuController {
    private VBox menu;
    private static GameMode instance;

    public GameMode() {
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
        menu = gameModeMenu;
        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
    }

    @FXML
    private VBox gameModeMenu;

    @Override
    void handle(String id) {
        menu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().getMenu().setVisible(true);
                updateCurrentMenu(Start.getInstance());
                break;
            case "sandBox":
                // TODO: 12/25/16 save the current mode and go to player menu
                break;
            case "timeAttack":
                // TODO: 12/25/16 save the current mode and go to player menu
                break;
            case "normal":
                // TODO: 12/25/16 save the current mode and go to player menu
                break;
            case "chooseLevel":
                // TODO: 12/25/16 save the current mode and go to player menu
                break;
        }
    }

    @Override
    public VBox getMenu() {
        return menu;
    }

    public static MenuController getInstance() {
        return instance;
    }
}
