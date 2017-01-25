package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.menus.options.Audio;
import controllers.menus.options.Input;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Options extends MenuController {
    private static Options instance;

    @FXML
    private AnchorPane optionsMenu;

    @FXML
    private VBox menu;


    public Options() {
        super();
        instance = this;
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
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
    }

    @Override
    void handle(String id) {
        optionsMenu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().setMenuVisible(true);
                updateCurrentMenu(Start.getInstance());
                break;
            case "audio":
                Audio.getInstance().setVisible(true);
                menu.setVisible(false);
                optionsMenu.setVisible(true);
                break;
            case "graphics":
                // TODO: 1/25/17
                break;
            case "input":
                Input.getInstance().setVisible(true);
                menu.setVisible(false);
                optionsMenu.setVisible(true);
                break;
            case "credits":
                // TODO: 1/25/17
                break;
        }
    }

    @Override
    protected VBox getMenu() {
        return menu;
    }

    @Override
    public void setMenuVisible(boolean visible) {
        optionsMenu.setVisible(visible);
        menu.setVisible(true);
        Audio.getInstance().setVisible(false);
        Input.getInstance().setVisible(false);
        updateCurrentMenu(this);
        this.requestFocus(0);
    }

    @Override
    public boolean isVisible() {
        return optionsMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
