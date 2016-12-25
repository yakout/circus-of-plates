package views.test;


import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller implements Initializable {
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
        currentMenu = startMenu;
    }

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
    Rectangle rect;


    private VBox currentMenu;
    private int currentItem = 0;


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
                rect.setX(rect.getX() - 10);
                break;

            case RIGHT:
                rect.setX(rect.getX() + 10);
                break;
            default:
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