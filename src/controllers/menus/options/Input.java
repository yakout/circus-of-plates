package controllers.menus.options;

import controllers.menus.Options;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Yakout on 1/25/17.
 */
public class Input implements Initializable {
    private static Input instance;

    @FXML
    private AnchorPane inputSettings;

    @FXML
    private Slider joystickSpeed;

    @FXML
    private Slider keyboardSpeed;

    private IntegerProperty volume = new SimpleIntegerProperty(50);

    public Input() {
    }

    public static synchronized Input getInstance() {
        if (instance == null) {
            try {
                URL url = new File("src/views/menus/options/input/input.fxml").toURI().toURL();
                FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

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
        instance = this;

//        volumeSlider.valueProperty().bindBidirectional(volume);
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        // // TODO: 1/25/17
        Options.getInstance().setMenuVisible(true);
    }

    public void setVisible(boolean isVisible) {
        inputSettings.setVisible(isVisible);
    }
}
