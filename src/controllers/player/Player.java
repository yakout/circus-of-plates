package controllers.player;


import javafx.scene.Node;

/**
 * Created by ahmedyakout on 1/23/17.
 */
public class Player {
    private String name;
    private Node leftStick;
    private Node rightStick;
    private Node playerPane;
    private models.players.Player playerModel;

    Player(Node rightStick, Node leftStick, Node playerPane, models.players.Player playerModel) {
        this.playerModel = playerModel;
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.playerPane = playerPane;
    }

    public Node getLeftStick() {
        return leftStick;
    }

    public Node getRightStick() {
        return rightStick;
    }

    public Node getPlayerPane() {
        return playerPane;
    }

    public models.players.Player getPlayerModel() {
        return playerModel;
    }
}
