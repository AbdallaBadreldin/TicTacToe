/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helpers.Navigation;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import tictactoe.TicTacToe;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class SplashScrennController extends Thread implements Initializable, Runnable {

    @FXML
    private BorderPane splashScreen;
    ImageView XOSplash = new ImageView();

    private Stage stage;
    private Thread thread;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/Gallary/main.gif", 500, 500, false, true, true);
        ImageView imageView = new ImageView(image);
        splashScreen.getChildren().add(imageView);

    }

    public SplashScrennController() {
        stage = new Stage();

    }

    public void run() {
        try {
            thread.sleep(6000);

            Platform.runLater(() -> {

                stage.close();

            });

        } catch (InterruptedException ex) {
            Logger.getLogger(SplashScrennController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void startScreen() {

        try {
            start();
            stage.initStyle(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SplashScrennController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
