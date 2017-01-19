package models.players;

import java.util.EmptyStackException;
import java.util.Stack;

import controllers.input.InputType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import models.GameRules;
import models.Point;
import models.shapes.Shape;

public class Player {

    private static int numOfPlayers = 0;

    private Object avatar; // TODO
    private Stack<Shape> leftStick;
    private Stack<Shape> rightStick;
    private String playerName;
    private Point position;
    private int score;
    private DoubleProperty speed;
    private InputType inputType;
    
    public Player() {
        this.playerName = "Player " + ++numOfPlayers;
        this.score = 0;
        this.position = new Point(0, 0);
        this.leftStick = new Stack<>();
        this.rightStick = new Stack<>();
        this.speed = new SimpleDoubleProperty();
    }

    public Player(String name) {
        this();
        this.playerName = name;
    }

    public boolean pushPlateLeft(Shape shape) {
        return pushPlate(this.leftStick, shape);
    }
    
    public boolean pushPlateRight(Shape shape) {
        return pushPlate(this.rightStick, shape);
    }
    
    private void popPlate(Stack<Shape> stick) throws EmptyStackException {
        stick.pop();
    }
    
    // returns true if got consecutive plates and the score increased
    private boolean pushPlate(Stack<Shape> stick, Shape shape) {
        stick.add(shape);
        if (stick.size() >= GameRules.NUM_OF_CONSECUTIVE_PLATES) {
            for (int i = 1; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
                if (!stick.peek().equals(shape.getColor())) {
                    return false;
                }
            }
            for (int i = 0; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
                this.popPlate(stick);
            }
            this.addScore(GameRules.CONSECUTIVE_PLATES_ADDED_SCORE);
            return true;
        }
        return false;
    }
    
    public void addScore(int change) {
        this.score += change;
    }

    public int getNumOfLeftPlates() {
        return this.leftStick.size();
    }
    
    public int getNumOfRightPlates() {
        return this.rightStick.size();
    }

    public void setName(String newName) {
        this.playerName = newName;
    }

    public String getName() {
        return this.playerName;
    }

    public void setPosition(Point newPosition) {
        this.position = newPosition;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getScore() {
        return this.score;
    }

    public double getSpeed() {
        return speed.get();
    }

    public void setSpeed(double speed) {
        this.speed.set(speed);
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public InputType getInputType() {
        return inputType;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }
    
    public Object getAvatar() {
        return this.avatar;
    }

}
