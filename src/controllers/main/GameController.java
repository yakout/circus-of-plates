package controllers.main;

import controllers.menus.Start;
import javafx.fxml.FXML;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javax.swing.*;

public class GameController implements Initializable, ActionListener {
    private VBox currentMenu;
    private Timer gameTimer;
    private final int CLOWNSPEED = 20;
    private final int PLATESPEED = 1;
    private static GameController instance;

    @FXML
    Rectangle rect, plate1, plate2, rightRod, leftRod;


    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMenu = Start.getInstance().getMenu();
        gameTimer = new Timer(10, this);
        gameTimer.start();
    }

    public void setCurrentMenu(VBox currentMenu) {
        this.currentMenu = currentMenu;
    }

//    private Double currentX;
//    @FXML
//    public void mouseHandler(MouseEvent event) {
//        if (currentX == null) {
//            currentX = event.getX();
//        } else {
//            if (currentX > event.getX()) {
//                rect.setX(Math.max(rect.getX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
//            } else {
//                rect.setX(Math.min(rect.getX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
//            }
//        }
//    }

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                rect.setX(Math.max(rect.getX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
                // rect.setX(rect.getX() - 10);
                break;
            case RIGHT:
                rect.setX(Math.min(rect.getX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
                // rect.setX(rect.getX() + 10);
                break;
            case ESCAPE:
                // startMenu.setVisible(currentMenu.isVisible() == startMenu.isVisible());
                // rect.setX(rect.getX() + 10);
                break;
            default:
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //ALl these numbers will be replaced by relative positions.
        //566 -> height of AnchorPane + half height of plate.
        if (plate1.getY() >= 500) {
            plate1.setX(639);
            plate1.setY(43);
        }
        else if (plate1.getX() + PLATESPEED < -350 + 3 * plate1.getWidth() / 2.0) {
            plate1.setY(plate1.getY() + PLATESPEED);
        } else {
            plate1.setX(plate1.getX() - PLATESPEED);
        }
        if (plate2.getX() + PLATESPEED > 350 - 3 * plate2.getWidth() / 2.0) {
            plate2.setY(plate2.getY() + PLATESPEED);
        } else {
            plate2.setX(plate2.getX() + PLATESPEED);
        }
    }
}

