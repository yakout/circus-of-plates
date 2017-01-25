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

    public BackgroundChooser() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        chosenBackground = "1";
    }

    public static BackgroundChooser getInstance() {
        return instance;
    }

    public void setVisible(final boolean visible) {
        anchor.setVisible(visible);
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        chosenBackground = ((Node) event.getSource()).getId();
    }

    @FXML
    public void selectBackground() {
        anchor.setVisible(false);
        Options.getInstance().setMenuVisible(true);
        Options.getInstance().updateCurrentMenu(GameMode.getInstance());

        logger.info("First player has chosen clown_" + chosenBackground + ".");
    }


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
