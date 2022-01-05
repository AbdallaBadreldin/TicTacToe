
package controller;

import client.GameClient;
import client.interfaces.SignUpInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Player;

/**
 *
 * @author Radwa
 */
public class RegisterScreenController implements Initializable, SignUpInterface {

    @FXML
    private AnchorPane loginStage;
    @FXML
    private ImageView backImage;
    @FXML
    private ImageView registerImage;
    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXButton imgBtn;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXPasswordField confirmPasswordTxt;
    
    private final Navigation navigator = new Navigation();
    private Player player;
    @FXML
    private JFXButton signUpBtn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerImage.setImage(new Image("/Gallary/loginImage.png"));
        confirmPasswordTxt.setFocusTraversable(false);
        passwordTxt.setFocusTraversable(false);
    }

    private void passwordText(MouseEvent event) {
        passwordTxt.clear();
    }



    private void signUpBtn(ActionEvent event) {
        player = new Player();
        player.setPassword(passwordTxt.getText());
        player.setUserName(usernameTxt.getText());
        try {
            GameClient client = GameClient.getInstactance("10.178.240.229", 3333);
            client.sendRequest(player);
            
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signInMouseClicked(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.LOGIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void backMouseClicked(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.LOGIN_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void usernameText(MouseEvent event) {
        usernameTxt.clear();
    }

    private void confirmPasswordText(MouseEvent event) {
        confirmPasswordTxt.clear();
    }

    @Override
    public void onStateRecive(Player player) {
        
    }

    @FXML
    private void onUsernameTxtAction(ActionEvent event) {
    }

    @FXML
    private void onPasswordTxtAction(ActionEvent event) {
    }

    @FXML
    private void onConfirmPasswordTxtAction(ActionEvent event) {
    }

    @FXML
    private void onSignUp(ActionEvent event) {
        
    }

}