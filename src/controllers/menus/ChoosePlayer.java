package controllers.menus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class ChoosePlayer implements Initializable {
    private static ChoosePlayer instance;
    private static final String CLOWN_DIR = "assets/images/clowns/clown_";
    private String chosenClownID;

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

    private ChoosePlayer() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static ChoosePlayer getInstance() {
        if (instance == null) {
            instance = new ChoosePlayer();
        }
        return instance;
    }

    public void setVisible(final boolean visible) {
        System.out.println(anchor);
        anchor.setVisible(visible);
    }

    /**
     * Should be invoked on choosing player's clown.
     */
    public void attachClowns() {
        System.out.println(anchor);
        image1.setStyle("-fx-background-image: url('assets/images/clowns/clown_1')");
        /*List<Node> nodeList = firstRow.getChildren();
        for (Node node : nodeList) {
            Pane currPane = (Pane)node;
            Image clown = new Image(CLOWN_DIR + currPane.getId());
            BackgroundImage bkgroundImg = new BackgroundImage(clown,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            currPane.setBackground(new Background(bkgroundImg));
        }
        for (Node node : secondRow.getChildren()) {
            Pane currPane = (Pane)node;
            Image clown = new Image(CLOWN_DIR + currPane.getId());
            BackgroundImage bkgroundImg = new BackgroundImage(clown,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            currPane.setBackground(new Background(bkgroundImg));
        }*/
    }

    @FXML
    public void mouseHandler() {
//        chosenClownID = ((Node)event.getSource()).getId().toString();
    }

    @FXML
    public void selectClown() {
        // TODO: here goes inter action with player controller.
    }

}
