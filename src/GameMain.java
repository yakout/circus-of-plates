import java.io.IOException;
import java.net.URL;

import controllers.input.joystick.Joystick;
import controllers.input.keyboard.Keyboard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameMain extends Application {
    @Override
    public void start(final Stage primaryStage) throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        final Pane root = FXMLLoader.load(getClass().getResource("views/game.fxml"));
        primaryStage.setTitle("Circus Of plates");
        primaryStage.setScene(new Scene(root, visualBounds.getWidth(),
                visualBounds.getHeight()));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); //"Press \"Ctrl + F\" to exit full screen."
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Ctrl+F"));
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
