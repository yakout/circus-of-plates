package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import controllers.menus.options.Audio;
import controllers.menus.options.Input;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Options extends MenuController {

    private static Logger logger = LogManager.getLogger(Options.class);
    private static Options instance;
    private final String BACKGROUND_CHOOSER_PANE_PATH = "src/views/menus/" +
            "options/graphics/ChooseBackground/ChooseBackground.fxml";

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
                logger.info("Setting audio in settings.");
                break;
            case "graphics":
                if (BackgroundChooser.getInstance() == null) {
                    loadBackgroundChooser();
                }
                optionsMenu.setVisible(true);
                menu.setVisible(false);
                BackgroundChooser.getInstance().setVisible(true);
                logger.info("Setting graphics resolutions in settings.");
                break;
            case "input":
                Input.getInstance().setVisible(true);
                menu.setVisible(false);
                optionsMenu.setVisible(true);
                logger.info("Setting input player controllers.");
                break;
            case "credits":
                // TODO: 1/25/17
                logger.info("Game credits is triggerd.");
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

    private void loadBackgroundChooser() {
        URL url;
        try {
            url = new File(BACKGROUND_CHOOSER_PANE_PATH).toURI().toURL();
            AnchorPane backgroundChooser = FXMLLoader.load(url);
            GameController.getInstance().getRootPane().getChildren().add(backgroundChooser);

            double width = GameController.getInstance().getStageWidth();
            AnchorPane.setLeftAnchor(backgroundChooser,
                    width / 2 - backgroundChooser.getPrefWidth() / 2);
            AnchorPane.setTopAnchor(backgroundChooser, 100.0);
//=======
//            Node playerChooser = FXMLLoader.load(url);
//            GameController.getInstance().getRootPane().getChildren().add
//                    (playerChooser);
//            AnchorPane.setBottomAnchor(playerChooser, 0.0);
//            AnchorPane.setLeftAnchor(playerChooser, GameController
//                    .getInstance().getRootPane().getWidth() / 4.0);
//            AnchorPane.setRightAnchor(playerChooser, 0.0);
//            AnchorPane.setTopAnchor(playerChooser, 0.0);
//>>>>>>> 601ed58eb293636bbc1f947648b08c4676107659
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MenuController getInstance() {
        return instance;
    }
}
