package controllers.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    int clownXPos;

    @FXML
    Rectangle clown;
    /**
     * Called to initialize a controllers after its root element has been
     * completely processed.
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                clown.setX(clown.getX() - 10);
                break;

            case RIGHT:
                clown.setX(clown.getX() + 10);
                break;
            default:
                break;
        }
        System.out.println("Clown is detected!");
    }
}
