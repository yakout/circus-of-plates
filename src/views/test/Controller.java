package views.test;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller implements Initializable {
	private final double CLOWNSPEED = 10;
	private final double PLATESPEED = 1.7;
	@FXML
	VBox startMenu;
	@FXML
	VBox gameModeMenu;
	@FXML
	VBox optionsMenu;
	@FXML
	VBox loadGameMenu;
	@FXML
	VBox helpMenu;
	@FXML
	Rectangle rect, rightRod, leftRod;
	@FXML
	AnchorPane anchorPane;
	@FXML
	javafx.scene.image.ImageView clown;
	@FXML
	javafx.scene.image.ImageView plate1;
	@FXML
	javafx.scene.image.ImageView plate2;
	private VBox currentMenu;
	private int currentItem = 0;
	private final ActionListener timerListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			//ALl these numbers will be replaced by relative positions.
			//566 -> height of AnchorPane + half height of plate.
			if (plate1.getY() >= plate1.getParent().getLayoutBounds().getHeight()) {
				plate1.setX(639);
				plate1.setY(43);
			} else if (plate1.getX() + plate1.getTranslateX() + PLATESPEED <
					-clown.getParent().getLayoutBounds().getWidth() / 2.0 + 3 *
							plate1.getLayoutBounds().getWidth() / 2.0) {
				plate1.setTranslateY(plate1.getTranslateY() + PLATESPEED);
			} else {
				plate1.setTranslateX(plate1.getTranslateX() - PLATESPEED);
			}
			if (plate2.getX() + plate2.getTranslateX() + PLATESPEED
					> clown.getParent().getLayoutBounds().getWidth() / 2.0
					- 3 * plate2.getLayoutBounds().getWidth() / 2.0) {
				plate2.setTranslateY(plate2.getTranslateY() + PLATESPEED);
			} else {
				plate2.setTranslateX(plate2.getTranslateX() + PLATESPEED);
			}
		}
	};

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or
	 *                  <tt>null</tt> if
	 */
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		currentMenu = startMenu;
		/*File file = new File("src/assets/images/Clowns/clown1.png");
        Image img = new Image(file.toURI().toString());
        clown.setImage(img);*/
		final PlateController<javafx.scene.image.ImageView> plate1Controller
				= new PlateController<>(plate1, false, rightRod.getWidth());
		final PlateController<javafx.scene.image.ImageView> plate2Controller
				= new PlateController<>(plate2, true, leftRod.getWidth());
		/*plate1Controller.move();
        plate2Controller.move();*/
		final Thread rightPlateThread = new Thread(plate1Controller, "Right Plate Thread");
		final Thread leftPlateThread = new Thread(plate2Controller, "Left Plate Thread");
		rightPlateThread.setDaemon(true);
		leftPlateThread.setDaemon(true);
		rightPlateThread.start();
		leftPlateThread.start();
		final Thread intersectionThread = new Thread(new Runnable() {
			boolean plate1Moving = true;
			boolean plate2Moving = true;

			@Override
			public void run() {
				while (true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (plate1Moving && clown.getBoundsInParent().intersects(
									plate1.getBoundsInParent())) {
								System.out.println("Right Plate Bounds: "
										+ plate1.getBoundsInParent().toString());
								System.out.println("Clown Bounds: " + clown
										.getBoundsInParent().toString());
								//								plate1.setX(plate1.getX() + plate1.getTranslateX());
								//								plate1.setTranslateX(0);
								plate1Moving = false;
								rightPlateThread.interrupt();
								plate1.translateXProperty().bind(clown.translateXProperty().add(plate1.getTranslateX() - clown.getTranslateX()));
								//plate1.xProperty().bind(clown.xProperty());
								System.out.println(clown.getX());
								System.out.println(clown.getLayoutX());
								System.out.println(clown.getTranslateX());
								//clown.layoutXProperty().bind(plate1.layoutXProperty());
								//								plate2.layoutXProperty().bind(clown.layoutXProperty());
							}
							if (plate2Moving && clown.getBoundsInParent().intersects(
									plate2.getBoundsInParent())) {
								System.out.println("Clown Bounds: " + clown
										.getBoundsInParent().toString());
								System.out.println("Left Plate Bounds: "
										+ plate2.getBoundsInParent().toString());
								plate2Moving = false;
								leftPlateThread.interrupt();
								System.out.println(plate2.getTranslateX());
								System.out.println(plate2.getX());
								plate2.translateXProperty().bind(clown.translateXProperty().add(plate2.getTranslateX() - clown.getTranslateX()));
								//								plate2.setX(plate2.getX() + plate2.getTranslateX());
								//								plate2.setTranslateX(0);
								//								plate2.translateXProperty().bind(clown.translateXProperty());

								//								plate2.layoutXProperty().bind(clown.layoutXProperty());
							}
						}
					});
					try {
						Thread.sleep(10);
					} catch (final InterruptedException ex) {
						System.out.println("Intersection Thread Interrupted");
						return;
					}
				}
			}
		});
		intersectionThread.setDaemon(true);
		intersectionThread.start();
		//gameTimer = new Timer(10, timerListener);
		// gameTimer.start();
		/*TranslateTransition tt = new TranslateTransition(Duration.millis
        (2000), plate1);
        tt.setByX(plate1.getX() - 500);
        tt.play();*/
        /*rect.setVisible(false)*/
		;
	}

	private Button getButton(int index) {
		return (Button) currentMenu.getChildren().get(index);
	}


	public void active(int id) {
		getButton(id).setTextFill(Color.DARKGOLDENROD);
	}

	public void disActive(int id) {
		getButton(id).setTextFill(Color.BLACK);
	}

	@FXML
	public void keyHandler(KeyEvent event) {
		switch (event.getCode()) {
			case UP:
				if (currentItem != 0) {
					disActive(currentItem);
					active(--currentItem);
				}
				break;
			case DOWN:
				if (currentItem != currentMenu.getChildren().size() - 1) {
					disActive(currentItem);
					active(++currentItem);
				}
				break;
			case LEFT:
				double transX = clown.getTranslateX();
				clown.setTranslateX(Math.max(clown.getTranslateX() - CLOWNSPEED,
						-clown.getParent().getLayoutBounds().getWidth() / 2.0
								+ clown.getLayoutBounds().getWidth()
								/ 2.0));
				break;
			case RIGHT:
				clown.setTranslateX(Math.min(clown.getTranslateX() + CLOWNSPEED,
						clown.getParent().getLayoutBounds().getWidth()
								/ 2.0 - clown.getLayoutBounds().getWidth() / 2.0));
				break;
			default:
				break;
		}
	}

	@FXML
	void handleKeyAction(KeyEvent event) {
		String id = ((Node) event.getSource()).getId();
		switch (currentMenu.getId()) {
			case "0":
				handleStartMenu(id);
				break;
			case "1":
				handleHelpMenu(id);
				break;
		}
	}


	private void handleHelpMenu(String id) {
		// currentMenu.setVisible(false);
	}

	private void handleStartMenu(String id) {
		currentMenu.setVisible(false);
		switch (id) {
			case "0":
				gameModeMenu.setVisible(true);
				currentMenu = gameModeMenu;
				break;
			case "1":
				currentMenu.setVisible(false);
				// TODO: 12/25/16  show the game pad
				break;
			case "2":
				loadGameMenu.setVisible(true);
				currentMenu = loadGameMenu;
				break;
			case "3":
				optionsMenu.setVisible(true);
				currentMenu = optionsMenu;
				break;
			case "4":
				helpMenu.setVisible(true);
				currentMenu = helpMenu;
				break;
			case "5":
				System.exit(0);
				break;
			default:
				break;
		}
	}
}