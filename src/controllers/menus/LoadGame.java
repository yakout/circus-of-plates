package controllers.menus;

import controllers.main.GameController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.file.FileHandler;
import services.file.SavedGameSet;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoadGame implements Initializable {

    private static Logger logger = LogManager.getLogger(LoadGame.class);
    private static LoadGame instance;
    private static final String SAVED_GAMES_PATH = "save";
    private FileHandler fileHandler;
    @FXML
    private AnchorPane loadGamePane;
    @FXML
    private VBox savedGames;

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

//        double width = ((AnchorPane) loadGamePane.getParent()).getPrefWidth(); //((ScrollPane) loadGamePane.getChildren().get(0)).getPrefWidth();
//        System.err.println(width);
//        ((ScrollPane) loadGamePane.getChildren().get(0)).setPrefWidth(width);
//        savedGames.setPrefWidth(width);
//        ((AnchorPane) savedGames.getParent()).setPrefWidth(width);
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        String text = ((Button) event.getSource()).getText();
        switch (text) {
            case "LOAD":
                // todo: game controller
                GameController.getInstance().startNewLoadGame(fileHandler
                        .read(SAVED_GAMES_PATH, selectedGame));
//                Start.getInstance().setMenuVisible(true);
//                loadGamePane.setVisible(false);
                logger.info("Game is loaded successfully");
                break;
            case "CANCEL":
                Start.getInstance().setMenuVisible(true);
                loadGamePane.setVisible(false);
                break;
        }
    }

    private void updateSavedGames() {
        List<String> savedGames = new ArrayList<>(); //fileHandler.getFileList(SAVED_GAMES_PATH);

        Iterator iterator = new SavedGameSet().iterator();

        while (iterator.hasNext()) {
            savedGames.add((String) iterator.next());
        }

        if (savedGames.isEmpty()) {
            return;
        }
        selectedGame = savedGames.get(0); // by default
        this.savedGames.getChildren().clear();
        savedGames.forEach(this::addSavedGame);
    }

    private void addSavedGame(String name) {

        String[] nameComponents = name.split("-");
        String saveName = nameComponents[0].trim();
        String saveDate = transformDate(nameComponents[1].trim());

        Button button = new Button(saveDate + " - " + saveName);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setId("savedGameButton");
        button.setPrefWidth(500.0);

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
