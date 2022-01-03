/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import client.GameClient;
import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.LoginRequest;
import models.Player;
import models.SignInInterface;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class LoginScreenController implements Initializable, SignInInterface {

    Navigation navigator = new Navigation();
    
    @FXML
    private ImageView backImage;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField emailText;
    @FXML
    private AnchorPane loginStage;
    @FXML
    private ImageView loginImage;
    
    private GameClient gameClient;
    private String ip = "10.178.241.60";
    private int port = 3333;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginImage.setImage(new Image("/Gallary/loginImage.png"));
        //stage.initStyle(StageStyle.UNDECORATED);
        passwordText.setFocusTraversable(false);
        try {
            gameClient = GameClient.getInstactance(ip, port);
            gameClient.read();
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void passwordText(MouseEvent event) {
        passwordText.clear();
    }
    
    @FXML
    private void emailText(MouseEvent event) {
        emailText.clear();
    }
    
    @FXML
    private void rememberCheckBox(ActionEvent event) {
    }
    
    @FXML
    private void signInBtn(ActionEvent event) {
        gameClient.setSignInInterface(this);
        try {
            gameClient.sendRequest(new LoginRequest(emailText.getText(), passwordText.getText()));
            gameClient.read();
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void signUp(MouseEvent event) {
        try {
            navigator.navigateTo(event, Navigation.REGISTER_SCREEN);
        } catch (IOException ex) {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void backMouseClicked(MouseEvent event) {
    }

    @Override
    public void onPlayerRevice(Player player) {
        if (player.getUserName() == null){
            System.out.println("data is not vailed.");
        }else {
            ///TODO: navigate to inGameSreacn;
            System.out.println("DONE!s");
        }
    }
    
}
