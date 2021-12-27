/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import database.DatabaseAccess;
import helper.ConnectionHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author Bossm
 */
public class FXMLDocumentController implements Initializable {
    DataInputStream dis;
    PrintStream ps;
    String msg;
    ServerSocket serverSocket;
  
    @FXML
    private Button stop;
    @FXML
    private Button start;
    @FXML
    private Text online;
    @FXML
    private Text current;
    @FXML
    private Text total;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // DatabaseAccess.setUpConnection();
       //startServer();
    }    
    public void startServer(){
      try {
            System.out.println("got into try connection");
            serverSocket = new ServerSocket(3333);
            Socket s = serverSocket.accept();
            System.out.println("accepted Connection");
            new ConnectionHandler(s);
            // TODO
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void stopServer(ActionEvent event) {
    }

    @FXML
    private void startServer(ActionEvent event) {
    }
      
    
}
 
