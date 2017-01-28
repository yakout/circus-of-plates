package controllers.menus;

import controllers.input.InputType;
import controllers.input.joystick.Joystick;
import controllers.main.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.players.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class PlayerChooser implements Initializable {

    private static final String CLOWN_DIR = "views/clowns/clown_";
    private static final String FILE_NAME = "/clown.fxml";
    private static final String PLAYER = "PLAYER ";
    private static final String CHOOSE = ": Choose";
    private static final String JOYSTICK = "joystick";
    private static final String KEYBOARD = "keyboard";
    private static final String MOUSE = "mouse";
    private static Logger logger = LogManager.getLogger(PlayerChooser.class);
    private static PlayerChooser instance;
    private String chosenClownID;
    private int currPlayer;
    private InputType inputType;
    private String playerName;
    private boolean isPlayer1;
    private List<Player> choosenPlayers;

    @FXML
    private AnchorPane anchor;
    @FXML
    private VBox imagesHolder;
    @FXML
    private HBox firstRow;
    @FXML
    private HBox secondRow;
    @FXML
    private Button choose;
    @FXML
    private RadioButton keyboard, joystick;
    @FXML
    private TextField currPlayerName;

    public static PlayerChooser getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choosenPlayers = new ArrayList<>();
        instance = this;
        isPlayer1 = true;
        inputType = InputType.KEYBOARD_PRIMARY;
        chosenClownID = "5";
        currPlayer = 1;
    }

    public void setVisible(final boolean visible) {
        anchor.setVisible(visible);
    }

    @FXML
    private void mouseHandler(MouseEvent event) {
        chosenClownID = ((Node) event.getSource()).getId();
    }

    @FXML
    public void selectClown() {
        if (isPlayer1) {
            choosenPlayers.clear();
            updateData();
            setPlayer2Label();
            keyboard.setSelected(true);
            joystick.setSelected(false);
            currPlayerName.setText("");
            inputType = InputType.KEYBOARD_SECONDARY;
            isPlayer1 = false;
        } else {
            updateData();
            isPlayer1 = true;
            keyboard.setSelected(true);
            joystick.setSelected(false);
            setVisible(false);
            setPlayer1Label();
            currPlayerName.setText("");


            GameController.getInstance().setPlayersToCurrentGame
                    (choosenPlayers);

            GameMode.getInstance().getMenu().setVisible(true);
            GameMode.getInstance().updateCurrentMenu(GameMode.getInstance());
            inputType = InputType.KEYBOARD_PRIMARY;
            logger.info("Game mode menu is shown after choosing clown.");
        }
        logger.info("First player has chosen clown_" + chosenClownID
                + ".");
    }

    private void updateData() {
        if (inputType == InputType.JOYSTICK_PRIMARY || inputType == InputType
                .JOYSTICK_SECONDARY) {
            Joystick.getInstance().start();
        }
        playerName = currPlayerName.getText();
        Player player = new Player(playerName);
        player.setPlayerUrl(CLOWN_DIR + chosenClownID + FILE_NAME);
        player.setInputType(inputType);
        if (inputType == InputType.JOYSTICK_SECONDARY) {
            player.setSpeed(5);
        } else if (inputType == InputType.JOYSTICK_PRIMARY) {
            player.setSpeed(0.5);
        } else {
            player.setSpeed(20);
        }
        choosenPlayers.add(player);
    }

    @FXML
    private void selectInputType(ActionEvent event) {
        System.out.println(((Node) event.getSource()).getId());
        switch (((Node) event.getSource()).getId()) {
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
        logger.debug("Player selected " + ((Node) event.getSource())
                .getId().toString() +
                " input type.");
    }

    private void setPlayer2Label() {
        ((Label) anchor.getChildren().get(0)).setText(PLAYER + String
                .valueOf(++currPlayer) + CHOOSE);
    }

    private void setPlayer1Label() {
        ((Label) anchor.getChildren().get(0)).setText(PLAYER + String
                .valueOf(--currPlayer) + CHOOSE);
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
