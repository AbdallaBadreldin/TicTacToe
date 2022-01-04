/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import static gameserver.FXMLDocumentController.serverListenerThread;
import static gameserver.FXMLDocumentController.serverSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socket.SocketHandler;
import static socket.SocketHandler.closeStreams;


/**
 *
 * @author Bossm
 */
public class GameServer extends Application {

    public static final int ONLINE = 1;
    public static final int OFF_ONLINE = 2;
    public static final int BUSY = 3;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* try {
            
            serverSocket = new ServerSocket(3333);
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        launch(args);
    }
    //  public static ServerSocket getServerSocket(){return serverSocket;}

    @Override
    public void stop() {
        try {
            closeStreams();
            serverSocket.close();
            serverListenerThread.stop();
            Platform.exit();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
