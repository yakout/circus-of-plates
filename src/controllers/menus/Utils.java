package controllers.menus;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class Utils {
    private static final String menuSelectionRes = "src/assets/sounds/effects/MenuSelection1.mp3";
    private static final String menuChoiceRes = "src/assets/sounds/effects/MenuChoice.mp3";

    public static final Media menuSelectionMedia = new Media(new File(menuSelectionRes).toURI().toString());
    public static final Media menuChoiceMedia = new Media(new File(menuChoiceRes).toURI().toString());

    static final MediaPlayer menuSelectionMediaPlayer = new MediaPlayer(menuChoiceMedia);
    static final MediaPlayer menuChoiceMediaPlayer = new MediaPlayer(menuSelectionMedia);
}
