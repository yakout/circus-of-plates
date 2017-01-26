package controllers.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Yakout on 1/25/17.
 */
public class BackgroundChooser implements Initializable {

    private static Logger logger = LogManager.getLogger(PlayerChooser.class);
    private static BackgroundChooser instance;
    private String chosenBackground;

    @FXML
    AnchorPane anchor;
    @FXML
    VBox imagesHolder;
    @FXML
    HBox firstRow;
    @FXML
    Button choose;

    /**
     * default constructor BackgroundChooser class.
     */
    public BackgroundChooser() {

    }

    /**
     * Called on loading the corresponding fxml file.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        chosenBackground = "1";
    }

    /**
     * @return instance of this class.
     */
    public static BackgroundChooser getInstance() {
        return instance;
    }

    public void setVisible(final boolean visible) {
        anchor.setVisible(visible);
    }

    /**
     * Handles any mouse actions.
     * @param event the mouse event actioned.
     */
    @FXML
    private void mouseHandler(MouseEvent event) {
        chosenBackground = ((Node) event.getSource()).getId();
    }

    /**
     * Sets background corresponding to the view.
     */
    @FXML
    public void selectBackground() {
        anchor.setVisible(false);
        Options.getInstance().setMenuVisible(true);
        Options.getInstance().updateCurrentMenu(GameMode.getInstance());

        logger.info("First player has chosen clown_" + chosenBackground + ".");
    }

    /**
     * Selects input type that corresponds the actioned event.
     * @param event {@link ActionEvent} event bby the user.
     */
    @FXML
    private void selectInputType(ActionEvent event) {
        System.out.println(((Node) event.getSource()).getId());
        switch (((Node) event.getSource()).getId()) {
            default:
                break;
        }
        logger.debug("Player selected " + ((Node) event.getSource())
                .getId() + " input type.");
    }
}
