package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import controllers.main.GameController;

public abstract class MenuController implements Initializable {
    private int currentItem;

    public MenuController() {
    }

    private Button getButton(int index) {

        return (Button)getMenu().getChildren().get(index);
    }

    public void activateOption(int id) {
        try {
            getButton(id).setTextFill(Color.DARKGOLDENROD);
        } catch (ClassCastException e) {

        }
    }

    public void disActivateOption(int id) {
        try {
            getButton(id).setTextFill(Color.BLACK);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    void updateCurrentMenu(VBox newMenu) {
        GameController.getInstance().setCurrentMenu(newMenu);
    }

    abstract void handle(String id);

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (currentItem != 0) {
                    disActivateOption(currentItem);
                    activateOption(--currentItem);
                }
                break;
            case DOWN:
                if (currentItem != getMenu().getChildren().size() - 1) {
                    disActivateOption(currentItem);
                    activateOption(++currentItem);
                }
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

    public abstract VBox getMenu();
}
