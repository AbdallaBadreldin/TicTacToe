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
import socket.SocketHandler;

/**
 *
 * @author Bossm
 */
public class FXMLDocumentController extends Thread implements Initializable {

    Socket mySocket;
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
    final boolean SERVER_ONLINE = true;
    final boolean SERVER_OFFLINE = false;
    boolean serverStatue = true;
    public volatile ServerSocket serverSocket;
Thread serverListenerThread;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        serverListenerThread = new Thread();
        try {
            serverSocket =  new ServerSocket(3333);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverListenerThread.start();
        
      
    }

    @FXML
    private void stopServer(ActionEvent event) {
        try {
           serverListenerThread.suspend();
            serverSocket.close();
            serverStatue = SERVER_OFFLINE;
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void startServer(ActionEvent event) {
        // startServer();  
        if (!serverStatue) {
             serverListenerThread = new Thread();
            serverListenerThread.resume();
            serverStatue = SERVER_ONLINE;
            try {
                serverSocket =  new ServerSocket(3333);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void run() {
        while (true) {
            try {
                mySocket = serverSocket.accept();

                new SocketHandler(mySocket);
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*@Override
  public void stop(){

      try {
          serverSocket.close();
      } catch (IOException ex) {
          Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
      }
}*/
    public void updateTotalPlayers(int i) {
        total.setText(String.valueOf(i));
    }

}
