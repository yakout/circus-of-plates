package controllers.menus;

import controllers.AudioPlayer;
import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMode extends MenuController {
    @FXML
    private VBox menu;

    @FXML
    private AnchorPane gameModeMenu;

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
        Joystick.getInstance().registerClassForInputAction(getClass(), instance);
    }

    @Override
    void handle(String id) {
        gameModeMenu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().getMenu().setVisible(true);
                updateCurrentMenu(Start.getInstance());
                break;
            case "sandBox":
//                 TODO: 12/25/16 save the current mode and go to player menu
                break;
            case "timeAttack":
//                 TODO: 12/25/16 save the current mode and go to player menu
                break;
            case "normal":
                GameController.getInstance().startGame(models.GameMode.NORMAL);
                break;
            case "chooseLevel":
                gameModeMenu.setVisible(true);
                menu.setVisible(false);
                break;
            case "choosePlayer":
                gameModeMenu.setVisible(true);
                menu.setVisible(false);
                ChoosePlayer.getInstance().setVisible(true);
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
        gameModeMenu.setVisible(visible);
        menu.setVisible(visible);
        ChoosePlayer.getInstance().setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return gameModeMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
