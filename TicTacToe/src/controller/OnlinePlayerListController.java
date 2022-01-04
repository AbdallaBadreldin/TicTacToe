/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class OnlinePlayerListController implements Initializable {

    @FXML
    private ListView<?> playersList;
    FXMLLoader fxml;
    
    private ArrayList<FXMLLoader>onlinePlayers;
    private ObservableList playersObservableList;
    PlayersListItemsController listItems;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    private void onlinePlayersList()
    {
         playersObservableList=FXCollections.observableArrayList();
        try {
           
            fxml=new FXMLLoader(getClass().getResource("/view/PlayersListItems.fxml"));
            listItems=fxml.getController();
            onlinePlayers.add(fxml.load());
            playersObservableList.addAll(onlinePlayers);
                       
        } catch (IOException ex) {
            Logger.getLogger(OnlinePlayerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
