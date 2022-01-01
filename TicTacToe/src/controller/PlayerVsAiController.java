/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helpers.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mahmoud
 */
public class PlayerVsAiController extends MainGridPaneController implements Initializable{

    @FXML
    private AnchorPane mainPane;
    
    MainGridPaneController mainnGridPaneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.MAIN_GRID_PAIN));
        try {
            mainPane.getChildren().add(loader.load());
            mainnGridPaneController = loader.getController();
            mainnGridPaneController.setPlayerOneName("Mahmoud");
            mainnGridPaneController.setPlayerTwoName("Comp");
            mainnGridPaneController.setPlayerTwoImage("/resources/ai-avatar.png");
            mainnGridPaneController.setPlayerOneImage("/resources/player-one-avatar.jpg");
            mainnGridPaneController.setIsAIMode(true);
        } catch (IOException ex) {
            Logger.getLogger(PlayerVsAiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    
    
}
