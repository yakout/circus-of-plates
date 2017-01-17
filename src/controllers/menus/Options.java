package controllers.menus;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Options extends MenuController {
    private VBox menu;
    private static Options instance;

    public Options() {
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
        menu = optionsMenu;
    }


    @FXML
    private VBox optionsMenu;

    @Override
    void handle(String id) {
        switch (id) {
            case "back":
                menu.setVisible(false);
                Start.getInstance().getMenu().setVisible(true);
                updateCurrentMenu(Start.getInstance().getMenu());
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
