package controllers.menus;

import controllers.AudioPlayer;
import controllers.input.InputAction;
import controllers.input.joystick.JoystickEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import controllers.main.GameController;
import javafx.scene.layout.VBox;


public abstract class MenuController implements Initializable {
    private int currentIndex = 0;

    enum Direction {
        UP,
        DOWN,
        PRESS
    }

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

    void updateCurrentMenu(MenuController menuController) {
        GameController.getInstance().setCurrentMenu(menuController);
        menuController.requestFocus(0);
    }

    public void requestFocus(int index) {
        Platform.runLater(() -> getButton(index).requestFocus());
    }

    abstract void handle(String id);

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
                Platform.runLater(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuChoiceMedia).play();
                    handleEvent(Direction.DOWN);
                });
                break;
            case UP:
                Platform.runLater(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuChoiceMedia).play();
                    handleEvent(Direction.UP);
                });
                break;
            case PRESS:
                Platform.runLater(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuSelectionMedia).play();
                    handleEvent(Direction.PRESS);
                });
            default:
                break;
        }
    }


    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                new Thread(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuSelectionMedia).play();
                }).start();
                handle(getButton(getCurrentIndex()).getId());
                break;
            case ESCAPE:
                System.out.println("escape is pressed and triggered by a menu");
                break;
            case DOWN:
                new Thread(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuChoiceMedia).play();
                }).start();
                break;
            case UP:
                new Thread(() -> {
                    new javafx.scene.media.MediaPlayer(AudioPlayer.menuChoiceMedia).play();
                }).start();
                break;
            default:
                break;
        }
    }

    @FXML
    public void mouseHandler(MouseEvent event) {
        handle(((Node) event.getSource()).getId());
    }

    protected abstract VBox getMenu();

    public abstract void setMenuVisible(boolean visible);

    public abstract boolean isVisible();

}
