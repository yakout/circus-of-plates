package controllers.menus;

import controllers.input.InputType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import jdk.nashorn.internal.scripts.JO;
import models.players.Player;
import models.players.PlayerFactory;

import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class ChoosePlayer implements Initializable {
    private static ChoosePlayer instance;
    private static final String CLOWN_DIR = "assets/images/clowns/clown_";
    private String chosenClownID;
    private int currPlayer;
    private static final String PLAYER = "PLAYER_";
    private static final String CHOOSE = ": Choose";
    private static final String JOYSTICK = "joystick";
    private static final String KEYBOARD = "keyboard";
    private InputType inputType;
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
    @FXML
    RadioButton keyboard, joystick;

    public ChoosePlayer() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        isPlayer1 = true;
        inputType = InputType.KEYBOARD_PRIMARY;
        currPlayer = 1;
    }

    public static ChoosePlayer getInstance() {
        return instance;
    }

    public void setVisible(final boolean visible) {
        anchor.setVisible(visible);
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currPlayer = currentPlayer;
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
            setPlayer2Label();
            keyboard.setSelected(true);
            joystick.setSelected(false);
            //TODO: here you send signal to game controller

            inputType = InputType.KEYBOARD_SECONDARY;
            isPlayer1 = false;
        } else {
            PlayerFactory.getFactory().registerPlayer(PLAYER + String.valueOf(currPlayer))
                    .setPlayerUrl(CLOWN_DIR + String.valueOf(currPlayer));
            isPlayer1 = true;
            keyboard.setSelected(true);
            joystick.setSelected(false);
            setVisible(false);
            setPlayer1Label();
            //TODO: here you send signal to game controller


            GameMode.getInstance().getMenu().setVisible(true);
            GameMode.getInstance().updateCurrentMenu(GameMode.getInstance());
            inputType = InputType.KEYBOARD_PRIMARY;
        }
    }

    @FXML
    private void selectInputType(ActionEvent event) {
        System.out.println(((Node)event.getSource()).getId());
        switch (((Node)event.getSource()).getId()) {
            case KEYBOARD:
                keyboard.setSelected(true);
                joystick.setSelected(false);
                handleInputType(KEYBOARD);
                break;
            case JOYSTICK:
                joystick.setSelected(true);
                keyboard.setSelected(false);
                handleInputType(JOYSTICK);
                break;
            default:
                break;
        }
    }
     private void setPlayer2Label() {
         ((Label)anchor.getChildren().get(0)).setText(PLAYER + String.valueOf(++currPlayer) + CHOOSE);
     }

     private void setPlayer1Label() {
         ((Label)anchor.getChildren().get(0)).setText(PLAYER + String.valueOf(--currPlayer) + CHOOSE);
     }
    private void handleInputType(String input) {
        switch (input) {
            case KEYBOARD:
                if (currPlayer == 1) {
                    inputType = InputType.KEYBOARD_PRIMARY;
                } else {
                    inputType = InputType.KEYBOARD_SECONDARY;
                }
                break;
            case JOYSTICK:
                if (currPlayer == 1) {
                    inputType = InputType.JOYSTICK_PRIMARY;
                } else {
                    inputType = InputType.JOYSTICK_SECONDARY;
                }
                break;
            default:
                break;
        }
    }
}
