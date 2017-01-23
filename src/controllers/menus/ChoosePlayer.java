package controllers.menus;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.event.MouseEvent;

/**
 * Created by Ahmed Khaled on 23/01/2017.
 */
public class ChoosePlayer {
    private static ChoosePlayer instance;
    private static final String CLOWN_DIR = "assets/images/clowns/clown_";
    @FXML
    VBox imagesHolder;
    @FXML
    HBox firstRow;
    @FXML
    HBox secondRow;
    private ChoosePlayer() {
    }

    public static ChoosePlayer getInstance() {
        if (instance == null) {
            instance = new ChoosePlayer();
        }
        return instance;
    }

    /**
     * Should be invoked on choosing player's clown.
     */
    public void attachClowns() {
        for (Node node : firstRow.getChildren()) {
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
        }
    }

    @FXML
    public void mouseHandler(MouseEvent event) {
        String id = ((Node)event.getSource()).getId().toString();

    }

    @FXML
    public void selectClown() {

    }
}
