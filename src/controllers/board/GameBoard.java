package controllers.board;

import controllers.main.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmedyakout on 1/23/17.
 */
public class GameBoard implements Initializable {

    private static Logger logger = LogManager.getLogger(GameBoard.class);
    private static GameBoard instance;
    private final String SCORE_PANEL_PATH = "src/views/board/scorePanel.fxml";

    @FXML
    private HBox board;

    @FXML
    private Label counter;

    @FXML
    private VBox leftPanel;

    @FXML
    private VBox rightPanel;


    private Timeline timeline;
    private IntegerProperty timeSeconds;
    private int GAME_TIME = 100;
    private int gameTime;
    /**
     * Called to show the game view.
     * @return returns the instance of this class.
     */
    public static GameBoard getInstance() {
        return instance;
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
        instance = this;
        timeSeconds = new SimpleIntegerProperty();
        timeline = new Timeline();
        gameTime = GAME_TIME;
        counter.setWrapText(true);
    }


    public void initializeGameTimer() {
        timeline = new Timeline();
        this.counter.textProperty().bind(timeSeconds.asString());
        this.counter.setTextFill(Color.RED);
        this.counter.setStyle("-fx-font-size: 4em;");
        this.counter.setVisible(true);
        this.timeSeconds.set(gameTime);
        this.timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameController.getInstance().playerLost(null);
            }
        });
        this.timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(gameTime + 1),
                        new KeyValue(timeSeconds, 0)));
        this.timeline.play();
        logger.info("Timer started counting");
    }

    /**
     * Adds the players-score panel.
     * @param playerName the name of player in order to set his score.
     * @throws IOException if not found such a file.
     */
    public void addPlayerPanel(String playerName) {
        URL url = null;
        AnchorPane scorePanel = null;
        try {
            url = new File(SCORE_PANEL_PATH).toURI().toURL();
            scorePanel = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Label) scorePanel.getChildren().get(0)).setText(playerName);

        if (leftPanel.getChildren().size() < rightPanel.getChildren().size()) {
            leftPanel.getChildren().add(scorePanel);
        } else {
            rightPanel.getChildren().add(scorePanel);
        }
        logger.info("Scores of players are shown.");
    }

    /**
     * Responsible for pausing the game.
     */
    public void pause() {
        timeline.pause();
        logger.info("Timer is paused.");
    }

    /**
     * Responsible for resuming the game.
     */
    public void resume() {
        timeline.play();
        logger.info("Timer is resumed.");
    }

    /**
     * Responsible for resetting the game.
     */
    public void reset() {
        leftPanel.getChildren().clear();
        rightPanel.getChildren().clear();
        counter.setVisible(false);
        GameController.getInstance().getMainGame().getChildren().remove(board);
        GameController.getInstance().getMainGame().getChildren().add(board);
//        initializeGameTimer();
    }

    /**
     * Updates scores of players on getting Points.
     * @param score the value of score to be incremented to the initial score.
     * @param playerName the name of the current player.
     */
    public void updateScore(int score, String playerName) {
        for (Node node : leftPanel.getChildren()) {
            if (((Label) ((AnchorPane) node).getChildren().get(0)).getText().equals(playerName)) {
                ((Label) ((AnchorPane) node).getChildren().get(1)).setText(String.valueOf(score));
            }
        }

        for (Node node : rightPanel.getChildren()) {
            if (((Label) ((AnchorPane) node).getChildren().get(0)).getText().equals(playerName)) {
                ((Label) ((AnchorPane) node).getChildren().get(1)).setText(String.valueOf(score));
            }
        }
        logger.info("Scores are updated.");
    }

    public synchronized int getRemainingTime() {
        return timeSeconds.getValue();
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
}
