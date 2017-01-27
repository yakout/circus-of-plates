package controllers.menus;

import controllers.AudioPlayer;
import controllers.input.InputAction;
import controllers.input.joystick.JoystickEvent;
import controllers.main.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static controllers.AudioPlayer.menuChoiceMediaPlayer;
import static controllers.AudioPlayer.menuSelectionMediaPlayer;


public abstract class MenuController implements Initializable {

    private static Logger logger = LogManager.getLogger(MenuController.class);
    private int currentIndex = 0;

    public MenuController() {
    }

    protected Button getButton(String id) {
        return (Button) getMenu().getScene().lookup("#" + id);
    }

    protected Button getButton(int index) {
        return (Button) getMenu().getChildren().get(index);
    }

    protected int getCurrentIndex() {
        int index = 0;
        for (Node button : getMenu().getChildren()) {
            if (button.isFocused()) {
                break;
            }
            index++;
        }
        currentIndex = index;
        return currentIndex;
    }

    public void updateCurrentMenu(MenuController menuController) {
        GameController.getInstance().setCurrentMenu(menuController);
        menuController.requestFocus(0);
    }

    public void requestFocus(int index) {
        Platform.runLater(() -> getButton(index).requestFocus());
    }

    abstract protected void handle(String id);

    private void handleEvent(Direction direction) {
        getCurrentIndex();
        switch (direction) {
            case UP:
                if (currentIndex > 0) {
                    requestFocus(--currentIndex);
                }
                break;
            case DOWN:
                if (currentIndex < getMenu().getChildren().size() - 1) {
                    requestFocus(++currentIndex);
                }
                break;
            case PRESS:
                handle(getButton(currentIndex).getId());
                currentIndex = 0;
                break;
            default:
                break;
        }
    }

    @InputAction
    public void joystickHancdle(JoystickEvent event) {
        if (!getMenu().isVisible()) {
            return;
        }
        switch (event.getJoystickCode()) {
            case DOWN:
                menuChoiceMediaPlayer.play();
                menuChoiceMediaPlayer.setStartTime(Duration.ZERO);
                handleEvent(Direction.DOWN);
                break;
            case UP:
                Platform.runLater(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer
                            .menuChoiceMedia).play();
                    handleEvent(Direction.UP);
                });
                break;
            case PRESS:
                Platform.runLater(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer
                            .menuSelectionMedia).play();
                    handleEvent(Direction.PRESS);
                });
                break;
            default:
                break;
        }
    }

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                menuSelectionMediaPlayer.play();
                menuSelectionMediaPlayer.seek(Duration.ZERO);
                handle(getButton(getCurrentIndex()).getId());
                break;
            case ESCAPE:
//                GameController.getInstance().continueGame();
                break;
            case DOWN:
                menuChoiceMediaPlayer.play();
                menuChoiceMediaPlayer.seek(Duration.ZERO);
                break;
            case UP:
                menuChoiceMediaPlayer.play();
                menuChoiceMediaPlayer.seek(Duration.ZERO);
                break;
            default:
                break;
        }
        logger.info("Music is played.");
    }

    @FXML
    public void mouseHandler(MouseEvent event) {
        handle(((Node) event.getSource()).getId());
    }

    protected abstract VBox getMenu();

    public abstract void setMenuVisible(boolean visible);

    public abstract boolean isVisible();

    enum Direction {
        UP,
        DOWN,
        PRESS
    }

}
