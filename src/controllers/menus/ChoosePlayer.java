package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import models.players.Player;
import models.players.PlayerFactory;

import java.awt.event.MouseEvent;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class ChoosePlayer implements Initializable {
    private static ChoosePlayer instance;
    private static final String CLOWN_DIR = "assets/images/clowns/clown_";
    private String chosenClownID;
    private int currPlayer;
    private static final String PLAYER = "PLAYER_";

    @FXML
    AnchorPane anchor;
    @FXML
    VBox imagesHolder;
    @FXML
    HBox firstRow;
    @FXML
    HBox secondRow;
    @FXML
    Button image1;
    @FXML
    Button choose;

    public ChoosePlayer() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
    }

    public static ChoosePlayer getInstance() {
        return instance;
    }

    public void setPlayerNumber(int player) {
        currPlayer = player;
    }

    public void setVisible(final boolean visible) {
        System.out.println(anchor);
        anchor.setVisible(visible);
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        chosenClownID = ((Node)event.getSource()).getId().toString();
    }

    @FXML
    public void selectClown() {
        // TODO: here goes inter action with player controller.
        PlayerFactory.getFactory().registerPlayer(PLAYER + String.valueOf(currPlayer))
                .setPlayerUrl(CLOWN_DIR + String.valueOf(currPlayer));

    }


}
