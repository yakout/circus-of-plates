package controllers.board;

import controllers.main.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ahmedyakout on 1/23/17.
 */
public class GameBoard implements Initializable {
    @FXML
    private HBox board;

    @FXML
    private Label counter;

    @FXML
    private Label playerOneScore;

    @FXML
    private Label playerTwoScore;


    private Timeline timeline;
    private IntegerProperty timeSeconds;
    int GAMETIME = 60;

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

}
