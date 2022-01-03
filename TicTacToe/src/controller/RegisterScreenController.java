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
import models.Player;

/**
 *
 * @author Radwa
 */
public class RegisterScreenController implements Initializable {

    Navigation navigator = new Navigation();
    private Player player;
    @FXML
    private AnchorPane loginStage;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField emailText;
    @FXML
    private ImageView backImage;
    @FXML
    private TextField usernameText;
    @FXML
    private ImageView registerImage;
    @FXML
    private TextField confirmPassText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerImage.setImage(new Image("/Gallary/loginImage.png"));
        confirmPassText.setFocusTraversable(false);
        passwordText.setFocusTraversable(false);
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
    private void policyCheckBox(ActionEvent event) {
    }

    @FXML
    private void signUpBtn(ActionEvent event) {
        player = new Player();
        player.setPassword(passwordText.getText());
        player.setUserName(usernameText.getText());
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

    @FXML
    private void usernameText(MouseEvent event) {
        usernameText.clear();
    }

    @FXML
    private void confirmPasswordText(MouseEvent event) {
        confirmPassText.clear();
    }

}
