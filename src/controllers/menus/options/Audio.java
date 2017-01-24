package controllers.menus.options;

import controllers.menus.Options;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Audio implements Initializable {
    private static Audio instance;

    @FXML
    private SplitPane audioSettings;

    @FXML
    private Slider volumeSlider;

    @FXML
    private RadioButton muteOption;

    private IntegerProperty volume = new SimpleIntegerProperty(50);
    private BooleanProperty mute = new SimpleBooleanProperty(false);

    public Audio() {
    }

    public static synchronized Audio getInstance() {
        if (instance == null) {
            try {
                URL url = new File("src/views/menus/options/audio/audio.fxml").toURI().toURL();
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
        muteOption.selectedProperty().bindBidirectional(mute);
        volumeSlider.valueProperty().bindBidirectional(volume);

        instance = this;
    }

    public SplitPane getAudioSettings() {
        return audioSettings;
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        // // TODO: 1/25/17  
        this.audioSettings.setVisible(true);
    }
}
