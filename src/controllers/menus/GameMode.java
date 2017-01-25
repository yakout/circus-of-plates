package controllers.menus;

import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMode extends MenuController {
    private static GameMode instance;
    private final String LOAD_GAME_PANE_PATH = "src/views/menus/ChoosePlayer/ChoosePlayer.fxml";
    private final int LEVEL_INDEX = 6;
    @FXML
    private VBox menu;

    @FXML
    private AnchorPane gameModeMenu;

    @FXML
    private AnchorPane chooseLevel;
    private ChoiceBox<String> choiceBox;


    public GameMode() {
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
        choiceBox = (ChoiceBox<String>) chooseLevel.getChildren().get(0);

        // // TODO: 1/25/17 get the levels from model
        choiceBox.setItems(FXCollections.observableArrayList(
                "Level 1", "Level 2", "Level 3", "Level 4", "Level 5"));

        choiceBox.setValue("Level 1");
    }

    @Override
    void handle(String id) {
        gameModeMenu.setVisible(false);
        switch (id) {
            case "back":
                Start.getInstance().setMenuVisible(true);
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
                chooseLevel.setVisible(true);
                menu.setVisible(false);
                break;
            case "choosePlayer":
                if (PlayerChooser.getInstance() == null) {
                    loadPlayerChooser();
                }
                gameModeMenu.setVisible(true);
                menu.setVisible(false);
                PlayerChooser.getInstance().setVisible(true);
                break;
            case "doneChoosingLevel":
                GameController.getInstance().getCurrentGame().setLevel(
                        Integer.parseInt(choiceBox.getValue().substring(LEVEL_INDEX)));
                setMenuVisible(true);
                break;
            default:
                break;
        }
    }

    private void loadPlayerChooser() {
        URL url;
        try {
            url = new File(LOAD_GAME_PANE_PATH).toURI().toURL();
            AnchorPane playerChooser = FXMLLoader.load(url);
            GameController.getInstance().getRootPane().getChildren().add(playerChooser);

            double width = GameController.getInstance().getStageWidth();
            AnchorPane.setLeftAnchor(playerChooser,
                    width / 2 - playerChooser.getPrefWidth() / 2);
            AnchorPane.setTopAnchor(playerChooser, 50.0);
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

    @Override
    protected VBox getMenu() {
        return menu;
    }

    @Override
    public void setMenuVisible(boolean visible) {
        gameModeMenu.setVisible(visible);
        menu.setVisible(visible);
        requestFocus(0);
        chooseLevel.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return gameModeMenu.isVisible();
    }

    public static MenuController getInstance() {
        return instance;
    }
}
