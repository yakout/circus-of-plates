package controllers.input;

import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class Input {
    enum Key {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    abstract void addAction(UserAction userAction);

}
