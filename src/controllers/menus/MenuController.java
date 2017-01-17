package controllers.menus;

import controllers.input.InputAction;
import controllers.input.joystick.JoystickEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import controllers.main.GameController;


public abstract class MenuController implements Initializable {
    private int currentIndex = 0;
    
    enum Direction {
        UP,
        DOWN
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

    void updateCurrentMenu(VBox newMenu) {
        GameController.getInstance().setCurrentMenu(newMenu);
    }

    void requestFocus(int index) {
        Platform.runLater(() -> getButton(index).requestFocus());
    }

    abstract void handle(String id);

    private void handleEvent(Direction direction) {
        getCurrentIndex();
        switch (direction) {
            case UP:
                if (currentIndex != 0) {
                    requestFocus(--currentIndex);
                }
                break;
            case DOWN:
                if (currentIndex != getMenu().getChildren().size() - 1) {
                    requestFocus(++currentIndex);
                }
                break;
            default:
                break;
        }
    }

    @InputAction
    public void joystickHancdle(JoystickEvent event) {
       switch (event.getJoystickCode()) {
           case DOWN:
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       handleEvent(Direction.DOWN);
                   }
               });
               break;
           case UP:
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       handleEvent(Direction.UP);
                   }
               });
               break;
           default:
               break;
       }
    }


    @FXML
    public void keyHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handle(getButton(getCurrentIndex()).getId());
        }
    }

    @FXML
    public void MouseHandler(MouseEvent event) {
        handle(((Node) event.getSource()).getId());
    }

    public abstract VBox getMenu();
}
