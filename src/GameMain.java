import java.io.IOException;
import java.net.URL;

import controllers.input.joystick.Joystick;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMain extends Application {
	@Override
	public void start(final Stage primaryStage) throws IOException {
		// load needed classes
		final URL resource = getClass().getResource("/assets/sounds/Circus Dilemma.mp3");
		//        final Media media = new Media(resource.toString());
		//        final MediaPlayer mediaPlayer = new MediaPlayer(media);
		//        mediaPlayer.play();

		final Pane root = FXMLLoader.load(getClass().getResource
				("src/views/game.fxml"));
		primaryStage.setTitle("Circus Of plates");
		//        primaryStage.setMinHeight(500);
		//        primaryStage.setMinWidth(700);
		primaryStage.setScene(new Scene(root));
		//primaryStage.setMaximized(true);


		// =========================== FULL SCREEN =================================
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint(""); //"Press \"Ctrl + F\" to exit full screen."
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Ctrl+F"));
		// =========================== EXIT ON WINDOW CLOSE =================================
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});

		primaryStage.setResizable(false);
		primaryStage.show();
		// root.requestFocus(); // the root don't have the focus when the stage is shown it goes to the first node.

		// TODO: 1/17/17
		Joystick.getInstance().start();
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
