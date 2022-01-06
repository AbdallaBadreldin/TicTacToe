package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img1;

    @FXML
    private JFXDialog levelDialog;

    @FXML
    private JFXButton Hard;
    @FXML
    private StackPane root;
    @FXML
    private JFXButton Easy;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img1.setImage(new Image("/Gallary/img4.png"));
        img2.setImage(new Image("/Gallary/img2.png"));
        img3.setImage(new Image("/Gallary/img3.png"));

        levelDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        levelDialog.setDialogContainer(root);
        Easy.setOnAction((event) -> {
            try {
                navigator.navigateTo(event, Navigation.PLAYER_VS_AI);
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Hard.setOnAction((e) -> {
            try {
                navigator.navigateTo(e, Navigation.HARD_MODE);
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });


        



    }

    private void exitBtnAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void playerVsAIBtnAction(ActionEvent event) {

    levelDialog.show();

       
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
