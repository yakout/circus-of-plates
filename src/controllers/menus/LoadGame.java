package controllers.menus;

import controllers.main.GameController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.file.FileHandler;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        fileHandler = FileHandler.getInstance();
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        String text = ((Button) event.getSource()).getText();
        switch (text) {
            case "LOAD":
                // todo: game controller
                GameController.getInstance().startNewLoadGame(fileHandler
                        .read(SAVED_GAMES_PATH, selectedGame));
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
        if (savedGames == null) return;
        for (String gameName : savedGames) {
            addSavedGame(gameName);
        }
    }

    private void addSavedGame(String name) {
        savedGames.getChildren().clear();

        String[] nameComponents = name.split("-");
        String saveName = nameComponents[0].trim();
        String saveDate = transformDate(nameComponents[1].trim());

        Button button = new Button(saveDate + " - " + saveName);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String text = ((Button) event.getSource()).getText();
                String[] nameComponents = text.split("-");
                String gameName = nameComponents[1].trim();
                String saveDate = reverseTransformDate(nameComponents[0].trim
                        ());
                selectedGame = gameName + " - " + saveDate;
            }
        });
        savedGames.getChildren().add(button);
    }

    private String transformDate(String saveDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy HH,mm,ss");
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date actualSaveDate = new Date();
        try {
            actualSaveDate = dateFormat.parse(saveDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat2.format(actualSaveDate);
    }

    private String reverseTransformDate(String saveDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy HH,mm,ss");
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date actualSaveDate = new Date();
        try {
            actualSaveDate = dateFormat2.parse(saveDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(actualSaveDate);
    }

    public void setVisible(boolean isVisible) {
        loadGamePane.setVisible(isVisible);
        updateSavedGames();
    }
}
