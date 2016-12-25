package views.test;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;


public class Controller implements Initializable, ActionListener {
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
        currentMenu = startMenu;
        gameTimer = new Timer(10, this);
        gameTimer.start();
        /*rect.setVisible(false)*/;
    }

    @FXML
    VBox startMenu;

    @FXML
    VBox gameModeMenu;

    @FXML
    VBox optionsMenu;

    @FXML
    VBox loadGameMenu;

    @FXML
    VBox helpMenu;

    @FXML
    Rectangle rect, plate1, plate2, rightRod, leftRod;

    private VBox currentMenu;
    private int currentItem = 0;
    private Timer gameTimer;
    private final int CLOWNSPEED = 10;
    private final int PLATESPEED = 1;


    private Button getButton(int index) {
        return (Button) currentMenu.getChildren().get(index);
    }


    public void active(int id) {
        getButton(id).setTextFill(Color.DARKGOLDENROD);
    }

    public void disActive(int id) {
        getButton(id).setTextFill(Color.BLACK);
    }

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (currentItem != 0) {
                    disActive(currentItem);
                    active(--currentItem);
                }
                break;
            case DOWN:
                if (currentItem != currentMenu.getChildren().size() - 1) {
                    disActive(currentItem);
                    active(++currentItem);
                }
                break;
            case LEFT:
                rect.setX(Math.max(rect.getX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
                break;
            case RIGHT:
                rect.setX(Math.min(rect.getX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
                break;
            default:
                break;
        }
    }

    @FXML
    void handleKeyAction(KeyEvent event) {
        String id = ((Node) event.getSource()).getId();
        switch (currentMenu.getId()) {
            case "0":
                handleStartMenu(id);
                break;
            case "1":
                handleHelpMenu(id);
                break;
        }
    }


    private void handleHelpMenu(String id) {
        // currentMenu.setVisible(false);
    }

    private void handleStartMenu(String id) {
        currentMenu.setVisible(false);
        switch (id) {
            case "0":
                gameModeMenu.setVisible(true);
                currentMenu = gameModeMenu;
                break;
            case "1":
                currentMenu.setVisible(false);
                // TODO: 12/25/16  show the game pad
                break;
            case "2":
                loadGameMenu.setVisible(true);
                currentMenu = loadGameMenu;
                break;
            case "3":
                optionsMenu.setVisible(true);
                currentMenu = optionsMenu;
                break;
            case "4":
                helpMenu.setVisible(true);
                currentMenu = helpMenu;
                break;
            case "5":
                System.exit(0);
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

