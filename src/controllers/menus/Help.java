package controllers.menus;

import controllers.input.joystick.Joystick;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class Help extends MenuController {

    private static Logger logger = LogManager.getLogger(Help.class);
    private VBox menu;
    private static Help instance;

    @FXML
    private VBox helpMenu;

    public Help() {
        super();
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
        instance = this;
        menu = helpMenu;
        Joystick.getInstance().registerClassForInputAction(getClass(),
                instance);
    }


    @Override
    protected void handle(String id) {
        menu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().setMenuVisible(true);
                updateCurrentMenu(Start.getInstance());
                logger.info("Menu is triggered to go back.");
                break;
            default:
                // not implemented yet
                break;
        }
    }

    @Override
    protected VBox getMenu() {
        return menu;
    }

    @Override
    public void setMenuVisible(boolean visible) {
        helpMenu.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return helpMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
