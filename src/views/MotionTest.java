package views;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MotionTest implements ActionListener, KeyListener {

    @FXML Rectangle rect;
    Timer tm = new Timer(5, this);


    @FXML
    public void move() {
        tm.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
