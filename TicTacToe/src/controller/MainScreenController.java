package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import helpers.Navigation;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class MainScreenController implements Initializable {

    private ImageView mainImageView;
    @FXML
    private Button playerVsAIBtn;
    @FXML
    private Button playerVsPlayerBtn;
    @FXML
    private Button playerOnlineBtn;

    private final Navigation navigator = new Navigation();
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView recImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mainImageView.setImage(new Image("/resources/tic-tac-toe.png"));

    }

    private void exitBtnAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void playerVsAIBtnAction(ActionEvent event) {
        try {
            navigator.navigateTo(event, Navigation.HARD_Mode_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void playerVsPlayerBtnAction(ActionEvent event) {
        try {
            navigator.navigateTo(event, Navigation.MAIN_GRID_PAIN);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playerOnlineBtnAction(ActionEvent event) {
        try {
            navigator.navigateTo(event, Navigation.IP_OF_SERVER);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onRecClick(MouseEvent event) {
        System.out.println("clicked.");
        try {
            navigator.navigateTo(event, Navigation.RECORDERS_SCREEN);
        } catch (IOException ex) {
            ex.getMessage();
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
