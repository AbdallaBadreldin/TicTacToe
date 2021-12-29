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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        Image image = new Image("/Gallary/main.gif", 200, 200, false, true, true);
        ImageView imageView = new ImageView(image);
        splashScreen.getChildren().add(imageView);

    }

    public SplashScrennController() {
        stage = new Stage();

    }

    public void run() {
        try {
            thread.sleep(13000);

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
            Parent root = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SplashScrennController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
