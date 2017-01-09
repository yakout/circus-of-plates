package controllers.menus;

import controllers.input.InputAction;
import controllers.input.joystick.Joystick;
import controllers.input.joystick.JoystickCode;
import controllers.input.joystick.JoystickEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import controllers.main.GameController;

import java.awt.event.MouseEvent;

public abstract class MenuController implements Initializable {
    private int currentItem;
    
    enum Direction {
        UP,
        DOWN
    }

    public MenuController() {
        // Joystick.getInstance().registerClassForInputAction(getClass());
    }

    protected Button getButton(int index) {
        return (Button)getMenu().getChildren().get(index);
    }

    protected Button getButton(String id) {
        return (Button) getMenu().getScene().lookup("#" + id);
    }

    public void activateOption(Button btn) {
        btn.setTextFill(Color.DARKGOLDENROD);
    }

    public void disActivateOption(Button btn) {
        btn.setTextFill(Color.BLACK);
    }

    void updateCurrentMenu(VBox newMenu) {
        GameController.getInstance().setCurrentMenu(newMenu);
    }

    abstract void handle(String id);

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                handleEvent(Direction.UP);
                break;
            case DOWN:
                handleEvent(Direction.DOWN);
                break;
            default:
                break;
        }
    }

    private void handleEvent(Direction direction) {
        switch (direction) {
            case UP:
                if (currentItem != 0) {
                    // System.out.println(getButton(0).isHover());
                    System.out.println(getButton(currentItem).isFocused());
                    System.out.println(getButton(currentItem).getId());

                    currentItem = getCurrentItem();
                    disActivateOption(getButton(currentItem));
                    activateOption(getButton(--currentItem));
                }
                break;
            case DOWN:
                if (currentItem != getMenu().getChildren().size() - 1) {
                    currentItem = getCurrentItem();

                    disActivateOption(getButton(currentItem));
                    activateOption(getButton(++currentItem));
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
               handleEvent(Direction.DOWN);
               break;
           case UP:
               handleEvent(Direction.UP);
               break;
           default:
               break;
       }
    }

    @FXML
    public void handleKeyAction(KeyEvent event) {
        String id = ((Node) event.getSource()).getId();
        handle(id);
        currentItem = 0;
    }

    @FXML
    public void MouseHandler(MouseEvent event) {
        String id = ((Node) event.getSource()).getId();
        // // TODO: 1/9/17  
    }

    public int getCurrentItem() {
        int index = 0;
        for(Node button : getMenu().getChildren()) {
            if (button.isFocused()) {
                break;
            }
            index++;
        }
        return index;
    }

    public abstract VBox getMenu();
}
