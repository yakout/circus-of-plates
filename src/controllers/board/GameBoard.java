package controllers.board;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmedyakout on 1/23/17.
 */
public class GameBoard implements Initializable {
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
    int GAMETIME = 60;

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
        timeline = new Timeline();
        timeSeconds = new SimpleIntegerProperty();

        initializeGameTimer();
    }

    private void initializeGameTimer() {
        this.counter.textProperty().bind(timeSeconds.asString());
        this.counter.setTextFill(Color.RED);
        this.counter.setStyle("-fx-font-size: 4em;");
        this.timeSeconds.set(GAMETIME);

        this.timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(GAMETIME + 1),
                        new KeyValue(timeSeconds, 0)));
        this.timeline.play();
    }

    public void addPlayerPanel(String playerName) throws IOException {
        URL url = new File(SCORE_PANEL_PATH).toURI().toURL();
        AnchorPane scorePanel = FXMLLoader.load(url);

        ((Label) scorePanel.getChildren().get(0)).setText(playerName);

        if (leftPanel.getChildren().size() < rightPanel.getChildren().size()) {
            leftPanel.getChildren().add(scorePanel);
        } else {
            rightPanel.getChildren().add(scorePanel);
        }
    }


    public void pause() {
        timeline.pause();
    }

    public void resume() {
        timeline.play();
    }

    public void reset() {
        leftPanel.getChildren().clear();
        rightPanel.getChildren().clear();
    }

    public void updateScore(int score, String playerName) {
        for (Node node : leftPanel.getChildren()) {
            if (((Label)((AnchorPane) node).getChildren().get(0)).getText().equals(playerName)) {
                ((Label)((AnchorPane) node).getChildren().get(1)).setText(String.valueOf(score));
            }
        }

        for (Node node : rightPanel.getChildren()) {
            if (((Label)((AnchorPane) node).getChildren().get(0)).getText().equals(playerName)) {
                ((Label)((AnchorPane) node).getChildren().get(1)).setText(String.valueOf(score));
            }
        }
    }
}
