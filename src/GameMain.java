import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.settings.FileConstants;
import models.shapes.util.ShapeLoader;

import java.io.File;
import java.io.IOException;

public class GameMain extends Application {
    private static void registerLevels() {
        try {
            Class.forName("models.levels.LevelOne");
            Class.forName("models.levels.LevelTwo");
            Class.forName("models.levels.LevelThree");
            Class.forName("models.levels.LevelFour");
            Class.forName("models.levels.LevelFive");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void registerShapes() {
        ShapeLoader.loadShapes(new File(FileConstants.CLASS_LOADING_PATH));
    }

    public static void main(final String[] args) {
        registerLevels();
        registerShapes();
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        final Pane root = FXMLLoader.load(getClass().getResource("views/game"
                + ".fxml"));
        primaryStage.setTitle("Circus Of plates");
        primaryStage.setScene(new Scene(root, visualBounds.getWidth(),
                visualBounds.getHeight()));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); //"Press \"Ctrl + F\" to exit
        // full screen."
        primaryStage.setFullScreenExitKeyCombination(KeyCombination
                .keyCombination("Ctrl+F"));
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.getIcons().add(new Image(new File("src/assets/images"
                + "/icon/circus-icon.png").toURI().toURL().toString()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
