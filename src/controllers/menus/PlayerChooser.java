package controllers.menus;

import controllers.input.InputType;
import controllers.main.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.players.PlayerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class PlayerChooser implements Initializable {

    private static Logger logger = LogManager.getLogger(PlayerChooser.class);
    private static final String CLOWN_DIR = "src/views/clowns/clown_";
    private static final String FILE_NAME = "/clown.fxml";
    private static final String PLAYER = "PLAYER ";
    private static final String CHOOSE = ": Choose";
    private static final String JOYSTICK = "joystick";
    private static final String KEYBOARD = "keyboard";
    private static final String MOUSE = "mouse";
    private static PlayerChooser instance;
    private String chosenClownID;
    private int currPlayer;
    private InputType inputType;
    private String playerName;
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
    RadioButton keyboard, joystick, mouse;
    @FXML
    TextField currPlayerName;

    public PlayerChooser() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        isPlayer1 = true;
        inputType = InputType.KEYBOARD_PRIMARY;
        chosenClownID = "5";
        currPlayer = 1;
    }

    public static PlayerChooser getInstance() {
        return instance;
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
            updateData();
            setPlayer2Label();
            keyboard.setSelected(true);
            joystick.setSelected(false);
            mouse.setSelected(false);
            currPlayerName.setText("");
            inputType = InputType.KEYBOARD_SECONDARY;
            isPlayer1 = false;
        } else {
            updateData();
            isPlayer1 = true;
            keyboard.setSelected(true);
            joystick.setSelected(false);
            mouse.setSelected(false);
            setVisible(false);
            setPlayer1Label();
            currPlayerName.setText("");

            GameMode.getInstance().getMenu().setVisible(true);
            GameMode.getInstance().updateCurrentMenu(GameMode.getInstance());
            inputType = InputType.KEYBOARD_PRIMARY;
            logger.info("Game mode menu is shown after choosing clown.");
        }
        logger.info("First player has chosen clown_" + chosenClownID
                + ".");
    }

    private void updateData() {
        playerName = currPlayerName.getText();
//        PlayerFactory.getFactory().registerPlayer(PLAYER + String.
//                valueOf(currPlayer))
//                .setPlayerUrl(CLOWN_DIR + String.valueOf(currPlayer));

        GameController.getInstance().getCurrentGame().createPlayer(CLOWN_DIR
                + String.valueOf(currPlayer), playerName, inputType);
    }

    @FXML
    private void selectInputType(ActionEvent event) {
        System.out.println(((Node) event.getSource()).getId());
        switch (((Node) event.getSource()).getId()) {
            case KEYBOARD:
                keyboard.setSelected(true);
                joystick.setSelected(false);
                mouse.setSelected(false);
                handleInputType(KEYBOARD);
                break;
            case JOYSTICK:
                joystick.setSelected(true);
                keyboard.setSelected(false);
                mouse.setSelected(false);
                handleInputType(JOYSTICK);
                break;
            case MOUSE:
                mouse.setSelected(true);
                keyboard.setSelected(false);
                joystick.setSelected(false);
                handleInputType(MOUSE);
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
            case MOUSE:
                inputType = InputType.MOUSE;
                break;
            default:
                break;
        }
    }
}
