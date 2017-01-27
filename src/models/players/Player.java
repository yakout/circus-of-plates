package models.players;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.sun.jmx.remote.internal.ArrayQueue;
import controllers.input.InputType;
import controllers.player.ScoreObserver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import models.GameRules;
import models.Point;
import models.shapes.Shape;

public class Player {
    private static int numOfPlayers = 0;

    private transient List<ScoreObserver> observers;
    private volatile Stack<Shape> leftStick;
    private volatile Stack<Shape> rightStick;
    private String playerName;
    private Point position;
    private volatile int score;
    private DoubleProperty speed;
    private InputType inputType;
    private String playerUrl;
    private String leftStickUrl;
    private String rightStickUrl;
    private boolean leftStackFull;
    private boolean rightStackFull;
    private static final String LEFT_STICK_URL = "src/views/sticks/stick.fxml";
    private static final String RIGHT_STICK_URL = "src/views/sticks/stick.fxml";

    public Player() {
        this.playerName = "PlayerController " + numOfPlayers;
        this.score = 0;
        this.position = new Point(0, 0);
        this.leftStick = new Stack<>();
        this.rightStick = new Stack<>();
        this.speed = new SimpleDoubleProperty();
        this.leftStickUrl = LEFT_STICK_URL;
        this.rightStickUrl = RIGHT_STICK_URL;
        this.playerUrl = "";
        this.observers = new ArrayList<>();
        leftStackFull = false;
        rightStackFull = false;
    }

    public Player(String name) {
        this();
        this.playerName = name;
    }

    public synchronized Stack<Shape> getLeftStack() {
        return leftStick;
    }

    public synchronized Stack<Shape> getRightStack() {
        return rightStick;
    }

    public synchronized boolean pushPlateLeft(Shape shape) {
        return pushPlate(this.leftStick, shape, Stick.LEFT);
    }

    public synchronized boolean pushPlateRight(Shape shape) {
        return pushPlate(this.rightStick, shape, Stick.RIGHT);
    }

    private void popPlate(Stack<Shape> stack) throws EmptyStackException {
        stack.pop();
    }

    // returns true if got consecutive plates and the score increased
    private synchronized boolean pushPlate(Stack<Shape> stack, Shape shape, Stick stick) {
        stack.push(shape);
        if (stack.size() >= GameRules.NUM_OF_CONSECUTIVE_PLATES) {
            Queue<Shape> queue = new LinkedList<>();
            for (int i = 0; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
                if (stack.peek().getColor() == shape.getColor()) {
                    queue.add(stack.pop());
                }
            }
            if (queue.size() < GameRules.NUM_OF_CONSECUTIVE_PLATES) {
                while (!queue.isEmpty()) {
                    stack.push(queue.poll());
                }
            } else {
                this.addScore(GameRules.CONSECUTIVE_PLATES_ADDED_SCORE);
                notifyObservers(stick);
                return true;
            }
            return false;
        }
        return false;
    }

    private synchronized void addScore(int change) {
        this.score += change;
    }

    private synchronized void notifyObservers(Stick stick) {
        for (ScoreObserver scoreObserver : observers) {
            scoreObserver.update(this.score, this.playerName, stick);
        }
    }

    public void registerObserver(ScoreObserver scoreObserver) {
        observers.add(scoreObserver);
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

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public String getLeftStickUrl() {
        return leftStickUrl;
    }

    public void setLeftStickUrl(String leftStickUrl) {
        this.leftStickUrl = leftStickUrl;
    }

    public String getRightStickUrl() {
        return rightStickUrl;
    }

    public void setRightStickUrl(String rightStickUrl) {
        this.rightStickUrl = rightStickUrl;
    }

    private String urlFromPath(String path) {
        try {
            return new File(path).toURI().toURL()
                    .toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stack<Shape> getLeftStick() {
        return leftStick;
    }

    public void setLeftStick(Stack<Shape> leftStick) {
        this.leftStick = leftStick;
    }

    public Stack<Shape> getRightStick() {
        return rightStick;
    }

    public void setRightStick(Stack<Shape> rightStick) {
        this.rightStick = rightStick;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DoubleProperty speedProperty() {
        return speed;
    }

    public synchronized boolean isLeftStackFull() {
        return leftStackFull;
    }

    public synchronized void setLeftStackFull(boolean leftStackFull) {
        this.leftStackFull = leftStackFull;
    }

    public synchronized boolean isRightStackFull() {
        return rightStackFull;
    }

    public synchronized void setRightStackFull(boolean rightStackFull) {
        this.rightStackFull = rightStackFull;
    }
}
