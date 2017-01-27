package controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;

public class AudioPlayer {

    private static final String MENU_SELECTION_RES =
            "assets/sounds/effects/MenuSelection1.mp3";
    public static Media menuSelectionMedia;
    private static final String MENU_CHOICE_RES =
            "assets/sounds/effects/MenuChoice.mp3";
    public static Media menuChoiceMedia;
    private static final String BACKGROUND_RES =
            "assets/sounds/Circus Dilemma.mp3";
    public static Media backgroundMedia;
    private static final String NEW_SCORE_RES =
            "assets/sounds/effects/score.mp3";
    public static Media newScoreMedia;
    private static final String WIN_RES =
            "assets/sounds/384. Steppin Up_1.mp3";
    public static Media windMedia;
    public static final MediaPlayer
            menuSelectionMediaPlayer;
    public static final MediaPlayer menuChoiceMediaPlayer;
    public static final MediaPlayer backgroundMediaPlayer;
    public static final MediaPlayer newScoreMediaPlayer;
    public static final MediaPlayer winMediaPlayer;
    private static Logger logger = LogManager.getLogger(AudioPlayer.class);

    static {
        try {
            menuSelectionMedia =
                    new Media(ClassLoader.getSystemResource(MENU_SELECTION_RES)
                            .toURI().toString());
            menuChoiceMedia =
                    new Media(ClassLoader.getSystemResource(MENU_CHOICE_RES)
                            .toURI().toString());
            backgroundMedia =
                    new Media(ClassLoader.getSystemResource(BACKGROUND_RES)
                            .toURI().toString());
            newScoreMedia =
                    new Media(ClassLoader.getSystemResource(NEW_SCORE_RES)
                            .toURI().toString());
             windMedia =
                    new Media(ClassLoader.getSystemResource(WIN_RES)
                            .toURI().toString());
        } catch (URISyntaxException e) {
            menuSelectionMedia = null;
            menuChoiceMedia = null;
            backgroundMedia = null;
            newScoreMedia = null;
            windMedia = null;
            e.printStackTrace();
        }
        menuSelectionMediaPlayer =
                new MediaPlayer(menuSelectionMedia);
        menuChoiceMediaPlayer =
                new MediaPlayer(menuChoiceMedia);
        backgroundMediaPlayer =
                new MediaPlayer(backgroundMedia);
        newScoreMediaPlayer =
                new MediaPlayer(newScoreMedia);
        winMediaPlayer =
                new MediaPlayer(windMedia);
    }

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
