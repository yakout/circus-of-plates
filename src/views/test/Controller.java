package views.test;


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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private final double CLOWNSPEED = 35;
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
    Rectangle rect, plate1, plate2, rightRod, leftRod;
    @FXML
    AnchorPane anchorPane;
    private VBox currentMenu;
    private int currentItem = 0;
    private ActionListener timerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //ALl these numbers will be replaced by relative positions.
            //566 -> height of AnchorPane + half height of plate.
            if (plate1.getY() >= 500) {
                plate1.setX(639);
                plate1.setY(43);
            } else if (plate1.getX() + plate1.getTranslateX() + PLATESPEED <
                    -350 + 3 *
                    plate1.getWidth()
                    / 2.0) {
                plate1.setTranslateY(plate1.getTranslateY() + PLATESPEED);
            } else {
                plate1.setTranslateX(plate1.getTranslateX() - PLATESPEED);
            }
            if (plate2.getX() + plate2.getTranslateX() + PLATESPEED > 350 - 3
                    * plate2.getWidth() /
                    2.0) {
                plate2.setTranslateY(plate2.getTranslateY() + PLATESPEED);
            } else {
                plate2.setTranslateX(plate2.getTranslateX() + PLATESPEED);
            }
        }
    };

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMenu = startMenu;
        PlateController<Rectangle> plate1Controller
                = new PlateController<>(plate1, false, rightRod.getWidth());
        PlateController<Rectangle> plate2Controller
                = new PlateController<>(plate2, true, leftRod.getWidth
                ());
        /*plate1Controller.move();
        plate2Controller.move();*/
        Thread rightPlateThread = new Thread(plate1Controller, "Right Plate Thread");
        Thread leftPlateThread = new Thread(plate2Controller, "Left Plate Thread");
        rightPlateThread.setDaemon(true);
        leftPlateThread.setDaemon(true);
        rightPlateThread.start();
        leftPlateThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (rect.getBoundsInParent().intersects(
                                    plate1.getBoundsInParent())) {
                                System.out.println("Right Plate Bounds: "
                                        + plate1.getBoundsInParent().toString());
                                System.out.println("Clown Bounds: " + rect
                                        .getBoundsInParent().toString());
                                rightPlateThread.interrupt();
                            }
                            if (rect.getBoundsInParent().intersects(
                                    plate2.getBoundsInParent())) {
                                System.out.println("Clown Bounds: " + rect
                                        .getBoundsInParent().toString());
                                System.out.println("Left Plate Bounds: "
                                        + plate2.getBoundsInParent().toString());
                                leftPlateThread.interrupt();
                            }
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("Intersection Thread Interrupted");
                        return;
                    }
                }
            }
        }).start();
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
                rect.setTranslateX(Math.max(rect.getTranslateX() - CLOWNSPEED,
                        -350 + rect
                                .getWidth() / 2.0));
                break;
            case RIGHT:
                rect.setTranslateX(Math.min(rect.getTranslateX() + CLOWNSPEED,
                        350 - rect
                                .getWidth() / 2.0));
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

