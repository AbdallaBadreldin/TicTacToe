/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class OnlinePlayerListController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label usernametxt;
    @FXML
    private Label scoretxt;
    @FXML
    private AnchorPane middanc;
    @FXML
    private Label emailtxt;
    @FXML
    private AnchorPane player2anc;
    @FXML
    private Label player2lbl;
    @FXML
    private AnchorPane player1anc;
    @FXML
    private Label player1lbl;
    @FXML
    private AnchorPane stateanc;
    @FXML
    private Label statelbl;
    @FXML
    private AnchorPane playboard;
    @FXML
    private GridPane grid;
    @FXML
    private Button btn5;
    @FXML
    private Button btn4;
    @FXML
    private Button btn2;
    @FXML
    private Button btn7;
    @FXML
    private Button btn1;
    @FXML
    private Button btn3;
    @FXML
    private Button btn9;
    @FXML
    private Button btn6;
    @FXML
    private Button btn8;
    @FXML
    private Pane paneLabel;
    @FXML
    private Label currentLabel;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Button btnWatchGame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonPressed(ActionEvent event) {
    }

    @FXML
    private void watchGame(ActionEvent event) {
    }

    @FXML
    private void backToMainPage(ActionEvent event) {
    }
    
}
