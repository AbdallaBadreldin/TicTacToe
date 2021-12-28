/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import client.GameClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import helpers.Navigation;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import models.GameRequest;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class MainScreenController implements Initializable {
    
    @FXML
    private Button exitBtn;
    @FXML
    private ImageView mainImageView;
    @FXML
    private Button playerVsAIBtn;
    @FXML
    private Button playerVsPlayerBtn;
    @FXML
    private Button playerOnlineBtn;
    @FXML
    private Button recordingsBtn;
    
    Navigation navigator;;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainImageView.setImage(new Image("/resources/tic-tac-toe.png"));
        Navigation navigator= new Navigation();
        
    }    

    @FXML
    private void exitBtnAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void playerVsAIBtnAction(ActionEvent event) {
        try {
            navigator.navigateToPlayerVsAI(event);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void playerVsPlayerBtnAction(ActionEvent event) {
        navigator = new Navigation();
        try {
            navigator.navigateToPlayerVsPlayer(event);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playerOnlineBtnAction(ActionEvent event) {
        try {
            navigator.navigateToPlayerOnline(event);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void recordingsBtnAction(ActionEvent event) {
        try {
            navigator.navigateToRecordings(event);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
