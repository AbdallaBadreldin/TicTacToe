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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import models.Player;
import socket.SocketHandler;
import static socket.SocketHandler.closeStreams;
//import static socket.SocketHandler.closeAllStreams;

/**
 *
 * @author Bossm
 */
public class FXMLDocumentController implements Initializable, Runnable {

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
private int totalPlayersConnected;
    public static volatile ServerSocket serverSocket;
    public static Thread serverListenerThread;
    public List<Player> players;
    Player player;
public static volatile FXMLDocumentController fx;
    public static volatile int totalPlayers ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fx=this;
        DatabaseAccess.setUpConnection();
        serverListenerThread = new Thread(this);
                       setPlayers();
                printPlayers(players);
     
        try {
            serverSocket = new ServerSocket(3333);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverListenerThread.start();
    }

    @FXML
    private void stopServer(ActionEvent event) {
updateTotalPlayers(0);
        if (!serverSocket.isClosed()) {
            try {
                closeStreams();
                serverSocket.close();
                serverListenerThread.suspend();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void startServer(ActionEvent event) {
        if (serverSocket.isClosed()) {
            try {
                serverSocket = new ServerSocket(3333);
                serverListenerThread.resume();
                
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void run() {
        while (true) {
            try {
                Socket mySocket = serverSocket.accept();
                new SocketHandler(mySocket);
                 //updateTotalPlayers(totalPlayersConnected++);
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    

    public void updateTotalPlayers( int i) {
        total.setText(String.valueOf(i));
        //FXMLDocumentController.updateTotalPlayers(5);
    }
    
    public List<Player> getAllPlayersFromDB(){
        return DatabaseAccess.selectAllPlayers();
    }
    
    public void setPlayers(){
        this.players = DatabaseAccess.selectAllPlayers();
    }
    
    public void printPlayers(List<Player> players) {
        for(Player p: players){
            System.out.println( p.getUserName());
        }
    }
    /*RegisterviewController reg = new Rg()
    player = reg.getPlayerData();
    DAO.signUp(player);
    */
    
    public void checkForUserToSignUp() {
        
        
    }

}
