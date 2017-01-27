package controllers;

import javafx.scene.media.Media;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AudioPlayer {

    private static final String MENU_SELECTION_RES =
            "src/assets/sounds/effects/MenuSelection1.mp3";
    public static final Media menuSelectionMedia =
            new Media(new File(MENU_SELECTION_RES).toURI().toString());
    private static final String MENU_CHOICE_RES =
            "src/assets/sounds/effects/MenuChoice.mp3";
    public static final Media menuChoiceMedia =
            new Media(new File(MENU_CHOICE_RES).toURI().toString());
    private static final String BACKGROUND_RES =
            "src/assets/sounds/Circus Dilemma.mp3";
    public static final Media backgroundMedia =
            new Media(new File(BACKGROUND_RES).toURI().toString());
    private static final String NEW_SCORE_RES =
            "src/assets/sounds/effects/score.mp3";
    public static final Media newScoreMedia =
            new Media(new File(NEW_SCORE_RES).toURI().toString());
    private static final String WIN_RES =
            "src/assets/sounds/384. Steppin Up_1.mp3";
    public static final Media windMedia =
            new Media(new File(WIN_RES).toURI().toString());
    public static final MediaPlayer
            menuSelectionMediaPlayer =
            new MediaPlayer(menuSelectionMedia);
    public static final MediaPlayer menuChoiceMediaPlayer =
            new MediaPlayer(menuChoiceMedia);
    public static final MediaPlayer backgroundMediaPlayer =
            new MediaPlayer(backgroundMedia);
    public static final MediaPlayer newScoreMediaPlayer =
            new MediaPlayer(newScoreMedia);
    public static final MediaPlayer winMediaPlayer =
            new MediaPlayer(windMedia);
    private static Logger logger = LogManager.getLogger(AudioPlayer.class);

    public static synchronized void play(AudioType audioType) {
        switch (audioType) {
            case MENU_CHOICE:
                menuChoiceMediaPlayer.play();
                menuChoiceMediaPlayer.seek(Duration.ZERO);
                break;
            case MENU_SELECTION:
                menuSelectionMediaPlayer.play();
                menuSelectionMediaPlayer.seek(Duration.ZERO);
                break;
            case BACKGROUND:
                backgroundMediaPlayer.play();
                backgroundMediaPlayer.seek(Duration.ZERO);
                break;
            case NEW_SCORE:
                newScoreMediaPlayer.play();
                newScoreMediaPlayer.seek(Duration.ZERO);
                break;
            default:
                break;
        }
    }

    /**
     * Stops the audio.
     * @param audioType
     */
    public static synchronized void stop(AudioType audioType) {
        switch (audioType) {
            case BACKGROUND:
                backgroundMediaPlayer.stop();
                break;
            default:
                break;
        }
    }

    /**
     * Pauses the audio.
     * @param audioType
     */
    public static synchronized void pause(AudioType audioType) {
        switch (audioType) {
            case BACKGROUND:
                backgroundMediaPlayer.pause();
                break;
            default:
                break;
        }
    }

    public static synchronized void resume(AudioType audioType) {
        switch (audioType) {
            case BACKGROUND:
                backgroundMediaPlayer.play();
                break;
            default:
                break;
        }
    }

    public static synchronized void mute(boolean isMute) {
        menuChoiceMediaPlayer.setMute(isMute);
        menuSelectionMediaPlayer.setMute(isMute);
        backgroundMediaPlayer.setMute(isMute);
        newScoreMediaPlayer.setMute(isMute);
        winMediaPlayer.setMute(isMute);
        logger.info("Sounds is muted.");
    }

    public static synchronized void setVolume(double volume) {
        menuChoiceMediaPlayer.setVolume(volume);
        menuSelectionMediaPlayer.setVolume(volume);
        backgroundMediaPlayer.setVolume(volume);
        newScoreMediaPlayer.setVolume(volume);
        winMediaPlayer.setVolume(volume);
        logger.info("Volume is set successfully.");
    }

    public enum AudioType {
        MENU_SELECTION,
        MENU_CHOICE,
        BACKGROUND,
        NEW_SCORE
    }
}
