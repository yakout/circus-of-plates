package controllers.menus;

import controllers.input.InputAction;
import controllers.input.joystick.JoystickEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import controllers.main.GameController;
import javafx.scene.media.MediaPlayer;


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
        for(Node button : getMenu().getChildren()) {
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
    }

    public void requestFocus(int index) {
        Platform.runLater(() -> getButton(index).requestFocus());
    }

    abstract void handle(String id);

    private void handleEvent(Direction direction) {
        System.out.println("prev " + currentIndex);
        getCurrentIndex();
        System.out.println(" curr " + currentIndex);
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
                    new MediaPlayer(Utils.menuChoiceMedia).play();
                    handleEvent(Direction.DOWN);
                });
                break;
            case UP:
                Platform.runLater(() -> {
                    new MediaPlayer(Utils.menuChoiceMedia).play();
                    handleEvent(Direction.UP);
                });
                break;
            case PRESS:
                Platform.runLater(() -> {
                    new MediaPlayer(Utils.menuSelectionMedia).play();
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
                new MediaPlayer(Utils.menuSelectionMedia).play();
                handle(getButton(getCurrentIndex()).getId());
                break;
            case ESCAPE:
                System.out.println("escape is pressed and triggered by a menu");
                break;
            case DOWN:
                new MediaPlayer(Utils.menuChoiceMedia).play();
                break;
            case UP:
                new MediaPlayer(Utils.menuChoiceMedia).play();
                break;
            default:
                break;
        }
    }

    @FXML
    public void mouseHandler(MouseEvent event) {
        handle(((Node) event.getSource()).getId());
    }

    public abstract VBox getMenu();
}
