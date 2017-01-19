package views.test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestMain extends Application {

	@Override
	public void start(final Stage primaryStage) throws IOException {
		// load needed classes
		final Pane root = FXMLLoader.load(getClass().getResource("test.fxml"));
		primaryStage.setTitle("Circus Of Plates");
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(700);
		primaryStage.setScene(new Scene(root, 700, 500));
		root.prefHeightProperty().bind(primaryStage.heightProperty());
		root.prefWidthProperty().bind(primaryStage.widthProperty());
		//primaryStage.setFullScreen(true);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}

	public static void main(final String[] args) {
		launch(args);
	}
}