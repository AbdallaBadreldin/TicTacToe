/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mahmoud
 */
public class Navigation {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void navigateToMainScreen(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
      public void navigateToDialog(ActionEvent event ) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/Dialog.fxml"));
        this.stage = stage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void navigateToMainGridPane(ActionEvent event ) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/MainGridPane.fxml"));
        this.stage = stage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void navigateToPlayerVsAI(ActionEvent event) throws IOException {
   

        root = FXMLLoader.load(getClass().getResource("/view/PlayerVsAIScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToPlayerOnline(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/OnlineScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToPlayerVsPlayer(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/MainGridPane.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToRecordings(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/RecordingsScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
