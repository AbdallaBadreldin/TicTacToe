package controller;

import com.jfoenix.controls.JFXButton;
import helpers.GameRecorder;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import models.Common;
import models.GameSession;
import models.ListOfRecorders;

/**
 * FXML Controller class
 *
 * @author Abdo
 */
public class RecordesScreenController implements Initializable {

    @FXML
    private ImageView backImg;
    @FXML
    private JFXButton imgBtn;
    @FXML
    private ListView<String> listView;
    @FXML
    private JFXButton watchBtn;

    private String selectedItem;
    private ListOfRecorders recorders;
    private final Navigation nav = new Navigation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameRecorder recorder = new GameRecorder();
        recorders = recorder.reader();
        List<String> gameNames = new ArrayList<>();
        for (GameSession session : recorders.getGameSession()) {
            gameNames.add(session.getGameName());
        }
        listView.getItems().addAll(gameNames);
        listView.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            selectedItem = listView.getSelectionModel().getSelectedItems().toString();
        });
    }

    @FXML
    private void onWatchBtnAction(ActionEvent event) {
        for (GameSession session : recorders.getGameSession()) {
            String str = "[" + session.getGameName() + "]";
            if (str.equals(selectedItem)) {
                Common.PREV_SESSION = session;
            }
        }
        try {
            nav.navigateTo(event, Navigation.MAIN_GRID_PAIN);
        } catch (IOException ex) {
            Logger.getLogger(RecordesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onBackBtnClick(ActionEvent event) {
        try {
            nav.navigateTo(event, Navigation.MAIN_SCREEN);
        } catch (IOException ex) {
            ex.getMessage();
            Logger.getLogger(RecordesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
