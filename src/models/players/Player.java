package models.players;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import models.GameRules;
import models.levels.Level;
import models.shapes.Shape;

public class Player {

    private static int num_of_players = 0;

    private List<Shape> plates;
    private String playerName;
    private int score;
    private Level level;
    private Point position;

    public Player() {
        this.playerName = "Player " + ++num_of_players;
        this.score = 0;
        this.position = new Point(0, 0);
        this.plates = new ArrayList<>();
    }

    public Player(String name) {
        this();
        this.playerName = name;
    }

    public boolean pushPlate(Shape shape) {
        this.plates.add(shape);
        if (this.plates.size() >= GameRules.NUM_OF_CONSECUTIVE_PLATES) {
            for (int i = 1; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
                if (this.plates.get(this.plates.size() - i - 1).equals(shape.getColor())) {
                    return false;
                }
            }
            for (int i = 0; i < GameRules.NUM_OF_CONSECUTIVE_PLATES; i++) {
                this.plates.remove(this.plates.size() - 1);
            }
            this.addScore(GameRules.CONSECUTIVE_PLATES_ADDED_SCORE);
            return true;
        }
        return false;
    }

    public void popPlate() {
        if (!this.plates.isEmpty())
            this.plates.remove(this.plates.size() - 1);
    }

    public void clearPlates() {
        this.plates.clear();
    }

    public int getNumOfPlates() {
        return this.plates.size();
    }

    public void setLevel(Level newLevel) {
        this.level = newLevel;
    }

    public Level getLevel() {
        return this.level;
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

    public void addScore(int change) {
        this.score += change;
    }

    // TODO keep the player within the boundaries of the stage
    public void moveLeft() {
        this.position.x = this.position.x - GameRules.PLAYER_SPEED;
    }

    public void moveRight() {
        this.position.x = this.position.x + GameRules.PLAYER_SPEED;
    }

}
