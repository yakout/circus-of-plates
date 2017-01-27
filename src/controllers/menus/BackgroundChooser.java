package controllers.menus;

import controllers.main.GameController;
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
    private final String BACKGROUND_PATH = "assets/images/backgrounds/background_";
    private final String BACKGROUND_IMAGE_STYLE = "-fx-background-image:" +
            " url(\"";// + BACKGROUND_PATH;
    private final String BACKGROUND_EXTENSION = ".png";
    private final String OTHER_BACKGROUND_STYLE = "-fx-background-repeat: " +
            "no-repeat;\n-fx-background-size: stretch;";

    @FXML
    private AnchorPane anchor;
    @FXML
    private VBox imagesHolder;
    @FXML
    private HBox firstRow;
    @FXML
    private Button choose;

    /**
     * @return instance of this class.
     */
    public static BackgroundChooser getInstance() {
        return instance;
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

        String backgroundPath = ClassLoader.getSystemResource(BACKGROUND_PATH + chosenBackground
                + BACKGROUND_EXTENSION).toString();
        GameController.getInstance().getRootPane()
                .setStyle(BACKGROUND_IMAGE_STYLE
                        + backgroundPath + "\");"
                        + OTHER_BACKGROUND_STYLE);
        logger.info("background changed to background_" + chosenBackground + ".");
    }
}
