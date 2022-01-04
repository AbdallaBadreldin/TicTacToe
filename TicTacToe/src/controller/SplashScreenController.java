/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Radwa
 */
public class SplashScreenController extends Thread implements Initializable, Runnable  {
    
    private Stage stage;
    private Thread thread;
    private Scene scene;

    @FXML
    private ImageView splashImage;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img1.setImage(new Image("/Gallary/img3.png"));
        img2.setImage(new Image("/Gallary/img2.png"));
        img3.setImage(new Image("/Gallary/img4.png"));
        img4.setImage(new Image("/Gallary/img1.jfif"));



       
    }    
     public SplashScreenController() {
         
        stage = new Stage();

    }

    public void run() {
        try {
            thread.sleep(6000);
            Platform.runLater(stage::close);
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
