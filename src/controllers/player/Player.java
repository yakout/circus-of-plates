package controllers.player;


import javafx.scene.Node;

/**
 * Created by ahmedyakout on 1/23/17.
 */
class Player {
    private String name;
    private Node leftStick;
    private Node rightStick;
    private Node playerNode;
    private models.players.Player playerModel;

    Player(String name, Node playerNode, models.players.Player playerModel) {
        this.name = name;
        this.playerModel = playerModel;
        this.playerNode = playerNode;
    }

    public void setLeftStick(Node leftStick) {
        this.leftStick = leftStick;
    }

    public void setRightStick(Node rightStick) {
        this.rightStick = rightStick;
    }

    public Node getLeftStick() {
        return leftStick;
    }

    public Node getRightStick() {
        return rightStick;
    }

    public Node getPlayerNode() {
        return playerNode;
    }

    public models.players.Player getPlayerModel() {
        return playerModel;
    }

    public String getName() {
        return name;
    }
}
