package controllers.menus;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;

class Utils {
    private static final String menuSelectionRes = "src/assets/sounds/effects/MenuSelection1.mp3";
    private static final String menuChoiceRes = "src/assets/sounds/effects/MenuChoice.mp3";
    private static final String backgroundRes = "src/assets/sounds/Circus Dilemma.mp3";

    public static final Media menuSelectionMedia = new Media(new File(menuSelectionRes).toURI().toString());
    public static final Media menuChoiceMedia = new Media(new File(menuChoiceRes).toURI().toString());
    public static final Media backgroundMedia = new Media(new File(backgroundRes).toURI().toString());

    static final MediaPlayer menuSelectionMediaPlayer = new MediaPlayer(menuChoiceMedia);
    static final MediaPlayer menuChoiceMediaPlayer = new MediaPlayer(menuSelectionMedia);
    static final MediaPlayer backgroundMediaPlayer = new MediaPlayer(backgroundMedia);
}
