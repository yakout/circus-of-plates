package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.file.FileHandler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoadGame implements Initializable {
    private static LoadGame instance;
    private static final String SAVED_GAMES_PATH = "save";
    private FileHandler fileHandler;
    @FXML
    private VBox savedGames;
    @FXML
    private AnchorPane loadGamePane;

    private String selectedGame;

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
        fileHandler = new FileHandler();
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        String text = ((Button) event.getSource()).getText();
        switch (text) {
            case "LOAD":
                // todo: game controller
                Start.getInstance().setMenuVisible(true);
                loadGamePane.setVisible(false);
                break;
            case "CANCEL":
                Start.getInstance().setMenuVisible(true);
                loadGamePane.setVisible(false);
                break;
        }
    }

    private void updateSavedGames() {
        List<String> savedGames = fileHandler.getFileList(SAVED_GAMES_PATH);
        for(String gameName : savedGames) {
            addSavedGame(gameName);
            System.out.println(gameName);
        }
    }

    private void addSavedGame(String name) {
        Button button = new Button(name);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnMouseClicked(event -> selectedGame = ((Button) event.getSource()).getText());
        savedGames.getChildren().add(button);
    }

    public void setVisible(boolean isVisible) {
        loadGamePane.setVisible(isVisible);
        updateSavedGames();
    }
}
