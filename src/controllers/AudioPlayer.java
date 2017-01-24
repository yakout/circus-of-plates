package controllers;

import javafx.scene.media.Media;
import java.io.File;

public class AudioPlayer {
    private static final String menuSelectionRes =
            "src/assets/sounds/effects/MenuSelection1.mp3";
    private static final String menuChoiceRes =
            "src/assets/sounds/effects/MenuChoice.mp3";
    private static final String backgroundRes =
            "src/assets/sounds/Circus Dilemma.mp3";
    private static final String newScoreRes =
            "src/assets/sounds/effects/score.mp3";

    public static final Media menuSelectionMedia =
            new Media(new File(menuSelectionRes).toURI().toString());
    public static final Media menuChoiceMedia =
            new Media(new File(menuChoiceRes).toURI().toString());
    public static final Media backgroundMedia =
            new Media(new File(backgroundRes).toURI().toString());
    public static final Media newScoreMedia =
            new Media(new File(newScoreRes).toURI().toString());

    public static final javafx.scene.media.MediaPlayer menuSelectionMediaPlayer =
            new javafx.scene.media.MediaPlayer(menuChoiceMedia);
    public static final javafx.scene.media.MediaPlayer menuChoiceMediaPlayer =
            new javafx.scene.media.MediaPlayer(menuSelectionMedia);
    public static final javafx.scene.media.MediaPlayer backgroundMediaPlayer =
            new javafx.scene.media.MediaPlayer(backgroundMedia);
    public static final javafx.scene.media.MediaPlayer newScoreMediaPlayer =
            new javafx.scene.media.MediaPlayer(newScoreMedia);
}
