package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.players.Player;
import models.players.PlayerFactory;

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
    private boolean isPlayer1;
    @FXML
    AnchorPane anchor;
    @FXML
    VBox imagesHolder;
    @FXML
    HBox firstRow;
    @FXML
    HBox secondRow;
    @FXML
    Button choose;

    public ChoosePlayer() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        isPlayer1 = true;
    }

    public static ChoosePlayer getInstance() {
        return instance;
    }

    public void setPlayerNumber(int player) {
        currPlayer = player;
    }

    public void setVisible(final boolean visible) {
        anchor.setVisible(visible);
    }

    @FXML
    private void mouseHandler(MouseEvent event){
        chosenClownID = ((Node)event.getSource()).getId().toString();
    }

    @FXML
    public void selectClown() {
        // TODO: here goes inter action with player controller.
        if (isPlayer1) {
            PlayerFactory.getFactory().registerPlayer(PLAYER + String.valueOf(currPlayer))
                    .setPlayerUrl(CLOWN_DIR + String.valueOf(currPlayer));
            ((Label)anchor.getChildren().get(1)).setText(PLAYER + String.valueOf(++currPlayer));;
            isPlayer1 = false;
        } else {
            PlayerFactory.getFactory().registerPlayer(PLAYER + String.valueOf(currPlayer))
                    .setPlayerUrl(CLOWN_DIR + String.valueOf(currPlayer));
            isPlayer1 = true;
            setVisible(false);
            GameMode.getInstance().getMenu().setVisible(true);
            GameMode.getInstance().updateCurrentMenu(GameMode.getInstance());
        }
    }
}
