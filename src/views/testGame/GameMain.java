package views.testGame;

import com.sun.tools.javac.comp.Todo;
import controllers.input.joystick.Joystick;
import controllers.menus.Help;
import controllers.menus.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GameMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // load needed classes
        final URL resource = getClass().getResource("/assets/sounds/Circus Dilemma.mp3");
//        final Media media = new Media(resource.toString());
//        final MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();

        Pane root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Circus Of Plates");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, 700, 500));
        //primaryStage.setFullScreen(true);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();

        // TODO: 1/17/17  
        Joystick.getInstance().start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
