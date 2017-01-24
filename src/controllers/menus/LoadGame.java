package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadGame implements Initializable {
    private static LoadGame instance;

    @FXML
    private AnchorPane savedGames;

    @FXML
    private AnchorPane loadGamePane; // // TODO: 12/29/16 package access

    public LoadGame() {
    }

    public static LoadGame getInstance() {
        return instance;
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
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        // menu.setVisible(false);
        switch (((Node)event.getSource()).getId()) {
            case "load":
                Start.getInstance().setMenuVisible(true);
//                 updateCurrentMenu(Start.getInstance());

                break;
            case "cancel":
                Start.getInstance().setMenuVisible(true);
                loadGamePane.setVisible(false);
                break;
        }
    }

    public AnchorPane getLoadGamePane() {
        return loadGamePane;
    }
}
