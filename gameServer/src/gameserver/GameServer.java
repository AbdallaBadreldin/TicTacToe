/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.io.IOException;
import java.io.ObjectInputStream;
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

/**
 *
 * @author Bossm
 */
public class GameServer extends Application {

    static ServerSocket serverSocket;
    static Thread listener;
    static Socket socket;

    static ObjectInputStream objectInputStream;

    @Override
    public void start(Stage stage) throws Exception {
Parent root = FXMLLoader.load(getClass().getResource("/gameserver/FXMLDocument.fxml"));     
        
        //Parent root = FXMLLoader.load(getClass().getResource("/view/ServerMainView.fxml"));     
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe Server");
        stage.show();
        stage.setOnCloseRequest((event) -> {
            System.exit(1);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();

    }

}
